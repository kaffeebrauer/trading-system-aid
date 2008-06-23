package au.com.openbiz.trading.logic.dao.dividend.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import au.com.openbiz.trading.base.IntegrationalTestingBase;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.dividend.Dividend;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class DefaultDividendDaoTest extends IntegrationalTestingBase {

	private BuyTransaction buyTransaction;
	private ShareHolding qbeShareHolding;
	private final String QBE_CODE = "QBE";
	
	private void createBuyTransaction() {
		Currency aud = new Currency("AUD");
		aud.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
		currencyDao.saveOrUpdate(aud);

		Security qbe = new Security(QBE_CODE, "AX");
		securityDao.saveOrUpdate(qbe);
		
		buyTransaction = new BuyTransaction(qbe, aud, new BigDecimal(2.45), 30L);
		buyTransaction.setBrokerage(new BigDecimal(20.0));
		buyTransaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
		transactionDao.saveOrUpdate(buyTransaction);
		
		qbeShareHolding = new ShareHolding();
		qbeShareHolding.setAmountOfSharesHeld(30L);
		qbeShareHolding.setInvestmentPlanDividend(Boolean.FALSE);
		qbeShareHolding.setSecurity(qbe);
		shareHoldingDao.saveOrUpdate(qbeShareHolding);
	}

	public void testCrud() {
		createBuyTransaction();
		
		Dividend dividend1 = new Dividend();
		dividend1.setNumberOfShares(Long.valueOf(115));
		dividend1.setFrankedPercentage(100);
		dividend1.setPaymentAmountPerShare(new BigDecimal(0.56));
		dividend1.setPaymentDate(new Timestamp(System.currentTimeMillis()));
		dividend1.setShareHolding(qbeShareHolding);
		dividendDao.saveOrUpdate(dividend1);
		
		List<Dividend> dividends = dividendDao.findAll();
		assertNotNull(dividends);
		assertEquals(dividends.size(), 1);
		
		Dividend dividend = dividends.get(0);
		assertNotNull(dividend);
	}

}
