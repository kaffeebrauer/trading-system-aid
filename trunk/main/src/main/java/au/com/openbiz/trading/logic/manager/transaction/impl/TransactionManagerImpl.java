package au.com.openbiz.trading.logic.manager.transaction.impl;

import java.util.Collections;
import java.util.List;

import au.com.openbiz.trading.logic.dao.transaction.BuySellTransactionDao;
import au.com.openbiz.trading.logic.dao.transaction.TransactionDao;
import au.com.openbiz.trading.logic.manager.shareholding.ShareHoldingManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;
import au.com.openbiz.trading.persistent.transaction.BuySellTransaction;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public class TransactionManagerImpl implements TransactionManager {

	private TransactionDao transactionDao;
	private ShareHoldingManager shareHoldingManager;
	private BuySellTransactionDao buySellTransactionDao;
	
	public <T> List<T> findAllByTransactionType(Class<T> clazz) {
		return transactionDao.findAllByTransactionType(clazz);
	}

	public <T> List<T> findAllByTransactionTypeAndCurrency(Class<T> clazz,
			String currency) {
		return transactionDao.findAllByTransactionTypeAndCurrency(clazz, currency);
	}

	public Transaction findByReferenceNumber(String referenceNumber) {
		return transactionDao.findByReferenceNumber(referenceNumber);
	}

	public Transaction findById(Integer id) {
		return transactionDao.findById(id);
	}
	
	public void saveTransactionAndShareHolding(Transaction transaction) {
		boolean isNewTransaction = (transaction.getId() == null);
		transactionDao.saveOrUpdate(transaction);
		
		if(isNewTransaction) {
			ShareHolding shareHolding = shareHoldingManager.findBySecurity(transaction.getSecurity());
			
			if(shareHolding == null) {
				shareHolding = new ShareHolding();
				shareHolding.setSecurity(transaction.getSecurity());
				shareHolding.setAmountOfSharesHeld(transaction.getQuantity());
			} else {
				shareHolding.setAmountOfSharesHeld(
						calculateAmountOfSharesHeld(shareHolding.getAmountOfSharesHeld(), transaction.getQuantity(), transaction.getClass()));
			}
			
			shareHolding.addTransaction(transaction);
			shareHoldingManager.saveOrUpdate(shareHolding);
		}
		
	}
	
	private <T extends Transaction> Long calculateAmountOfSharesHeld(final Long amountAlreadyHeld, 
			final Long newAmount, final Class<T> transactionType) {
		Long amountOfSharesHeld = amountAlreadyHeld;
		if(isBuyTransaction(transactionType)) {
			amountOfSharesHeld = amountOfSharesHeld + newAmount;
		} else {
			amountOfSharesHeld = amountOfSharesHeld - newAmount;
		}
		return amountOfSharesHeld;
	}
	
	private <T extends Transaction> boolean isBuyTransaction(final Class<T> transactionType) {
		return transactionType.equals(BuyTransaction.class);
	}
	
	public void saveBuySellTransaction(BuySellTransaction buySellTransaction) {
		buySellTransactionDao.saveOrUpdate(buySellTransaction);
	}
	
	public List<BuySellTransaction> findAllBuySellTransaction() {
		return buySellTransactionDao.findAll();
	}
	
	public List<BuyTransaction> findAllBuyTransactionsThatHaveNotBeenSold(Integer portfolioId) {
		List<BuyTransaction> buyTransactions = transactionDao.findTransactionsByPortfolioIdAndTransactionType(portfolioId, BuyTransaction.class);
		List<BuySellTransaction> buySellTransactions = findAllBuySellTransaction();
		for(BuySellTransaction buySellTransaction : buySellTransactions) {
			if(buyTransactions.contains(buySellTransaction.getBuyTransaction())) {
				buyTransactions.remove(buySellTransaction.getBuyTransaction());
			}
		}
		return buyTransactions;
	}

	/**
	 * This method finds all transactions that have the given currency and then removes the ones 
	 * that already exist in the given portfolio
	 */
	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsWithoutPortfolioByCurrency(String currencyCode) {
		List<Transaction> transactionsWithoutPortfolioAssigned = transactionDao.findTransactionsWithoutPortfolioAssignedByCurrency(currencyCode);
		Collections.sort(transactionsWithoutPortfolioAssigned);
		return transactionsWithoutPortfolioAssigned;
	}
	
	public List<Transaction> findTransactionsByPortfolioId(Integer portfolioId) {
		return transactionDao.findTransactionsByPortfolioId(portfolioId);
	}
	
	public List<Transaction> findTransactionsBySecurity(Security security) {
		return transactionDao.findTransactionsBySecurity(security);
	}
	
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
	public void setShareHoldingManager(ShareHoldingManager shareHoldingManager) {
		this.shareHoldingManager = shareHoldingManager;
	}
	public void setBuySellTransactionDao(BuySellTransactionDao buySellTransactionDao) {
		this.buySellTransactionDao = buySellTransactionDao;
	}
}
