package au.com.openbiz.trading.logic.manager.positionsummary.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.calculator.impl.BoughtSoldAmountCalculator;
import au.com.openbiz.trading.logic.calculator.impl.MarketValueCalculator;
import au.com.openbiz.trading.logic.calculator.impl.NumberOfWeeksCalculator;
import au.com.openbiz.trading.logic.calculator.impl.ReturnOnCapitalCalculator;
import au.com.openbiz.trading.logic.manager.positionsummary.PositionSummaryManager;
import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.security.Snapshot;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class DefaultPositionSummaryManager implements PositionSummaryManager {

	private TransactionManager transactionManager;
	private SecurityPriceManager securityPriceManager;

	public PositionSummaryTransferObject calculatePositionSummary(final Portfolio portfolio) {
		List<PositionSummaryValueObject> positionSummaryVOs = new ArrayList<PositionSummaryValueObject>();

		BigDecimal marketValueTotal = BigDecimal.ZERO;
		BigDecimal differenceTotal = BigDecimal.ZERO;
		BigDecimal boughtAmountTotal = BigDecimal.ZERO;

		List<BuyTransaction> buyTransactionList = null;
		buyTransactionList = transactionManager.findAllBuyTransactionsThatHaveNotBeenSold(portfolio.getId());

		if(buyTransactionList.isEmpty()) return PositionSummaryTransferObject.EMPTY_POSITION_SUMMARY;

		for (BuyTransaction buyTransaction : buyTransactionList) {
			//Find last trade stock price
			SecurityPrice securityPrice = securityPriceManager.findLastTradeStockPrice(buyTransaction.getSecurity());
			//Create a snapshot
			Snapshot snapshot = new Snapshot(new BigDecimal(securityPrice.getClosePrice()), buyTransaction, new Timestamp(System.currentTimeMillis()));
			//Calculate market value
			snapshot.setMarketValue(new MarketValueCalculator(securityPrice.getClosePrice(), buyTransaction.getQuantity()).calculate());
			//Calculate amount spent
			BigDecimal boughtAmount = new BoughtSoldAmountCalculator(buyTransaction).calculate();
			//Calculate difference between spent and market value (profit or loss)
			snapshot.setDifference(BigDecimalHelper.scaleAndRound(snapshot.getMarketValue().subtract(boughtAmount)));
			//Calculate return on capital
			snapshot.setReturnOnCapital(new ReturnOnCapitalCalculator(snapshot.getDifference(), boughtAmount).calculate());
			//Calculate number of weeks since buy
			snapshot.setNumberOfWeeksSinceBuy(new NumberOfWeeksCalculator(buyTransaction.getTimestamp(), new Timestamp(System.currentTimeMillis())).calculate().intValue());

			//Totals
			marketValueTotal = marketValueTotal.add(snapshot.getMarketValue());
			differenceTotal = differenceTotal.add(snapshot.getDifference());
			boughtAmountTotal = boughtAmountTotal.add(boughtAmount);

			positionSummaryVOs.add(new PositionSummaryValueObject(buyTransaction,securityPrice, buyTransaction.getSecurity(), snapshot));
		}

		Collections.sort(positionSummaryVOs);
		BigDecimal rocAverage = new ReturnOnCapitalCalculator(differenceTotal, boughtAmountTotal).calculate();

		return PositionSummaryTransferObject.valueOf(
				positionSummaryVOs, marketValueTotal, differenceTotal, boughtAmountTotal, rocAverage);
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setSecurityPriceManager(SecurityPriceManager securityPriceManager) {
		this.securityPriceManager = securityPriceManager;
	}

}
