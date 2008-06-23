package au.com.openbiz.trading.persistent.security;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

@Entity
public class Snapshot implements Serializable {

	private static final long serialVersionUID = 3285979639633125424L;

	private Integer id;
	private BigDecimal lastPrice;
	private BigDecimal marketValue;
	private BigDecimal difference;
	private BigDecimal returnOnCapital;
	private BigDecimal trailingStopLoss;
	private BigDecimal risk;
	private BigDecimal channelTaken;
	private Timestamp timestamp;
	private Integer numberOfWeeksSinceBuy;
	private Integer snapshotNumber;
	private BuyTransaction buyTransaction;
	
	protected Snapshot() {};
	
	/**
	 * Creates a snapshot of the security that has not been sold yet.
	 * @param lastPrice Last price in the market
	 * @param buyTransaction Buy transaction
	 * @param timestamp Time stamp of this snapshot
	 */
	public Snapshot(BigDecimal lastPrice, BuyTransaction buyTransaction, Timestamp timestamp) {
		this.lastPrice = lastPrice;
		this.buyTransaction = buyTransaction;
		this.timestamp = timestamp;
	}
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="buy_transaction_id", nullable=false)
	public BuyTransaction getBuyTransaction() {
		return buyTransaction;
	}

	public void setBuyTransaction(BuyTransaction buyTransaction) {
		this.buyTransaction = buyTransaction;
	}

	@Column(name="channel_taken", nullable=false)
	public BigDecimal getChannelTaken() {
		return channelTaken;
	}
	public void setChannelTaken(BigDecimal channelTaken) {
		this.channelTaken = channelTaken;
	}
	
	@Column(name="difference", nullable=false)
	public BigDecimal getDifference() {
		return difference;
	}
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
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
	
	@Column(name="last_price", nullable=false)
	public BigDecimal getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}
	
	@Column(name="market_value", nullable=false)
	public BigDecimal getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}
	
	@Column(name="number_weeks_since_buy", nullable=false)
	public Integer getNumberOfWeeksSinceBuy() {
		return numberOfWeeksSinceBuy;
	}
	public void setNumberOfWeeksSinceBuy(Integer numberOfWeeksSinceBuy) {
		this.numberOfWeeksSinceBuy = numberOfWeeksSinceBuy;
	}
	
	@Column(name="return_on_capital", nullable=false)
	public BigDecimal getReturnOnCapital() {
		return returnOnCapital;
	}
	public void setReturnOnCapital(BigDecimal returnOnCapital) {
		this.returnOnCapital = returnOnCapital;
	}
	
	@Column(name="risk", nullable=false)
	public BigDecimal getRisk() {
		return risk;
	}
	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}
	
	@Column(name="snapshot_number", 
			nullable=false)
	public Integer getSnapshotNumber() {
		return snapshotNumber;
	}
	public void setSnapshotNumber(Integer snapshotNumber) {
		this.snapshotNumber = snapshotNumber;
	}
	
	@Column(name="timestamp", nullable=false)
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	@Column(name="trailing_stop_loss", nullable=false)
	public BigDecimal getTrailingStopLoss() {
		return trailingStopLoss;
	}
	public void setTrailingStopLoss(BigDecimal trailingStopLoss) {
		this.trailingStopLoss = trailingStopLoss;
	}
	
}
