package au.com.openbiz.trading.logic.dao.security;

import java.util.List;

import au.com.openbiz.trading.logic.dao.GenericDao;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;

public interface SecurityDao extends GenericDao<Security, Integer> {

	Security findByCode(String code, String country);
	
	List<Security> findByPortfolio(Portfolio portfolio);
	
	List<Security> findSecuritiesWithoutPortfolio();
}