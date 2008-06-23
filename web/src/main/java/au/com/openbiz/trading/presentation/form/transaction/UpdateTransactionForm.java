package au.com.openbiz.trading.presentation.form.transaction;

import java.math.BigDecimal;

public class UpdateTransactionForm {

	private Integer transactionId;
	private Integer securityId;
	private Long quantity;
	private BigDecimal price;
	private String timestamp;
	private String timestampMonthYear;
	private String referenceNumber;
	private BigDecimal brokerage;
	private String currencyCode;
	private Integer technicalAnalysisId;
	private BigDecimal stopLoss;
	private BigDecimal lowerChannel;
	private BigDecimal upperChannel;
	private String transactionType;
	private String action;
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getSecurityId() {
		return securityId;
	}
	public void setSecurityId(Integer securityId) {
		this.securityId = securityId;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public BigDecimal getBrokerage() {
		return brokerage;
	}
	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(BigDecimal stopLoss) {
		this.stopLoss = stopLoss;
	}
	public BigDecimal getLowerChannel() {
		return lowerChannel;
	}
	public void setLowerChannel(BigDecimal lowerChannel) {
		this.lowerChannel = lowerChannel;
	}
	public BigDecimal getUpperChannel() {
		return upperChannel;
	}
	public void setUpperChannel(BigDecimal upperChannel) {
		this.upperChannel = upperChannel;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getTechnicalAnalysisId() {
		return technicalAnalysisId;
	}
	public void setTechnicalAnalysisId(Integer technicalAnalysisId) {
		this.technicalAnalysisId = technicalAnalysisId;
	}
	public String getTimestampMonthYear() {
		return timestampMonthYear;
	}
	public void setTimestampMonthYear(String timestampMonthYear) {
		this.timestampMonthYear = timestampMonthYear;
	}
	

}
