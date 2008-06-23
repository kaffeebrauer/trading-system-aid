package au.com.openbiz.trading.logic.dao.transaction;

import java.util.List;

import au.com.openbiz.trading.logic.dao.GenericDao;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public interface TransactionDao extends GenericDao<Transaction, Integer> {

	<T> List<T> findAllByTransactionType(Class<T> clazz);

	<T> List<T> findAllByTransactionTypeAndCurrency(Class<T> clazz, String currencyCode);

	Transaction findByReferenceNumber(String referenceNumber);
	
	List<Transaction> findTransactionsByPortfolioId(Integer portfolioId);
	
	List<Transaction> findTransactionsWithoutPortfolioAssignedByCurrency(String currencyCode);
	
	<T> List<T> findTransactionsByPortfolioIdAndTransactionType(Integer portfolioId, Class<T> clazz);
	
	List<Transaction> findTransactionsBySecurity(Security security);

}