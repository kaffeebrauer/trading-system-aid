package au.com.openbiz.trading.logic.calculator;

import java.math.BigDecimal;

public interface Calculator {

	/**
	 * Performs the necessary calculations depending on the implementation's algorithm
	 * @return A BigDecimal which is the result of this calculation
	 */
	public BigDecimal calculate();
}
