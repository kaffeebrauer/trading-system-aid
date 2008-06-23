package au.com.openbiz.trading.logic.calculator.impl;

import java.math.BigDecimal;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.calculator.Calculator;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public class BoughtSoldAmountCalculator implements Calculator {

	private final Long quantity;
	private final BigDecimal price;
	private final BigDecimal brokerage;
	private final Transaction transaction;
	
	public BoughtSoldAmountCalculator(Transaction transaction) {
		this.transaction = transaction;
		this.quantity = transaction.getQuantity();
		this.price = transaction.getPrice();
		this.brokerage = transaction.getBrokerage();
	}
	
	public BigDecimal calculate() {
		final BigDecimal amount;
		if(transaction instanceof BuyTransaction) {
			amount = new BigDecimal((quantity * price.doubleValue()) + brokerage.doubleValue());
		} else {
			amount = new BigDecimal((quantity * price.doubleValue()) - brokerage.doubleValue());
		}
		
		return BigDecimalHelper.scaleAndRound(amount);
	}

}
