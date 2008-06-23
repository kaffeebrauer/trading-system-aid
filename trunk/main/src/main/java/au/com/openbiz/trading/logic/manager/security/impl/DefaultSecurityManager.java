package au.com.openbiz.trading.logic.manager.security.impl;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import au.com.openbiz.trading.logic.dao.security.SecurityDao;
import au.com.openbiz.trading.logic.manager.security.SecurityManager;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;

public class DefaultSecurityManager implements SecurityManager {

	private SecurityDao securityDao;
	
	public SortedSet<Security> findAll() {
		List<Security> securities = securityDao.findAll();
		Collections.sort(securities);
		SortedSet<Security> sortedSetOfSecurities = new TreeSet<Security>(Collections.unmodifiableList(securities));
		return sortedSetOfSecurities;
	}

	public void setSecurityDao(SecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public Security findByCodeAndCountry(String code, String country) {
		return securityDao.findByCode(code, country);
	}
	
	public void save(Security security) {
		securityDao.saveOrUpdate(security);
	}

	public Security findById(Integer id) {
		return securityDao.findById(id);
	}

	public List<Security> findByPortfolio(Portfolio portfolio) {
		List<Security> securities = securityDao.findByPortfolio(portfolio);
		Collections.sort(securities);
		return securities;
	}

	public List<Security> findSecuritiesWithoutPortfolio() {
		List<Security> securities = securityDao.findSecuritiesWithoutPortfolio();
		Collections.sort(securities);
		return securities;
	}
	
	
}
