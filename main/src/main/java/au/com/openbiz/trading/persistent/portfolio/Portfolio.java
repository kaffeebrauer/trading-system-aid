package au.com.openbiz.trading.persistent.portfolio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

@Entity
public class Portfolio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String description;
	private Currency currency;
	private Set<Security> securities = new HashSet<Security>();
	private Set<ShareHolding> shareHoldings = new HashSet<ShareHolding>();

	public Portfolio() {}
	
	public Portfolio(Integer id) {
		this.id = id;
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

	@Column(name="name", nullable=false, unique=true, length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="description", nullable=true, length=250)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="currency_id", nullable=false)
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", this.id)
			.append("name", this.name)
			.append("description", this.description)
			.append("currency", this.currency.getCode())
			.toString();
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="portfolio_fk", nullable=true)
	public Set<Security> getSecurities() {
		return new HashSet<Security>(securities);
	}
	protected void setSecurities(Set<Security> securities) {
		this.securities = securities;
	}
	public void addSecurity(Security security) {
		securities.add(security);
	}
	public void removeSecurity(Security security) {
		securities.remove(security);
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="portfolio_fk", nullable=true)
	public Set<ShareHolding> getShareHoldings() {
		return new HashSet<ShareHolding>(shareHoldings);
	}
	protected void setShareHoldings(Set<ShareHolding> shareHoldings) {
		this.shareHoldings = shareHoldings;
	}
	public void addShareHolding(ShareHolding shareHolding) {
		shareHoldings.add(shareHolding);
	}
	public void removeShareHolding(ShareHolding shareHolding) {
		shareHoldings.remove(shareHolding);
	}
}
