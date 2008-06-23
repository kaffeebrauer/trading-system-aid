package au.com.openbiz.trading.persistent.dividend;

import java.math.BigDecimal;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.persistent.dividend.Dividend;


import junit.framework.TestCase;

public class DividendTest extends TestCase {

	private Dividend dividend = new Dividend();
	
	public void testFullyFrankedDividend() throws Exception {
		dividend.setNumberOfShares(Long.valueOf(115)); //115 shares
		dividend.setPaymentAmountPerShare(new BigDecimal(0.17)); //17 cents
		dividend.setFrankedPercentage(100); //100%
		
		assertEquals(dividend.getUnfrankedAmount(), BigDecimalHelper.scaleAndRound(new BigDecimal(0)));
		assertEquals(dividend.getFrankedAmount(), BigDecimalHelper.scaleAndRound(new BigDecimal(19.55)));
		assertEquals(dividend.getTotalDividends(), BigDecimalHelper.scaleAndRound(new BigDecimal(19.55)));
		assertEquals(dividend.getFrankingCredit(), BigDecimalHelper.scaleAndRound(new BigDecimal(8.38)));
	}
	
	public void testPartiallyFrankedDividend() throws Exception {
		dividend.setNumberOfShares(Long.valueOf(33)); //33 shares
		dividend.setPaymentAmountPerShare(new BigDecimal(0.55)); //55 cents
		dividend.setFrankedPercentage(60); //60%
		
		assertEquals(dividend.getUnfrankedAmount(), BigDecimalHelper.scaleAndRound(new BigDecimal(7.26)));
		assertEquals(dividend.getFrankedAmount(), BigDecimalHelper.scaleAndRound(new BigDecimal(10.89)));
		assertEquals(dividend.getTotalDividends(), BigDecimalHelper.scaleAndRound(new BigDecimal(18.15)));
		assertEquals(dividend.getFrankingCredit(), BigDecimalHelper.scaleAndRound(new BigDecimal(4.67)));
	}
	
	public void testFullyUnfrankedDividend() throws Exception {
		dividend.setNumberOfShares(Long.valueOf(283)); //283 shares
		dividend.setPaymentAmountPerShare(new BigDecimal(0.051)); //5.1 cents
		dividend.setFrankedPercentage(0); //0%
		
		assertEquals(dividend.getUnfrankedAmount(), BigDecimalHelper.scaleAndRound(new BigDecimal(14.43)));
		assertEquals(dividend.getFrankedAmount(), BigDecimalHelper.scaleAndRound(new BigDecimal(0)));
		assertEquals(dividend.getTotalDividends(), BigDecimalHelper.scaleAndRound(new BigDecimal(14.43)));
		assertEquals(dividend.getFrankingCredit(), BigDecimalHelper.scaleAndRound(new BigDecimal(0)));
	}
	
}
