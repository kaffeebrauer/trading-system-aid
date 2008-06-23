package au.com.openbiz.trading.logic.calculator;

import java.math.BigDecimal;

import org.jmock.integration.junit3.MockObjectTestCase;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.calculator.impl.ChannelCalculatorImpl;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.technicalanalysis.TechnicalAnalysis;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

/**
 * Black box testing of channel calculator
 */
public class ChannelCalculatorTest extends MockObjectTestCase {

	public void testCalculate() {
		
		final BuyTransaction buyTransaction = new BuyTransaction(null, null, null, null);
		final TechnicalAnalysis technicalAnalysis = new TechnicalAnalysis(
				new BigDecimal(2), new BigDecimal(1), new BigDecimal(3));
		buyTransaction.setTechnicalAnalysis(technicalAnalysis);
		buyTransaction.setPrice(new BigDecimal(2.5));
		
		final SecurityPrice securityPrice = new SecurityPrice();
		securityPrice.setClosePrice(2.8);
		
		Calculator calculator = new ChannelCalculatorImpl(buyTransaction, securityPrice);
		BigDecimal channelTaken = calculator.calculate();
		
		assertEquals("Channel taken should be 15%", BigDecimalHelper.scaleAndRound(new BigDecimal(15)), channelTaken);

	}

}
