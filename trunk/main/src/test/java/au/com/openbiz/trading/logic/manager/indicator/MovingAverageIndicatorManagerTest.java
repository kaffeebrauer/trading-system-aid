package au.com.openbiz.trading.logic.manager.indicator;

import java.util.List;

import au.com.openbiz.trading.base.IntegrationalTestingBase;
import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;

public class MovingAverageIndicatorManagerTest extends IntegrationalTestingBase {

	protected SecurityPriceManager securityPriceManager;
	protected MovingAverageIndicatorManager movingAverageIndicatorManager;
	
	
	public void testGetMovingAverage() throws Exception {
		Security security = new Security("GGAL", "BA");
		int days = 100;
		List<SecurityPrice> securityPrices = securityPriceManager.findLastNDaysStockPrices(security, days);
		int movingAverageWindow = 20;
		List result = movingAverageIndicatorManager.getMovingAverage(security, movingAverageWindow, securityPrices);
		assertNotNull("Result should not be null.", result);
	}
}
