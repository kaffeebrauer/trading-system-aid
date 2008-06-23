package au.com.openbiz.trading.logic.dao.security;

import java.util.Date;
import java.util.List;

import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.security.SecurityPrice;

public interface SecurityPriceDao {

	// TODO Split Logic and DAOs
	// TODO Centralise URL management
	// TODO Exception treatment must be enhanced
	List<SecurityPrice> findStockPricesByDate(Security security, Date startDate, Date endDate);

	SecurityPrice findStockPriceForADate(Security security, Date date);
	
	SecurityPrice findLastTradeStockPrice(Security security);

}