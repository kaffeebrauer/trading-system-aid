package au.com.openbiz.trading.logic.manager.indicator.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.openbiz.trading.logic.manager.indicator.MovingAverageIndicatorManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;
import au.com.openbiz.trading.persistent.security.SecurityPriceIndicator;


public class MovingAverageIndicatorManagerImpl implements MovingAverageIndicatorManager {
	
	private static Logger LOGGER = Logger.getLogger(MovingAverageIndicatorManagerImpl.class);

	/* (non-Javadoc)
	 * @see au.com.openbiz.trading.logic.manager.indicator.IMovingAverageIndicatorManager#getMovingAverage(au.com.openbiz.trading.persistent.security.Security, int, java.util.List)
	 */
	public List getMovingAverage(Security security, int days, List securityPrices) {
		Collections.reverse(securityPrices);
		List<SecurityPriceIndicator> movingAveragePrices = new ArrayList<SecurityPriceIndicator>();
		SecurityPrice securityPrice;
		double sum = 0;
		double average = 0;
		SecurityPriceIndicator securityPriceIndicator = null;
		
		for(int counter=0; counter<securityPrices.size(); counter++) {
			securityPrice = (SecurityPrice) securityPrices.get(counter);
			sum = sum + securityPrice.getClosePrice();
			if(counter >= days-1) {
				if(counter > days-1) {
					sum = sum - ((SecurityPrice)securityPrices.get(counter-days)).getClosePrice();
				}
				average = sum/days;
				securityPriceIndicator = new SecurityPriceIndicator();
				securityPriceIndicator.setDate(securityPrice.getDate());
				securityPriceIndicator.setValue(average);
				securityPriceIndicator.setSecurity(security);
				LOGGER.debug("Security Price Indicator[" + securityPriceIndicator + "]");
				movingAveragePrices.add(securityPriceIndicator);
			}
		}
		return movingAveragePrices;
	}
}
