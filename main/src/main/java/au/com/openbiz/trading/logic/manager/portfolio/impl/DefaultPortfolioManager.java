package au.com.openbiz.trading.logic.manager.portfolio.impl;

import java.util.List;
import java.util.Set;

import au.com.openbiz.trading.logic.dao.portfolio.PortfolioDao;
import au.com.openbiz.trading.logic.manager.currency.CurrencyManager;
import au.com.openbiz.trading.logic.manager.portfolio.PortfolioManager;
import au.com.openbiz.trading.logic.manager.security.SecurityManager;
import au.com.openbiz.trading.logic.manager.shareholding.ShareHoldingManager;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

public class DefaultPortfolioManager implements PortfolioManager {

	private PortfolioDao portfolioDao;
	private ShareHoldingManager shareHoldingManager;
	private CurrencyManager currencyManager;
	private SecurityManager securityManager;
	
	public Set<Portfolio> findAll() {
		return portfolioDao.findAllUniquePortfolios();
	}

	public void saveOrUpdate(Portfolio newPortfolio, String currencyCode, List<Integer> securityIdList) {
		Portfolio portfolio;
		if(newPortfolio.getId() != null) {
			portfolio = portfolioDao.findById(newPortfolio.getId());
		} else {
			portfolio = new Portfolio();
		}
		
		portfolio.setName(newPortfolio.getName());
		portfolio.setDescription(newPortfolio.getDescription());
		portfolio.setCurrency(currencyManager.findByCode(currencyCode));
		for(Integer securityId : securityIdList) {
			Security security = securityManager.findById(securityId);
			portfolio.addSecurity(security);
			
			ShareHolding shareHolding = shareHoldingManager.findBySecurity(security);
			if(shareHolding != null) {
				portfolio.addShareHolding(shareHolding);
			}
		}
		portfolioDao.saveOrUpdate(portfolio);
	}

	public Portfolio findById(Integer id) {
		return portfolioDao.findById(id);
	}

	public void setPortfolioDao(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}
	public void setShareHoldingManager(ShareHoldingManager shareHoldingManager) {
		this.shareHoldingManager = shareHoldingManager;
	}
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	public void setCurrencyManager(CurrencyManager currencyManager) {
		this.currencyManager = currencyManager;
	}
}
