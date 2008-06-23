package au.com.openbiz.trading.logic.dao.security;

import java.math.BigDecimal;
import java.sql.Timestamp;

import au.com.openbiz.trading.base.IntegrationalTestingBase;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.Snapshot;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class SnapshotDaoTest extends IntegrationalTestingBase {

	public void testSave() throws Exception {
		Security security = new Security("QBE", "AX");
		securityDao.saveOrUpdate(security);
		
		Currency currency = new Currency("AUD");
		currency.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
		currencyDao.saveOrUpdate(currency);
		
		BuyTransaction buyTrans = new BuyTransaction(security, currency, new BigDecimal(29.65), 33L);
		buyTrans.setTimestamp(new Timestamp(System.currentTimeMillis()));
		transactionDao.saveOrUpdate(buyTrans);
		
		Snapshot snapshot = new Snapshot(new BigDecimal(31.21), buyTrans, new Timestamp(System.currentTimeMillis()));
		snapshot.setChannelTaken(new BigDecimal(17.69));
		snapshot.setDifference(new BigDecimal(51.48));
		snapshot.setMarketValue(new BigDecimal(1029.93));
		snapshot.setNumberOfWeeksSinceBuy(new Integer(16));
		snapshot.setReturnOnCapital(new BigDecimal(5.26));
		snapshot.setRisk(new BigDecimal(0));
		snapshot.setSnapshotNumber(new Integer(2));
		snapshot.setTrailingStopLoss(new BigDecimal(29.7));
		snapshotDao.saveOrUpdate(snapshot);
	}
}
