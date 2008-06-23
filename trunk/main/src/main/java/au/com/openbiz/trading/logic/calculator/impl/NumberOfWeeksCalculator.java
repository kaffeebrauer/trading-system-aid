package au.com.openbiz.trading.logic.calculator.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import au.com.openbiz.trading.logic.calculator.Calculator;

public class NumberOfWeeksCalculator implements Calculator {
	
	private final Timestamp from;
	private final Timestamp to;
	
	public NumberOfWeeksCalculator(final Timestamp from, final Timestamp to) {
		this.from = from;
		this.to = to;
	}

	public BigDecimal calculate() {
		Long timeDifferenceMillis = to.getTime() - from.getTime();
		Long numberOfWeeks = timeDifferenceMillis / 1000 / 60 / 60 / 24 / 7;
		return new BigDecimal(numberOfWeeks);
	}

}
