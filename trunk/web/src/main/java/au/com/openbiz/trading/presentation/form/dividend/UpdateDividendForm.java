package au.com.openbiz.trading.presentation.form.dividend;

import java.math.BigDecimal;

public class UpdateDividendForm {

	private Integer id;
	private String paymentDate;
	private String paymentMonthYear;
	private BigDecimal paymentAmountPerShare; //Cents
	private Long numberOfShares;
	private Integer frankedPercentage;
	private Boolean isInvestmentPlanDividend;
	private Integer allotedShares;
	private BigDecimal allotmentSharePrice;
	private Integer securityId;
	
	public Integer getSecurityId() {
		return securityId;
	}
	public void setSecurityId(Integer securityId) {
		this.securityId = securityId;
	}
	public Integer getAllotedShares() {
		return allotedShares;
	}
	public void setAllotedShares(Integer allotedShares) {
		this.allotedShares = allotedShares;
	}
	public BigDecimal getAllotmentSharePrice() {
		return allotmentSharePrice;
	}
	public void setAllotmentSharePrice(BigDecimal allotmentSharePrice) {
		this.allotmentSharePrice = allotmentSharePrice;
	}
	public Boolean getIsInvestmentPlanDividend() {
		return isInvestmentPlanDividend;
	}
	public void setIsInvestmentPlanDividend(Boolean isInvestmentPlanDividend) {
		this.isInvestmentPlanDividend = isInvestmentPlanDividend;
	}
	public Integer getFrankedPercentage() {
		return frankedPercentage;
	}
	public void setFrankedPercentage(Integer frankedPercentage) {
		this.frankedPercentage = frankedPercentage;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getNumberOfShares() {
		return numberOfShares;
	}
	public void setNumberOfShares(Long numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
	public BigDecimal getPaymentAmountPerShare() {
		return paymentAmountPerShare;
	}
	public void setPaymentAmountPerShare(BigDecimal paymentAmountPerShare) {
		this.paymentAmountPerShare = paymentAmountPerShare;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentMonthYear() {
		return paymentMonthYear;
	}
	public void setPaymentMonthYear(String paymentMonthYear) {
		this.paymentMonthYear = paymentMonthYear;
	}
	
}
