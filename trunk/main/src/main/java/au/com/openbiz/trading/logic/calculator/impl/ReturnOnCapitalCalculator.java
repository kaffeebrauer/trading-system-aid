package au.com.openbiz.trading.logic.calculator.impl;

import java.math.BigDecimal;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.calculator.Calculator;

public class ReturnOnCapitalCalculator implements Calculator {

	private BigDecimal diffBetweenBoughtAndActual;
	private BigDecimal boughtAmount;
	
	public ReturnOnCapitalCalculator(final BigDecimal diffBetweenBoughtAndActual, final BigDecimal boughtAmount) {
		this.diffBetweenBoughtAndActual = diffBetweenBoughtAndActual;
		this.boughtAmount = boughtAmount;
	}
	
	public BigDecimal calculate() {
		return BigDecimalHelper.scaleAndRound(new BigDecimal(diffBetweenBoughtAndActual.doubleValue() / boughtAmount.doubleValue() * 100));
	}

}
