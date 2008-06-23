package au.com.openbiz.trading.persistent.dividend;

import java.math.BigDecimal;

import junit.framework.TestCase;
import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;

public class InvestmentPlanDividendTest extends TestCase {
	
	private InvestmentPlanDividend investmentPlanDividend = new InvestmentPlanDividend();
	
	public void testPositiveCashBalance() throws Exception {
		investmentPlanDividend.setNumberOfShares(Long.valueOf(65));
		investmentPlanDividend.setPaymentAmountPerShare(new BigDecimal(1.40));
		investmentPlanDividend.setFrankedPercentage(100);
		investmentPlanDividend.setAllotedShares(2);
		investmentPlanDividend.setAllotmentSharePrice(new BigDecimal(40.4672));
		
		assertEquals(investmentPlanDividend.getTotalAvailableForInvestment(), BigDecimalHelper.scaleAndRound(new BigDecimal(91)));
		assertEquals(investmentPlanDividend.getAmountAppliedToAllotment(), BigDecimalHelper.scaleAndRound(new BigDecimal(80.93)));
		assertEquals(investmentPlanDividend.getCashBalance(), BigDecimalHelper.scaleAndRound(new BigDecimal(10.07)));
	}
	
}
