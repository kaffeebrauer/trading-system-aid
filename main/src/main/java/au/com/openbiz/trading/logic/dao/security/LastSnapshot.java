package au.com.openbiz.trading.logic.dao.security;

import java.math.BigDecimal;

public class LastSnapshot {

	private String securityCode;
	private BigDecimal lastPrice;
	private BigDecimal difference;
	private BigDecimal returnOnCapital;
	private BigDecimal trailingStopLoss;
	private BigDecimal channelTaken;
	
	public BigDecimal getChannelTaken() {
		return channelTaken;
	}
	public void setChannelTaken(BigDecimal channelTaken) {
		this.channelTaken = channelTaken;
	}
	public BigDecimal getDifference() {
		return difference;
	}
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	public BigDecimal getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}
	public BigDecimal getReturnOnCapital() {
		return returnOnCapital;
	}
	public void setReturnOnCapital(BigDecimal returnOnCapital) {
		this.returnOnCapital = returnOnCapital;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public BigDecimal getTrailingStopLoss() {
		return trailingStopLoss;
	}
	public void setTrailingStopLoss(BigDecimal trailingStopLoss) {
		this.trailingStopLoss = trailingStopLoss;
	}
	
	
}
