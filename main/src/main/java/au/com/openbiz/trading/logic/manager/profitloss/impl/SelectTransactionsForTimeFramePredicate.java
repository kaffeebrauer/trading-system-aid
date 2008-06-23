package au.com.openbiz.trading.logic.manager.profitloss.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.collections.Predicate;

import au.com.openbiz.trading.persistent.transaction.Transaction;

public class SelectTransactionsForTimeFramePredicate implements Predicate {

	private Date from;
	private Date to;
	
	public SelectTransactionsForTimeFramePredicate(final Date from, final Date to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean evaluate(Object object) {
		Transaction transaction = (Transaction)object;
		return transaction.getTimestamp().compareTo(new Timestamp(this.from.getTime())) > 0 
			&& transaction.getTimestamp().compareTo(new Timestamp(this.to.getTime())) <= 0;
	}

}
