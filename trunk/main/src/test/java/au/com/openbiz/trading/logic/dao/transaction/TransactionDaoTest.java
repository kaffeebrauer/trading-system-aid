package au.com.openbiz.trading.logic.dao.transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import au.com.openbiz.trading.base.IntegrationalTestingBase;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.transaction.BuySellTransaction;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.SellTransaction;

public class TransactionDaoTest extends IntegrationalTestingBase {
	
	private Currency aud;
	private Security qbe;
	private BuyTransaction buy;
	private SellTransaction sell;
	private BuySellTransaction buySellTransaction;
	
	private void createCurrencyAUD() {
		aud = new Currency("AUD");
		aud.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
		currencyDao.saveOrUpdate(aud);
	}

	private void createSecurityQBE() {
		qbe = new Security("QBE", "AX");
		securityDao.saveOrUpdate(qbe);
	}
	
	private void createBuyTransaction() {
		buy = new BuyTransaction(qbe, aud, new BigDecimal(2.45), 30L);
		buy.setBrokerage(new BigDecimal(20.0));
		buy.setTimestamp(new Timestamp(System.currentTimeMillis()));
		transactionDao.saveOrUpdate(buy);
	}
	
	private void createSellTransaction() {
		sell = new SellTransaction(qbe, aud, new BigDecimal(2.78), 30L);
		sell.setBrokerage(new BigDecimal(20.0));
		sell.setTimestamp(new Timestamp(System.currentTimeMillis()));
		transactionDao.saveOrUpdate(sell);
	}
	
	private void createBuySellTransaction() {
		buySellTransaction = new BuySellTransaction();
		buySellTransaction.setSellTransaction(sell);
		buySellTransaction.setBuyTransaction(buy);
		buySellTransactionDao.saveOrUpdate(buySellTransaction);
	}
	
	public void testSave() throws Exception {
		createCurrencyAUD();
		createSecurityQBE();

		createBuyTransaction();
		BuyTransaction buyFound = (BuyTransaction)transactionDao.findById(buy.getId());
		assertEquals("Found buy and recently saved should have same id.", buyFound.getId(), buy.getId());
		
		createSellTransaction();
		SellTransaction sellFound = (SellTransaction)transactionDao.findById(sell.getId());
		assertEquals("Found sell and recently saved should have same id.", sellFound.getId(), sell.getId());
		
		createBuySellTransaction();
		BuySellTransaction buySellFound = (BuySellTransaction)buySellTransactionDao.findById(buySellTransaction.getId());
		assertEquals("Buy sell should be found", buySellFound.getId(), buySellTransaction.getId());
	}
	
	public void testFindByCurrency() throws Exception {
		createCurrencyAUD();
		createSecurityQBE();
		createSellTransaction();
		
		List<SellTransaction> audSellTransactions = transactionDao.findAllByTransactionTypeAndCurrency(SellTransaction.class, "AUD");
		assertNotNull("Sell transactions should not be null", audSellTransactions);
		assertFalse("Sell transactions should be greater than 0", audSellTransactions.isEmpty());
	}
	
	public void testFindByType() throws Exception {
		createCurrencyAUD();
		createSecurityQBE();
		createBuyTransaction();
		createSellTransaction();
		
		List<SellTransaction> transactions = transactionDao.findAllByTransactionType(SellTransaction.class);
		assertNotNull("Sell transactions should not be null", transactions);
		assertFalse("Sell transactions should be greater than 0", transactions.isEmpty());
	}
}
