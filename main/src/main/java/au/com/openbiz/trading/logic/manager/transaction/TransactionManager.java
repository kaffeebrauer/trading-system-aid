package au.com.openbiz.trading.logic.manager.transaction;

import java.util.List;

import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.transaction.BuySellTransaction;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public interface TransactionManager {

	<T> List<T> findAllByTransactionTypeAndCurrency(Class<T> clazz, String currency);
	
	<T> List<T> findAllByTransactionType(Class<T> clazz);
	
	Transaction findByReferenceNumber(String referenceNumber);
	
	Transaction findById(Integer id);
	
	void saveTransactionAndShareHolding(Transaction transaction);
	
	void saveBuySellTransaction(BuySellTransaction buySellTransaction);
	
	List<BuySellTransaction> findAllBuySellTransaction();
	
	List<BuyTransaction> findAllBuyTransactionsThatHaveNotBeenSold(Integer portfolioId);
	
	List<Transaction> findTransactionsWithoutPortfolioByCurrency(String currencyCode);
	
	List<Transaction> findTransactionsByPortfolioId(Integer portfolioId);

	/** Finds transactions by security ordering by transaction date */
	List<Transaction> findTransactionsBySecurity(Security security);
}
