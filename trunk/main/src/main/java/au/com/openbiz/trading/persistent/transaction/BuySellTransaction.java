package au.com.openbiz.trading.persistent.transaction;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BuySellTransaction implements Serializable {
	
	private static final long serialVersionUID = -4499032455363459508L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@ManyToOne(targetEntity=au.com.openbiz.trading.persistent.transaction.BuyTransaction.class)
	@JoinColumn(name="buy_transaction_fk")
	private BuyTransaction buyTransaction;

	@ManyToOne(targetEntity=au.com.openbiz.trading.persistent.transaction.SellTransaction.class)
	@JoinColumn(name="sell_transaction_fk")
	private SellTransaction sellTransaction;
	
	public BuyTransaction getBuyTransaction() {
		return buyTransaction;
	}
	public void setBuyTransaction(BuyTransaction buyTransaction) {
		this.buyTransaction = buyTransaction;
	}
	public SellTransaction getSellTransaction() {
		return sellTransaction;
	}
	public void setSellTransaction(SellTransaction sellTransaction) {
		this.sellTransaction = sellTransaction;
	}
	public Integer getId() {
		return id;
	}
	protected void setId(Integer id) {
		this.id = id;
	}
	
}
