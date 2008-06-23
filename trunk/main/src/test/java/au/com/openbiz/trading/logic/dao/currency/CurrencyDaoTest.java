package au.com.openbiz.trading.logic.dao.currency;

import java.math.BigDecimal;
import java.sql.Timestamp;

import au.com.openbiz.trading.base.IntegrationalTestingBase;
import au.com.openbiz.trading.persistent.currency.Currency;

public class CurrencyDaoTest extends IntegrationalTestingBase {
	
	private Currency aud;
	
	public void testSave() {
		aud = new Currency("AUD");
		aud.setFxRate(new BigDecimal(0.8));
		aud.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
		aud.setDescription("test");
		
		currencyDao.saveOrUpdate(aud);
		
		Currency found = currencyDao.findByCode("AUD");
		assertEquals("Ids should be equal.", found.getId(), aud.getId());
	}

}
