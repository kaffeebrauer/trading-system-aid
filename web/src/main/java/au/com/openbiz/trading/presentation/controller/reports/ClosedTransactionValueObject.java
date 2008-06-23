package au.com.openbiz.trading.presentation.controller.reports;

import java.math.BigDecimal;
import java.sql.Timestamp;

import au.com.openbiz.trading.logic.calculator.impl.BoughtSoldAmountCalculator;
import au.com.openbiz.trading.logic.calculator.impl.NumberOfWeeksCalculator;
import au.com.openbiz.trading.logic.calculator.impl.ReturnOnCapitalCalculator;
import au.com.openbiz.trading.persistent.transaction.BuySellTransaction;

public class ClosedTransactionValueObject {

	public ClosedTransactionValueObject(BuySellTransaction buySellTransaction) {
		if(buySellTransaction == null) {
			throw new IllegalArgumentException("Buy sell transaction must not be null.");
		}
		this.buySellTransaction = buySellTransaction;
	}

	private final BuySellTransaction buySellTransaction;

	public final String getSecurity() {
		return buySellTransaction.getBuyTransaction().getSecurity().getCompleteCode();
	}
	
	public final Timestamp getBuyDate() {
		return buySellTransaction.getBuyTransaction().getTimestamp();
	}
	
	public final Long getBuyQuantity() {
		return buySellTransaction.getBuyTransaction().getQuantity();
	}

	public final BigDecimal getBuyPrice() {
		return buySellTransaction.getBuyTransaction().getPrice();
	}
	
	public final BigDecimal getAmountPaid() {
		return new BoughtSoldAmountCalculator(buySellTransaction.getBuyTransaction()).calculate();
	}
	
	public final Timestamp getSellDate() {
		return buySellTransaction.getSellTransaction().getTimestamp();
	}
	
	public final Long getSellQuantity() {
		return buySellTransaction.getSellTransaction().getQuantity();
	}

	public final BigDecimal getSellPrice() {
		return buySellTransaction.getSellTransaction().getPrice();
	}

	public final BigDecimal getAmountReceived() {
		return new BoughtSoldAmountCalculator(buySellTransaction.getSellTransaction()).calculate();
	}
	
	public final BigDecimal getProfitLoss() {
		return getAmountReceived().subtract(getAmountPaid());
	}
	
	public final BigDecimal getReturnOnCapital() {
		return new ReturnOnCapitalCalculator(getProfitLoss(), getAmountPaid()).calculate();
	}
	
	public final int getNumberOfWeeks() {
		return new NumberOfWeeksCalculator(getBuyDate(), getSellDate()).calculate().intValue();
	}
}
