package au.com.openbiz.trading.persistent.dividend;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("CASH")
public class Dividend {

	private Integer id;
	private Timestamp paymentDate;
	private BigDecimal paymentAmountPerShare; //Cents
	private Long numberOfShares;
	private Integer frankedPercentage;
	private ShareHolding shareHolding;
	
	private final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	private final BigDecimal FRANKING_CREDIT = new BigDecimal(0.3/0.7);

	@Transient
	public BigDecimal getUnfrankedAmount() {
		BigDecimal unFrankedAmount = 
			getTotalDividends()
				.multiply(ONE_HUNDRED.subtract(getBigDecimalFrankedPercentage()))
				.divide(ONE_HUNDRED);
		return BigDecimalHelper.scaleAndRound(unFrankedAmount);
	}
	
	@Transient
	public BigDecimal getFrankedAmount() {
		BigDecimal frankedAmount = 
			getTotalDividends()
				.multiply(getBigDecimalFrankedPercentage())
				.divide(ONE_HUNDRED);
		return BigDecimalHelper.scaleAndRound(frankedAmount);
	}
	
	@Transient
	public BigDecimal getFrankingCredit() {
		return BigDecimalHelper.scaleAndRound(getFrankedAmount().multiply(FRANKING_CREDIT));
	}
	
	@Transient
	public BigDecimal getTotalDividends() {
		return BigDecimalHelper.scaleAndRound(new BigDecimal((paymentAmountPerShare.doubleValue() * numberOfShares)));
	}
	
	@Transient
	private BigDecimal getBigDecimalFrankedPercentage() {
		return new BigDecimal(frankedPercentage);
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="franked_percentage", nullable=false)
	public Integer getFrankedPercentage() {
		return frankedPercentage;
	}
	public void setFrankedPercentage(Integer frankedPercentage) {
		this.frankedPercentage = frankedPercentage;
	}
	
	@Column(name="number_of_shares", nullable=false)
	public Long getNumberOfShares() {
		return numberOfShares;
	}
	public void setNumberOfShares(Long numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
	
	@Column(name="payment_amount_per_share", nullable=false, precision=15, scale=4)
	public BigDecimal getPaymentAmountPerShare() {
		return paymentAmountPerShare;
	}
	public void setPaymentAmountPerShare(BigDecimal paymentAmount) {
		this.paymentAmountPerShare = paymentAmount;
	}
	
	@Column(name="payment_date", nullable=false)
	public Timestamp getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@ManyToOne
	@JoinColumn(name="shareholding_fk", nullable=false)
	public ShareHolding getShareHolding() {
		return shareHolding;
	}
	public void setShareHolding(ShareHolding shareHolding) {
		this.shareHolding = shareHolding;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((frankedPercentage == null) ? 0 : frankedPercentage.hashCode());
		result = PRIME * result + ((numberOfShares == null) ? 0 : numberOfShares.hashCode());
		result = PRIME * result + ((paymentAmountPerShare == null) ? 0 : paymentAmountPerShare.hashCode());
		result = PRIME * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Dividend other = (Dividend) obj;
		if (frankedPercentage == null) {
			if (other.frankedPercentage != null)
				return false;
		} else if (!frankedPercentage.equals(other.frankedPercentage))
			return false;
		if (numberOfShares == null) {
			if (other.numberOfShares != null)
				return false;
		} else if (!numberOfShares.equals(other.numberOfShares))
			return false;
		if (paymentAmountPerShare == null) {
			if (other.paymentAmountPerShare != null)
				return false;
		} else if (!paymentAmountPerShare.equals(other.paymentAmountPerShare))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		return true;
	}

	
}
