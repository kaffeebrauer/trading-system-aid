package au.com.openbiz.trading.logic.manager.security.impl;

import java.util.Date;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.presentation.inputcontroller.MainInputController;
import au.com.openbiz.trading.persistent.security.SecurityPrice;

public class SecurityPriceManagerImplIntegrationTest extends AbstractDependencyInjectionSpringContextTests {

	protected SecurityPriceManager securityPriceManager;
	private Security cba = new Security("CBA", "AX");
	
	public SecurityPriceManagerImplIntegrationTest() {
		setPopulateProtectedVariables(true);
		System.setProperty(MainInputController.ENV_VARIABLE, MainInputController.TEST_ENV);
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[]{"applicationContext.xml"};
	}
	
	public void testFindStockPriceForADate() throws Exception {
		Date dateInThePast = DateHelper.parseDate("02/11/2007", DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
		SecurityPrice securityPrice = securityPriceManager.findStockPriceForADate(cba, dateInThePast);
		assertEquals(securityPrice.getClosePrice(), 61.20d);
	}

}
