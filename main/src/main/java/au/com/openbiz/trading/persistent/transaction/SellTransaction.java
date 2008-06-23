package au.com.openbiz.trading.persistent.transaction;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;

@Entity
@DiscriminatorValue("SELL")
public class SellTransaction extends Transaction {

	private static final long serialVersionUID = 433559261493424265L;

	private Set<BuySellTransaction> buyTransactions = new HashSet<BuySellTransaction>();
	
	public SellTransaction() {}
	
	public SellTransaction(Integer id) {
		super(id);
	}
	
	/**
	 * Constructor for a transaction type SELL
	 * @param security The security involved in the transaction
	 * @param currency The currency of the transaction
	 * @param price The price paid for each share
	 * @param quantity The quantity of shares sold
	 */
	public SellTransaction(Security security, Currency currency, BigDecimal price, Long quantity) {
		super(security, currency, price, quantity);
	}
	
	@OneToMany()
	@JoinColumn(name="sell_transaction_fk")
	public Set<BuySellTransaction> getBuyTransactions() {
		return buyTransactions;
	}

	public void setBuyTransactions(Set<BuySellTransaction> buyTransactions) {
		this.buyTransactions = buyTransactions;
	}
	
	public void addBuySellTransaction(BuySellTransaction buySellTransaction) {
		this.getBuyTransactions().add(buySellTransaction);
		buySellTransaction.setSellTransaction(this);
	}
}
