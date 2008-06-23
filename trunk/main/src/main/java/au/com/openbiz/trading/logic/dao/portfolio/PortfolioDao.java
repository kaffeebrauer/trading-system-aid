package au.com.openbiz.trading.logic.dao.portfolio;

import java.util.Set;

import au.com.openbiz.trading.logic.dao.GenericDao;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;

public interface PortfolioDao extends GenericDao<Portfolio, Integer>{

	Set<Portfolio> findAllUniquePortfolios();
}
