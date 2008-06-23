package au.com.openbiz.trading.logic.manager.transaction.impl;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.dao.transaction.TransactionDao;
import au.com.openbiz.trading.logic.manager.shareholding.ShareHoldingManager;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class TransactionManagerImplTest extends MockObjectTestCase {

	private TransactionManagerImpl manager = new TransactionManagerImpl();
	
	private ShareHoldingManager shareHoldingManager;
	private TransactionDao transactionDao;
	
	private Security cba;
	private Currency aud;
	
	@Override
	protected void setUp() throws Exception {
		shareHoldingManager = mock(ShareHoldingManager.class);
		manager.setShareHoldingManager(shareHoldingManager);
		
		transactionDao = mock(TransactionDao.class);
		manager.setTransactionDao(transactionDao);
		
		cba = new Security("CBA", "AX");
		aud = new Currency("AUD");
	}
	
	public void testSaveTransactionAndShareHoldingWithEmptyShareHoldings() throws Exception {
		final BuyTransaction buy100CBA = new BuyTransaction(cba, aud, new BigDecimal(50), Long.valueOf(100));
		final ShareHolding shareHolding = new ShareHolding();
		shareHolding.setSecurity(cba);
		shareHolding.setAmountOfSharesHeld(Long.valueOf(100));
		
		checking(new Expectations() {{
			one (shareHoldingManager).findBySecurity(cba);
			one (shareHoldingManager).saveOrUpdate(shareHolding);
			one (transactionDao).saveOrUpdate(buy100CBA);
		}});
		
		manager.saveTransactionAndShareHolding(buy100CBA);
	}
	
	public void testSaveTransactionAndShareHoldingWithPreviousShareHoldings() throws Exception {
		final ShareHolding before = new ShareHolding();
		before.setSecurity(cba);
		before.setAmountOfSharesHeld(Long.valueOf(20));

		final BuyTransaction buy100CBA = new BuyTransaction(cba, aud, new BigDecimal(50), Long.valueOf(100));
		buy100CBA.setBrokerage(new BigDecimal(20));
		
		final ShareHolding after = new ShareHolding();
		after.setSecurity(cba);
		after.setAmountOfSharesHeld(Long.valueOf(120));
		after.addTransaction(buy100CBA);
		
		checking(new Expectations() {{
			one (shareHoldingManager).findBySecurity(cba); will(returnValue(before));
			one (shareHoldingManager).saveOrUpdate(after);
			one (transactionDao).saveOrUpdate(buy100CBA);
		}});
		
		manager.saveTransactionAndShareHolding(buy100CBA);
	}

}
