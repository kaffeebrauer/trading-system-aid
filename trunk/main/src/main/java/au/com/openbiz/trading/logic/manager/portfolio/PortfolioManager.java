package au.com.openbiz.trading.logic.manager.portfolio;

import java.util.List;
import java.util.Set;

import au.com.openbiz.trading.persistent.portfolio.Portfolio;

public interface PortfolioManager {

	void saveOrUpdate(Portfolio portfolio, String currencyCode, List<Integer> securityIdList);
	
	Set<Portfolio> findAll();
	
	Portfolio findById(Integer id);
}
