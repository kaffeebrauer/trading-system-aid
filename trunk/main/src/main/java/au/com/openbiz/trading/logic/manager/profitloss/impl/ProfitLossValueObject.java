package au.com.openbiz.trading.logic.manager.profitloss.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.transaction.Transaction;

/**
 * Immutable value object to hold 
 */
public class ProfitLossValueObject {

	public ProfitLossValueObject(
			MarketValuationValueObject initialMarketValuation, 
			MarketValuationValueObject finalMarketValuation,
			List<Transaction> transactions,
			BigDecimal profitLoss,
			Security security) {
		this.initialMarketValuation = initialMarketValuation;
		this.finalMarketValuation = finalMarketValuation;
		if(transactions == null) {
			this.transactions = Collections.emptyList();
		} else {
			this.transactions = new ArrayList<Transaction>(transactions);
		}
		this.profitLoss = profitLoss;
		this.security = security;
	}
	
	private final MarketValuationValueObject initialMarketValuation;
	private final MarketValuationValueObject finalMarketValuation;
	private final List<Transaction> transactions;
	private final BigDecimal profitLoss;
	private final Security security;

	public final MarketValuationValueObject getInitialMarketValuation() {
		return initialMarketValuation;
	}
	public final MarketValuationValueObject getFinalMarketValuation() {
		return finalMarketValuation;
	}
	public final List<Transaction> getTransactions() {
		return new ArrayList<Transaction>(transactions);
	}
	public final BigDecimal getProfitLoss() {
		return profitLoss;
	}
	public final Security getSecurity() {
		return security;
	}
}
