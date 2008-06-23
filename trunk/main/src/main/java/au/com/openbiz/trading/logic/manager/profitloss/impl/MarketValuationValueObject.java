package au.com.openbiz.trading.logic.manager.profitloss.impl;

import java.math.BigDecimal;
import java.util.Date;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.persistent.security.Security;

public class MarketValuationValueObject {

	public MarketValuationValueObject(Long quantity, Security security, BigDecimal marketPrice, Date valuationDate) {
		this.quantity = quantity;
		this.security = security;
		this.marketPrice = marketPrice;
		if(valuationDate == null) {
			throw new IllegalArgumentException("Valuation date should not be null.");
		}
		this.valuationDate = new Date(valuationDate.getTime());
	}
	
	private final Long quantity;
	private final Security security;
	private final BigDecimal marketPrice;
	private final Date valuationDate;

	public final Long getQuantity() {
		return quantity;
	}
	public final Security getSecurity() {
		return security;
	}
	public final BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public final Date getValuationDate() {
		return valuationDate;
	}
	public final BigDecimal getValuation() {
		if(quantity == 0 || marketPrice.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
		return BigDecimalHelper.scaleAndRound(marketPrice.multiply(new BigDecimal(quantity)));
	}
	
}
