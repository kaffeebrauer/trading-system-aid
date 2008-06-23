package au.com.openbiz.trading.logic.calculator.impl;

import java.math.BigDecimal;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.calculator.Calculator;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.technicalanalysis.TechnicalAnalysis;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class ChannelCalculatorImpl implements Calculator {

	private BuyTransaction buyTransaction;
	private SecurityPrice securityPrice;
	
	/**
	 * Channel calculator constructor
	 * @param buyTransaction Buy transaction
	 * @param securityPrice Security price
	 */
	public ChannelCalculatorImpl(BuyTransaction buyTransaction, SecurityPrice securityPrice) {
		this.buyTransaction = buyTransaction;
		this.securityPrice = securityPrice;
	}
	
	public BigDecimal calculate() {
		//Get the technical analysis from the buy transaction
		TechnicalAnalysis technicalAnalysis = buyTransaction.getTechnicalAnalysis();
		
		if(technicalAnalysis==null) {
			//If no technical analysis is found then return 0 as a default channel taken.
			return new BigDecimal(0);
		}
		
		//Channel width = upper channel - lower channel
		BigDecimal channelWidth = technicalAnalysis.getUpperChannel().subtract(technicalAnalysis.getLowerChannel());
		//If price is negative, then the channel taken will be negative
		BigDecimal priceDifference = new BigDecimal(securityPrice.getClosePrice()).subtract(buyTransaction.getPrice());
		return BigDecimalHelper.scaleAndRound(new BigDecimal(priceDifference.doubleValue() / channelWidth.doubleValue() * 100));
	}
}
