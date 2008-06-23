package au.com.openbiz.trading.logic.dao.portfolio.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.portfolio.PortfolioDao;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;

public class DefaultPortfolioDao 
	extends AbstractGenericDao<Portfolio, Integer> 
	implements PortfolioDao {
	
	@SuppressWarnings("unchecked")
	public Set<Portfolio> findAllUniquePortfolios() {
		return new LinkedHashSet<Portfolio>(getHibernateTemplate()
				.find("from " + Portfolio.class.getName() + " p order by p.name"));
	}

}
