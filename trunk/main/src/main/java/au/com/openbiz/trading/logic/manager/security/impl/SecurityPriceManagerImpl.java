package au.com.openbiz.trading.logic.manager.security.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.calculator.impl.BoughtSoldAmountCalculator;
import au.com.openbiz.trading.logic.calculator.impl.ChannelCalculatorImpl;
import au.com.openbiz.trading.logic.calculator.impl.MarketValueCalculator;
import au.com.openbiz.trading.logic.calculator.impl.NumberOfWeeksCalculator;
import au.com.openbiz.trading.logic.calculator.impl.ReturnOnCapitalCalculator;
import au.com.openbiz.trading.logic.dao.security.SecurityDao;
import au.com.openbiz.trading.logic.dao.security.SecurityPriceDao;
import au.com.openbiz.trading.logic.dao.security.SnapshotDao;
import au.com.openbiz.trading.logic.dao.transaction.TransactionDao;
import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.security.Snapshot;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class SecurityPriceManagerImpl implements SecurityPriceManager {

	private SecurityPriceDao securityPriceDao;
	private SecurityDao securityDao;
	private TransactionDao transactionDao;
	private SnapshotDao snapshotDao;
	
	private static Logger LOGGER = Logger.getLogger(SecurityPriceManagerImpl.class);
	
	public SecurityPrice findStockPriceForADate(Security security, Date date) {
		return securityPriceDao.findStockPriceForADate(security, date);
	}
	
	public List<SecurityPrice> findStockPricesByDate(Security security, Date startDate, Date endDate) {
		LOGGER.info("Finding stock prices for [" + security.getCompleteCode()
				+ "] by date from[" + startDate + "] to [" + endDate + "]");
		return securityPriceDao.findStockPricesByDate(security, startDate, endDate);
	}
	
	public List<SecurityPrice> findLastNDaysStockPrices(Security security, int days) {
		Date currentDate = new Date(System.currentTimeMillis());
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(currentDate);
		startDate.add(Calendar.DATE, -days);
		return this.findStockPricesByDate(security, startDate.getTime(), currentDate);
	}
	
	public SecurityPrice findLastTradeStockPrice(Security security) {
		LOGGER.info("Finding last trade stock price for [" + security.getCompleteCode() + "]");
		return securityPriceDao.findLastTradeStockPrice(security);
	}
	
	@SuppressWarnings("unchecked")
	public void createSnapshot() {
		//TODO What if you buy 2 parcels of the same security in the same portfolio
		//TODO Build more calculators to decouple calculation algorithms
		//TODO Include currency as paramenter, fix test
		//Get all buy transactions
		List<BuyTransaction> buyTrList = transactionDao.findAllByTransactionTypeAndCurrency(BuyTransaction.class, "AUD");
		
		for(BuyTransaction buyTransaction : buyTrList) {
			//Find the security associated in the transaction
			Security security = securityDao.findById(buyTransaction.getSecurity().getId());
			
			//Find last price for the specified security
			SecurityPrice securityPrice = securityPriceDao.findLastTradeStockPrice(security);
			
			//Calculate market value
			BigDecimal marketValue = new MarketValueCalculator(securityPrice.getClosePrice(), 
					buyTransaction.getQuantity()).calculate();
			LOGGER.info("Security [" + security.getCompleteCode() + "] with [" + buyTransaction.getQuantity()
					+ "] shares @ $[" + securityPrice.getClosePrice() + "] has a market value $[" 
					+ BigDecimalHelper.scaleAndRound(marketValue) + "].");
			
			//Calculate Difference
			BigDecimal boughtAmount = new BoughtSoldAmountCalculator(buyTransaction).calculate();
			BigDecimal difference = BigDecimalHelper.scaleAndRound(marketValue.subtract(boughtAmount));
			
			//Calculate Return On Capital
			BigDecimal roc = new ReturnOnCapitalCalculator(difference, boughtAmount).calculate();
			
			//Find a previous snapshot and Calculate Stop Loss
			//TODO if its the first time use stoploss from techinal analysis
			Snapshot snapshot = snapshotDao.findLastSnapshotByBuyTransactionId(buyTransaction.getId());
			
			//TODO Receive % as parameter to calculate trailing stoploss
			//Substract 10% to have the stoploss
			BigDecimal lastPriceMinusTenPercent = new BigDecimal(securityPrice.getClosePrice())
				.multiply(new BigDecimal(0.9));
			BigDecimal newStopLoss = BigDecimalHelper.scaleAndRound(lastPriceMinusTenPercent);
			if(snapshot!=null) {
				newStopLoss = BigDecimalHelper.scaleAndRound(lastPriceMinusTenPercent.max(snapshot.getTrailingStopLoss()));
			}
			
			//Calculate risk
			//TODO Calculate Risk
			BigDecimal risk = new BigDecimal(0);
			
			//Calculate channel taken
			BigDecimal channelTaken = new ChannelCalculatorImpl(buyTransaction, securityPrice).calculate();
			
			LOGGER.info("Difference is $[" + difference + "], ROC [" + roc + "]%, newStopLoss $[" 
					+ newStopLoss + "], channelTaken [" + channelTaken + "]%");
			
			//Generate a new snapshot
			Snapshot newSnapshot = new Snapshot(new BigDecimal(securityPrice.getClosePrice()), 
					buyTransaction, new Timestamp(System.currentTimeMillis()));
			newSnapshot.setMarketValue(marketValue);
			newSnapshot.setDifference(difference);
			newSnapshot.setReturnOnCapital(roc);
			newSnapshot.setTrailingStopLoss(newStopLoss);
			newSnapshot.setRisk(risk);
			newSnapshot.setChannelTaken(channelTaken);
			
			Integer numberOfWeeks = new NumberOfWeeksCalculator(buyTransaction.getTimestamp(), new Timestamp(System.currentTimeMillis())).calculate().intValue();
			LOGGER.debug("Number of weeks: " + numberOfWeeks);
			
			newSnapshot.setNumberOfWeeksSinceBuy(numberOfWeeks);
			newSnapshot.setSnapshotNumber(1);
			if(snapshot!=null) {
				newSnapshot.setSnapshotNumber(snapshot.getSnapshotNumber() + 1);
			}
			
			snapshotDao.saveOrUpdate(newSnapshot);
		}
		
	}
	
	public void setSecurityPriceDao(SecurityPriceDao securityPriceDao) {
		this.securityPriceDao = securityPriceDao;
	}

	public void setSecurityDao(SecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public void setSnapshotDao(SnapshotDao snapshotDao) {
		this.snapshotDao = snapshotDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
	
}
