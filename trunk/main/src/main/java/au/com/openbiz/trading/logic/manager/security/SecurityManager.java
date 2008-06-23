package au.com.openbiz.trading.logic.manager.security;

import java.util.List;
import java.util.SortedSet;

import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;

public interface SecurityManager {

	SortedSet<Security> findAll();
	
	Security findByCodeAndCountry(String code, String country);
	
	void save(Security security);
	
	Security findById(Integer id);
	
	List<Security> findByPortfolio(Portfolio portfolio);
	
	List<Security> findSecuritiesWithoutPortfolio();
}
