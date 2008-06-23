package au.com.openbiz.trading.persistent.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.technicalanalysis.TechnicalAnalysis;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
public abstract class Transaction implements Serializable, Comparable<Transaction> {

	private static final long serialVersionUID = -7522120480637918205L;

	private Integer id;
	private Timestamp timestamp;
	private String description;
	private BigDecimal price;
	private BigDecimal brokerage = BigDecimal.ZERO;
	private Long quantity;
	private Security security;
	private Currency currency;
	private String referenceNumber;
	private TechnicalAnalysis technicalAnalysis;
	
	public Transaction() {}
	
	public Transaction(Integer id) {
		this.id = id;
	}
	
	public Transaction(Security security, Currency currency, BigDecimal price, Long quantity) {
		this.security = security;
		this.currency = currency;
		this.price = price;
		this.quantity = quantity;
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
	
	@Column(name="brokerage", nullable=true)
	public BigDecimal getBrokerage() {
		return brokerage;
	}
	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="currency_id", nullable=false)
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	@Column(name="description", length=50, nullable=true)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="price", nullable=false)
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name="quantity", nullable=false)
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	@Column(name="reference_number", nullable=true, unique=true)
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="security_id", nullable=false)
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="technical_analysis_id", nullable=true)
	public TechnicalAnalysis getTechnicalAnalysis() {
		return technicalAnalysis;
	}
	public void setTechnicalAnalysis(TechnicalAnalysis technicalAnalysis) {
		this.technicalAnalysis = technicalAnalysis;
	}
	
	@Column(name="timestamp", nullable=false)
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean equals(Object object) {
		if(object == this) return true;
		if((object == null) || (object.getClass() != this.getClass())) return false;
		Transaction tran = (Transaction) object;
		return (timestamp==tran.getTimestamp() || (timestamp!=null && timestamp.equals(tran.getTimestamp()))) &&
				(description==tran.getDescription() || (description!=null && description.equals(tran.getDescription()))) &&
				(price==tran.getPrice() || (price!=null && price.equals(tran.getPrice()))) &&
				(brokerage==tran.getBrokerage() || (brokerage!=null && brokerage.equals(tran.getBrokerage()))) &&
				(quantity==tran.getQuantity() || (quantity!=null && quantity.equals(tran.getQuantity()))) &&
				(referenceNumber==tran.getReferenceNumber() || (referenceNumber!=null && referenceNumber.equals(tran.getReferenceNumber())));
	}
	
	public int hashCode() {
		final int PRIME = 31;
		int hash = 7;
		hash = PRIME * hash + (timestamp == null? 0 : timestamp.hashCode());
		hash = PRIME * hash + (description == null? 0 : description.hashCode());
		hash = PRIME * hash + (price == null? 0 : price.hashCode());
		hash = PRIME * hash + (brokerage == null? 0 : brokerage.hashCode());
		hash = PRIME * hash + (quantity == null? 0 : quantity.hashCode());
		hash = PRIME * hash + (referenceNumber == null? 0 : referenceNumber.hashCode());
		return hash;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
			.append("timestamp", timestamp)
			.append("description", description)
			.append("price", price)
			.append("brokerage", brokerage)
			.append("quantity", quantity)
			.append("security", security)
			.append("currency", currency)
			.append("referenceNumber", referenceNumber)
			.append("technicalAnalysis", technicalAnalysis)
			.toString();
	}
	
	public int compareTo(Transaction transaction1) {
		return transaction1.getSecurity().getCode().compareTo(this.getSecurity().getCode());
	}
	
}
