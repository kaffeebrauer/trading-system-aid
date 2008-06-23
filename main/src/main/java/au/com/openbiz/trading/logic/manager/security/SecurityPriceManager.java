package au.com.openbiz.trading.logic.manager.security;

import java.util.Date;
import java.util.List;

import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;

public interface SecurityPriceManager {

	List<SecurityPrice> findStockPricesByDate(Security security, Date startDate, Date endDate);
	
	SecurityPrice findStockPriceForADate(Security security, Date date);

	List<SecurityPrice> findLastNDaysStockPrices(Security security, int days);

	SecurityPrice findLastTradeStockPrice(Security security);

	void createSnapshot();
}