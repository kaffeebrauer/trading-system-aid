package au.com.openbiz.trading.persistent.shareholding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

import au.com.openbiz.trading.persistent.dividend.Dividend;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.transaction.Transaction;

@Entity
public class ShareHolding {

	private Integer id;
	private Long amountOfSharesHeld;
	private Security security;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	private List<Dividend> dividends = new ArrayList<Dividend>();
	private Boolean hasInvestmentPlanDividends = Boolean.FALSE;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="amount_of_shares_held", nullable=false)
	public Long getAmountOfSharesHeld() {
		return amountOfSharesHeld;
	}
	public void setAmountOfSharesHeld(Long amountOfSharesHeld) {
		this.amountOfSharesHeld = amountOfSharesHeld;
	}
	
	@ManyToOne
	@JoinColumn(name="security_fk", nullable=false, unique=true)
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	
	@OneToMany
	@JoinColumn(name="shareholding_fk", nullable=true)
	public List<Transaction> getTransactions() {
		return new ArrayList<Transaction>(transactions);
	}
	protected void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
	public void removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
	}
	
	@OneToMany(mappedBy="shareHolding")
	@JoinColumn(name="shareholding_fk", nullable=true)
	public List<Dividend> getDividends() {
		return new ArrayList<Dividend>(dividends);
	}
	protected void setDividends(List<Dividend> dividends) {
		this.dividends = dividends;
	}
	public void addDividend(Dividend dividend) {
		dividends.add(dividend);
		dividend.setShareHolding(this);
	}
	public void removeDividend(Dividend dividend) {
		dividends.remove(dividend);
	}
	
	
	@Column( name="has_investment_plan_dividends", nullable=false )
	public Boolean getInvestmentPlanDividend() {
		return hasInvestmentPlanDividends;
	}
	public void setInvestmentPlanDividend(Boolean investmentPlanDividend) {
		this.hasInvestmentPlanDividends = investmentPlanDividend;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amountOfSharesHeld == null) ? 0 : amountOfSharesHeld.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final ShareHolding other = (ShareHolding) obj;
		if (amountOfSharesHeld == null) {
			if (other.amountOfSharesHeld != null) return false;
		} else if (!amountOfSharesHeld.equals(other.amountOfSharesHeld))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
			.append("amountOfSharesHeld", amountOfSharesHeld)
			.append("securityId", security.getId())
			.toString();
	}
	
	
}
