package au.com.openbiz.trading.logic.manager.profitloss.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.currency.Currency;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.SellTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public class DefaultProfitLossCalculatorManagerTest extends MockObjectTestCase {

	private DefaultProfitLossCalculatorManager manager = new DefaultProfitLossCalculatorManager();
	
	private TransactionManager transactionManager;
	private SecurityPriceManager securityPriceManager;
	
	private Security cba = new Security("CBA", "AX");
	private Currency aud = new Currency("AUD");
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	@Override
	protected void setUp() throws Exception {
		transactionManager = mock(TransactionManager.class);
		manager.setTransactionManager(transactionManager);
		
		securityPriceManager = mock(SecurityPriceManager.class);
		manager.setSecurityPriceManager(securityPriceManager);
	}
	
	public void testCalculateWithOneBuy() {
		final Date from = DateHelper.parseDate("10/08/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
		final Date to = DateHelper.parseDate("15/08/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
		
		BuyTransaction buy = new BuyTransaction(cba, aud, new BigDecimal(50), Long.valueOf(100));
		buy.setBrokerage(new BigDecimal(19.95));
		buy.setTimestamp(DateHelper.parseDateToTimestamp("12/08/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
		transactions.add(buy);
		
		final SecurityPrice securityPrice = new SecurityPrice();
		securityPrice.setClosePrice(52.00);
		
		checking(new Expectations() {{
			one (transactionManager).findTransactionsBySecurity(cba); will(returnValue(transactions));
			one (securityPriceManager).findStockPriceForADate(cba, to); will(returnValue(securityPrice));
		}});
		
		ProfitLossValueObject profitLossValueObject = manager.calculateProfitLoss(from, to, cba);
		assertEquals(BigDecimalHelper.scaleAndRound(profitLossValueObject.getProfitLoss()), BigDecimalHelper.scaleAndRound(new BigDecimal(180.05)));
	}

	public void testCalculateAfterSomeBuysHaveBeenDone() throws Exception {
		BuyTransaction buy100CBA = new BuyTransaction(cba, aud, new BigDecimal(50), Long.valueOf(100));
		buy100CBA.setBrokerage(new BigDecimal(19.95));
		buy100CBA.setTimestamp(DateHelper.parseDateToTimestamp("12/08/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
		
		BuyTransaction buy50CBA = new BuyTransaction(cba, aud, new BigDecimal(55), Long.valueOf(50));
		buy50CBA.setBrokerage(new BigDecimal(19.95));
		buy50CBA.setTimestamp(DateHelper.parseDateToTimestamp("02/09/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
		
		SellTransaction sell20CBA = new SellTransaction(cba, aud, new BigDecimal(57), Long.valueOf(20));
		sell20CBA.setBrokerage(new BigDecimal(19.95));
		sell20CBA.setTimestamp(DateHelper.parseDateToTimestamp("20/10/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
		
		BuyTransaction buy15CBA = new BuyTransaction(cba, aud, new BigDecimal(53), Long.valueOf(15));
		buy15CBA.setBrokerage(new BigDecimal(19.95));
		buy15CBA.setTimestamp(DateHelper.parseDateToTimestamp("02/12/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
		
		transactions.addAll(Arrays.asList(buy100CBA, buy50CBA, sell20CBA, buy15CBA));
		
		final Date from = DateHelper.parseDate("15/10/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
		final Date to = DateHelper.parseDate("20/12/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
		
		final SecurityPrice initialSecurityPrice = new SecurityPrice();
		initialSecurityPrice.setClosePrice(56.00);
		
		final SecurityPrice finalSecurityPrice = new SecurityPrice();
		finalSecurityPrice.setClosePrice(54.00);
		
		checking(new Expectations() {{
			one (transactionManager).findTransactionsBySecurity(cba); will(returnValue(transactions));
			one (securityPriceManager).findStockPriceForADate(cba, from); will(returnValue(initialSecurityPrice));
			one (securityPriceManager).findStockPriceForADate(cba, to); will(returnValue(finalSecurityPrice));
		}});
		
		ProfitLossValueObject profitLossValueObject = manager.calculateProfitLoss(from, to, cba);
		assertEquals(BigDecimalHelper.scaleAndRound(profitLossValueObject.getProfitLoss()), BigDecimalHelper.scaleAndRound(new BigDecimal(-264.90)));
	}
	
}
