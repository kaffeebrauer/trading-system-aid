package au.com.openbiz.trading.logic.manager.profitloss.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.manager.profitloss.ProfitLossCalculatorManager;
import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public class DefaultProfitLossCalculatorManager implements ProfitLossCalculatorManager {

	private final Date MINIMUM_DATE = DateHelper.parseDate("01/01/2000", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
	
	private TransactionManager transactionManager;
	private SecurityPriceManager securityPriceManager;
	
	@SuppressWarnings("unchecked")
	public ProfitLossValueObject calculateProfitLoss(final Date from, final Date to, final Security security) {
		BigDecimal profitLoss = BigDecimal.ZERO;
		List<Transaction> transactions = transactionManager.findTransactionsBySecurity(security);
		
		//1. Find out initial market valuation and subtract it
		MarketValuationValueObject initialMarketValuation = getInitialMarketValuation(transactions, security, from);
		profitLoss = profitLoss.subtract(initialMarketValuation.getValuation());
		
		//2. Find transactions in the time frame and add sells and subtract buys
		List<Transaction> transactionsInTimeFrame = (List<Transaction>)CollectionUtils
			.select(transactions, new SelectTransactionsForTimeFramePredicate(from, to));
		profitLoss = addSellsAndSubtractBuys(transactionsInTimeFrame, profitLoss);
		
		//3. Find out final market valuation and add it
		MarketValuationValueObject finalMarketValuation = getFinalMarketValuation(transactions, security, to);
		profitLoss = profitLoss.add(finalMarketValuation.getValuation());
		
		//4. Subtract all transaction brokerage
		for(Transaction transaction : transactionsInTimeFrame) {
			profitLoss = profitLoss.subtract(transaction.getBrokerage());
		}
		
		return new ProfitLossValueObject(initialMarketValuation, finalMarketValuation, transactionsInTimeFrame, profitLoss, security);
	}

	@SuppressWarnings("unchecked")
	private MarketValuationValueObject getInitialMarketValuation(final List<Transaction> transactions, final Security security, final Date from) {
		List<Transaction> previousToFromTransactions = (List<Transaction>)CollectionUtils
			.select(transactions, new SelectTransactionsForTimeFramePredicate(MINIMUM_DATE, from));

		Long holdingAmount = Long.valueOf(0);
		for(Transaction previousToFromTransaction : previousToFromTransactions) {
			if(previousToFromTransaction instanceof BuyTransaction) {
				holdingAmount = holdingAmount + previousToFromTransaction.getQuantity();
			} else {
				holdingAmount = holdingAmount - previousToFromTransaction.getQuantity();
			}
		}
		return getMarketValuation(holdingAmount, security, from);
	}
	
	private BigDecimal addSellsAndSubtractBuys(final List<Transaction> transactionsInTimeFrame, BigDecimal profitLoss) {
		for(Transaction transaction : transactionsInTimeFrame) {
			BigDecimal amount = transaction.getPrice().multiply(new BigDecimal(transaction.getQuantity()));
			if(transaction instanceof BuyTransaction) {
				profitLoss = profitLoss.subtract(amount);
			} else {
				profitLoss = profitLoss.add(amount);
			}
		}
		return BigDecimalHelper.scaleAndRound(profitLoss);
	}
	
	@SuppressWarnings("unchecked")
	private MarketValuationValueObject getFinalMarketValuation(final List<Transaction> transactions, final Security security, final Date to) {
		List<Transaction> previousToFinalDateTransactions = (List<Transaction>)CollectionUtils
			.select(transactions, new SelectTransactionsForTimeFramePredicate(MINIMUM_DATE, to));
		
		Long holdingAmount = Long.valueOf(0);
		for(Transaction transaction : previousToFinalDateTransactions) {
			if(transaction instanceof BuyTransaction) {
				holdingAmount = holdingAmount + transaction.getQuantity();
			} else {
				holdingAmount = holdingAmount - transaction.getQuantity();
			}
		}
		
		return getMarketValuation(holdingAmount, security, to);
	}
	
	private MarketValuationValueObject getMarketValuation(final Long holdingAmount, final Security security, final Date valuationDate) {
		MarketValuationValueObject marketValuation = null;
		if(holdingAmount > 0) {
			SecurityPrice securityPrice = null;
			if(DateHelper.isTodayDate(valuationDate)) {
				securityPrice = securityPriceManager.findLastTradeStockPrice(security);
			} else {
				securityPrice = securityPriceManager.findStockPriceForADate(security, valuationDate);
			}
			BigDecimal price = BigDecimalHelper.scaleAndRound(new BigDecimal(securityPrice.getClosePrice()));
			marketValuation = new MarketValuationValueObject(holdingAmount, security, price, valuationDate);
		} else {
			marketValuation = new MarketValuationValueObject(Long.valueOf(0), security, BigDecimal.ZERO, valuationDate);
		}
		return marketValuation;
	}

	public List<ProfitLossValueObject> calculateProfitLoss(Date from, Date to, Portfolio portfolio) {
		List<ProfitLossValueObject> profitLossVOs = new ArrayList<ProfitLossValueObject>();
		for(Security security : portfolio.getSecurities()) {
			ProfitLossValueObject profitLossVO = calculateProfitLoss(from, to, security);
			if(profitLossVO.getProfitLoss().compareTo(BigDecimal.ZERO) != 0) {
				profitLossVOs.add(profitLossVO);
			}
		}
		return profitLossVOs;
	}
	
	public BigDecimal calculateTotalProfitLoss(final List<ProfitLossValueObject> profitLossVOs) {
		BigDecimal totalProfitLoss = BigDecimal.ZERO;
		for(ProfitLossValueObject profitLossVO : profitLossVOs) {
			totalProfitLoss = totalProfitLoss.add(profitLossVO.getProfitLoss());
		}
		return totalProfitLoss;
	}
	
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setSecurityPriceManager(SecurityPriceManager securityPriceManager) {
		this.securityPriceManager = securityPriceManager;
	}

}
