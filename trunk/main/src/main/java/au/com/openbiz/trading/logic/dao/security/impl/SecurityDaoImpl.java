package au.com.openbiz.trading.logic.dao.security.impl;

import java.util.List;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.security.SecurityDao;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;

public class SecurityDaoImpl extends AbstractGenericDao<Security, Integer> implements SecurityDao {
	
	public Security findByCode(String code, String country) {
		List<Security> securities = findByExample(new Security(code, country));
		if(securities != null && securities.size() == 1) {
			return securities.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Security> findByPortfolio(Portfolio portfolio) {
		return getHibernateTemplate().find("from " + Security.class.getName() + " s "
				+ "where s.portfolio=?", portfolio);
	}

	@SuppressWarnings("unchecked")
	public List<Security> findSecuritiesWithoutPortfolio() {
		return getHibernateTemplate().find("from " + Security.class.getName() + " s "
				+ "where s.portfolio is null");
	}
}
