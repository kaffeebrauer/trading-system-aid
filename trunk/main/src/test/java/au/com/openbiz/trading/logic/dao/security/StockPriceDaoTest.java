package au.com.openbiz.trading.logic.dao.security;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.trading.logic.dao.security.impl.SecurityPriceDaoImpl;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;


public class StockPriceDaoTest extends TestCase {
	private final static Logger LOGGER = Logger.getLogger(StockPriceDaoTest.class);
	
	Security security = new Security("QBE", "AX");
	Transaction buyTrans = new BuyTransaction(security, new Currency("AUD"), new BigDecimal(29.65), 33L);
	
	public void testFindStockPricesByDate() throws Exception {
		SecurityPriceDao stockPriceDao = new SecurityPriceDaoImpl();
		Date startDate = DateHelper.parseDate("19-May-06", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_DASHES, true);
		Date endDate = DateHelper.parseDate("22-Jun-06", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_DASHES, true);
		
		List<SecurityPrice> result = stockPriceDao.findStockPricesByDate(security, startDate, endDate);
		assertNotNull("Result should not be null.", result);
		assertFalse("Result should not be empty.", result.isEmpty());
	}
	
	public void testFindLastTradeStockPrice() throws Exception {
		SecurityPriceDao stockPriceDao = new SecurityPriceDaoImpl();
		SecurityPrice stockPrice = stockPriceDao.findLastTradeStockPrice(security);
		assertNotNull("Stock price should not be null.", stockPrice);
	}
	
	public void testCheckWeeklyPerformance() throws Exception {
		SecurityPriceDao stockPriceDao = new SecurityPriceDaoImpl();
		SecurityPrice price = stockPriceDao.findLastTradeStockPrice(security);
		Double difference = (buyTrans.getQuantity().doubleValue()*price.getClosePrice()) - 
			(buyTrans.getQuantity().doubleValue() * buyTrans.getPrice().doubleValue());
		LOGGER.info(security.getCode() + " Money Difference: " + difference);
		assertNotNull("Money Difference shouldnot be null", difference);
	}
}
