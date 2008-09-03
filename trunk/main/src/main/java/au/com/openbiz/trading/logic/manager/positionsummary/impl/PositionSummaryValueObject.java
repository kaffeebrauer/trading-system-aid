package au.com.openbiz.trading.logic.manager.positionsummary.impl;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.security.Snapshot;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public final class PositionSummaryValueObject implements Serializable, Comparable<PositionSummaryValueObject> {

	private static final long serialVersionUID = 8677722049076301211L;

	private final BuyTransaction buyTransaction;
	private final SecurityPrice securityPrice;
	private final Security security;
	private final Snapshot snapshot;

	public PositionSummaryValueObject(
			BuyTransaction buyTransaction,
			SecurityPrice securityPrice,
			Security security,
			Snapshot snapshot) {
		this.buyTransaction = buyTransaction;
		this.securityPrice = securityPrice;
		this.security = security;
		this.snapshot = snapshot;
	}

	public BuyTransaction getBuyTransaction() {
		return buyTransaction;
	}
	public SecurityPrice getSecurityPrice() {
		return securityPrice;
	}
	public Security getSecurity() {
		return security;
	}
	public Snapshot getSnapshot() {
		return snapshot;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("buyTransaction", buyTransaction)
			.append("securityPrice", securityPrice)
			.append("security", security)
			.append("snapshot", snapshot)
			.toString();
	}

	public int compareTo(PositionSummaryValueObject valueObject) {
		return new CompareToBuilder()
			.append(valueObject.getSnapshot().getDifference(), getSnapshot().getDifference())
			.toComparison();
	}

}
