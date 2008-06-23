package au.com.openbiz.trading.logic.manager.dividend.impl;

import java.util.List;

import au.com.openbiz.trading.logic.dao.dividend.DividendDao;
import au.com.openbiz.trading.logic.manager.dividend.DividendManager;
import au.com.openbiz.trading.logic.manager.shareholding.ShareHoldingManager;
import au.com.openbiz.trading.persistent.dividend.Dividend;
import au.com.openbiz.trading.persistent.dividend.InvestmentPlanDividend;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

public class DefaultDividendManager implements DividendManager {

	private DividendDao dividendDao;
	private ShareHoldingManager shareHoldingManager;
	
	public void saveOrUpdate(Dividend dividend) {
		dividendDao.saveOrUpdate(dividend);
	}

	public List<Dividend> findAll() {
		return dividendDao.findAll();
	}

	public Dividend findById(Integer id) {
		return dividendDao.findById(id);
	}
	
	public void saveAndTransform(InvestmentPlanDividend investmentPlanDividend, Security security) {
		if(isNewDividend(investmentPlanDividend)) {
			ShareHolding shareHolding = shareHoldingManager.findBySecurity(security);
			if(shareHolding == null) throw new IllegalArgumentException("You are trying to save or update a dividend that does not have share holdings."); 
			shareHolding.addDividend(investmentPlanDividend);
			long newAmountOfShares = shareHolding.getAmountOfSharesHeld() + investmentPlanDividend.getAllotedShares();
			shareHolding.setAmountOfSharesHeld(newAmountOfShares);
			shareHolding.setInvestmentPlanDividend(Boolean.TRUE);
			shareHoldingManager.saveOrUpdate(shareHolding);
		}
		saveAndTransform(investmentPlanDividend, Dividend.class, security);
	}
	
	public void saveAndTransform(Dividend dividend, Security security) {
		if(isNewDividend(dividend)) {
			ShareHolding shareHolding = shareHoldingManager.findBySecurity(security);
			if(shareHolding == null) throw new IllegalArgumentException("You are trying to save or update a dividend that does not have share holdings.");
			shareHolding.addDividend(dividend);
			shareHolding.setInvestmentPlanDividend(Boolean.FALSE);
			shareHoldingManager.saveOrUpdate(shareHolding);
		}
		saveAndTransform(dividend, InvestmentPlanDividend.class, security);
	}
	
	private <T extends Dividend>  void saveAndTransform(Dividend dividendToSave, 
			Class<T> transformToClass, Security security) {
		
		if(!isNewDividend(dividendToSave)) {
			Dividend dividend = dividendDao.findById(dividendToSave.getId());
			if(dividend.getClass().equals(transformToClass)) {
				dividendDao.delete(dividend);
				dividendToSave.setId(null);
			}
		}
		this.saveOrUpdate(dividendToSave);
	}
	
	private boolean isNewDividend(Dividend dividend) {
		return dividend.getId() == null;
	}

	public void setDividendDao(DividendDao dividendDao) {
		this.dividendDao = dividendDao;
	}
	public void setShareHoldingManager(ShareHoldingManager shareHoldingManager) {
		this.shareHoldingManager = shareHoldingManager;
	}
}
