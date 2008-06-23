package au.com.openbiz.trading.logic.manager.security.impl;

import static org.hamcrest.core.Is.is;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import au.com.openbiz.trading.logic.dao.security.SecurityDao;
import au.com.openbiz.trading.logic.dao.security.SecurityPriceDao;
import au.com.openbiz.trading.logic.dao.security.SnapshotDao;
import au.com.openbiz.trading.logic.dao.transaction.TransactionDao;
import au.com.openbiz.trading.logic.manager.security.impl.SecurityPriceManagerImpl;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.security.Snapshot;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.presentation.inputcontroller.MainInputController;

public class SecurityPriceManagerImplTest extends MockObjectTestCase {

	private SecurityPriceManagerImpl securityPriceManager;
	private TransactionDao transactionDaoMock;
	private SecurityDao securityDaoMock;
	private SecurityPriceDao securityPriceDaoMock;
	private SnapshotDao snapshotDaoMock;
	
	private Security security;
	private Integer securityId;
	private Currency currency;
	private BuyTransaction buyTransaction1;
	private BigDecimal priceTr1;
	private Long quantityTr1;
	private BuyTransaction buyTransaction2;
	private BigDecimal priceTr2;
	private Long quantityTr2;
	private int buyTrId1 = -1;
	private int buyTrId2 = -2;
	private SecurityPrice securityPrice;
	
	@Override
	protected void setUp() throws Exception {
		System.setProperty(MainInputController.ENV_VARIABLE, MainInputController.TEST_ENV);
		securityPriceManager = new SecurityPriceManagerImpl();
		transactionDaoMock = mock(TransactionDao.class);
		securityPriceManager.setTransactionDao(transactionDaoMock);
		securityDaoMock = mock(SecurityDao.class);
		securityPriceManager.setSecurityDao(securityDaoMock);
		securityPriceDaoMock = mock(SecurityPriceDao.class);
		securityPriceManager.setSecurityPriceDao(securityPriceDaoMock);
		snapshotDaoMock = mock(SnapshotDao.class);
		securityPriceManager.setSnapshotDao(snapshotDaoMock);
		
		security = new Security("QBE", "AX");
		currency = new Currency("AUD");
		
		priceTr1 = new BigDecimal("1.11");
		quantityTr1 = new Long("111");
		buyTransaction1 = new BuyTransaction(security, currency, priceTr1, quantityTr1);
		buyTransaction1.setId(buyTrId1);
		buyTransaction1.setTimestamp(new Timestamp(System.currentTimeMillis()));
		
		priceTr2 = new BigDecimal("2.22");
		quantityTr2 = new Long("222");
		buyTransaction2 = new BuyTransaction(security, currency, priceTr2, quantityTr2);
		buyTransaction2.setId(buyTrId2);
		buyTransaction2.setTimestamp(new Timestamp(System.currentTimeMillis()));
		
		securityPrice = new SecurityPrice();
		securityPrice.setClosePrice(2.22);
	}
	
	public void testCreateSnapshot() {
		final List<BuyTransaction> buyTrList = Arrays.asList(buyTransaction1, buyTransaction2);
		
		checking(new Expectations() {{
			one (transactionDaoMock).findAllByTransactionTypeAndCurrency(BuyTransaction.class, "AUD"); will(returnValue(buyTrList));
			exactly(2).of(securityDaoMock).findById(securityId); will(returnValue(security));
			one (snapshotDaoMock).findLastSnapshotByBuyTransactionId(buyTrId1); will(returnValue(null));
			one (snapshotDaoMock).findLastSnapshotByBuyTransactionId(buyTrId2); will(returnValue(null));
			exactly(2).of(snapshotDaoMock).saveOrUpdate((Snapshot)with(is(Snapshot.class)));
			exactly(2).of(securityPriceDaoMock).findLastTradeStockPrice(security); will(returnValue(securityPrice));
		}});
		
		securityPriceManager.createSnapshot();
	}
	


}
