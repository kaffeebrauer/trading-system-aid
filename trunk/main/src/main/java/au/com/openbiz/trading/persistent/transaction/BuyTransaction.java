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
@DiscriminatorValue("BUY")
public class BuyTransaction extends Transaction {

	private static final long serialVersionUID = -1717356446083506038L;

	private Set<BuySellTransaction> sellTransactions = new HashSet<BuySellTransaction>();
	
	public BuyTransaction() {}
	
	public BuyTransaction(Integer id) {
		super(id);
	}
	
	/**
	 * Constructor for a transaction type BUY
	 * @param security The security involved in the transaction
	 * @param currency The currency of the transaction
	 * @param price The price paid for each share
	 * @param quantity The quantity of shares bought
	 */
	public BuyTransaction(Security security, Currency currency, BigDecimal price, Long quantity) {
		super(security, currency, price, quantity);
	}

	@OneToMany()
	@JoinColumn(name="buy_transaction_fk")
	public Set<BuySellTransaction> getSellTransactions() {
		return sellTransactions;
	}
	public void setSellTransactions(Set<BuySellTransaction> sellTransactions) {
		this.sellTransactions = sellTransactions;
	}
	public void addBuySellTransaction(BuySellTransaction buySellTransaction) {
		this.getSellTransactions().add(buySellTransaction);
		buySellTransaction.setBuyTransaction(this);
	}
	
}
