package au.com.openbiz.trading.logic.calculator.impl;

import java.math.BigDecimal;

import au.com.openbiz.trading.logic.calculator.Calculator;

public class MarketValueCalculator implements Calculator {

	private double closePrice;
	private Long quantityBought;
	
	public MarketValueCalculator(final double closePrice, final Long quantityBought) {
		this.closePrice = closePrice;
		this.quantityBought = quantityBought;
	}
	
	public BigDecimal calculate() {
		return new BigDecimal(closePrice * quantityBought);
	}

}
