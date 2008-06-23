package au.com.openbiz.commons.helper.decimal;

import java.math.BigDecimal;

public class BigDecimalHelper {

	public static final int SCALE = 2;
	public static final int ROUNDING = BigDecimal.ROUND_HALF_EVEN;
	
	public static BigDecimal scaleAndRound(final BigDecimal input) {
		return input.setScale(SCALE, ROUNDING);
	}
	
}
