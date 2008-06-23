package au.com.openbiz.trading.logic.manager.chart.impl;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;

public class GoogleChartManagerTest extends MockObjectTestCase {

	private GoogleChartManager impl = new GoogleChartManager("http://chart.apis.google.com/chart?", "cht=", "chs=", "chd=t:", "chl=");
	private TransactionManager transactionManager;
	private SecurityPriceManager securityPriceManager;
	
	private Portfolio portfolio = new Portfolio();
	private GoogleChartType chartType = GoogleChartType.PIE_3D;
	private String size = "300x125";
	
	@Override
	protected void setUp() throws Exception {
		transactionManager = mock(TransactionManager.class);
		impl.setTransactionManager(transactionManager);
		
		securityPriceManager = mock(SecurityPriceManager.class);
		impl.setSecurityPriceManager(securityPriceManager);
	}
	
	public void testIllegalPortfolioArgument() throws Exception {
		try {
			impl.buildChartUrl(portfolio, chartType, size);
			fail("An exception should be thrown.");
		} catch (Throwable e) {}
	}
	
	public void testNoEnoughTransactionsException() throws Exception {
		portfolio.setId(1);
		try {
			impl.buildChartUrl(portfolio, chartType, size);
			fail("An exception should be thrown.");
		} catch (Throwable e) {}
	}
	
	public void testBuildChartUrlCorrectly() throws Exception {
		final Integer portfolioId = 1;
		portfolio.setId(portfolioId);
		
		final List<BuyTransaction> buys = new ArrayList<BuyTransaction>();
		final Security bhp = new Security("BHP", "AX");
		BuyTransaction buyBhp = new BuyTransaction();
		buyBhp.setSecurity(bhp);
		buyBhp.setQuantity(100l);
		buys.add(buyBhp);
		
		final Security cba = new Security("CBA", "AX");
		BuyTransaction buyCba = new BuyTransaction();
		buyCba.setSecurity(cba);
		buyCba.setQuantity(100l);
		buys.add(buyCba);
		
		final SecurityPrice bhpPrice = new SecurityPrice();
		bhpPrice.setClosePrice(60);
		
		final SecurityPrice cbaPrice = new SecurityPrice();
		cbaPrice.setClosePrice(40);
		
		checking(new Expectations() {{
			one(transactionManager).findAllBuyTransactionsThatHaveNotBeenSold(portfolioId); will(returnValue(buys));
			one(securityPriceManager).findLastTradeStockPrice(bhp); will(returnValue(bhpPrice));
			one(securityPriceManager).findLastTradeStockPrice(cba); will(returnValue(cbaPrice));
		}});
		
		String resultUrl = impl.buildChartUrl(portfolio, chartType, size);
		assertNotNull(resultUrl);
		assertEquals(resultUrl, "http://chart.apis.google.com/chart?cht=p3&chs=300x125&chd=t:40.00,60.00&chl=CBA.AX|BHP.AX&");
	}
}
