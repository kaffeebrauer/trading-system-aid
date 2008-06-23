package au.com.openbiz.trading.logic.manager.indicator;

import java.util.List;

import au.com.openbiz.trading.persistent.security.Security;

public interface MovingAverageIndicatorManager {

	public List getMovingAverage(Security security, int days,
			List securityPrices);

}