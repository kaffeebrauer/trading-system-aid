package au.com.openbiz.trading.persistent.dividend;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;

@Entity
@DiscriminatorValue("INVESTMENT")
public class InvestmentPlanDividend extends Dividend {

	private Integer allotedShares;
	private BigDecimal allotmentSharePrice;

	@Transient
	public BigDecimal getTotalAvailableForInvestment() {
		return super.getTotalDividends();
	}
	
	@Transient
	public BigDecimal getAmountAppliedToAllotment() {
		return BigDecimalHelper.scaleAndRound(allotmentSharePrice.multiply(new BigDecimal(allotedShares)));
	}
	
	@Transient
	public BigDecimal getCashBalance() {
		return BigDecimalHelper.scaleAndRound(getTotalAvailableForInvestment().subtract(getAmountAppliedToAllotment()));
	}
	
	@Column(name="alloted_shares", nullable=true)
	public Integer getAllotedShares() {
		return allotedShares;
	}
	public void setAllotedShares(Integer allotedShares) {
		this.allotedShares = allotedShares;
	}
	
	@Column(name="allotment_share_price", nullable=true, precision=15, scale=4)
	public BigDecimal getAllotmentSharePrice() {
		return allotmentSharePrice;
	}
	public void setAllotmentSharePrice(BigDecimal allotmentSharePrice) {
		this.allotmentSharePrice = allotmentSharePrice;
	}
}
