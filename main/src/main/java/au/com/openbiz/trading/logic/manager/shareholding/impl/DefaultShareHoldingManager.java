package au.com.openbiz.trading.logic.manager.shareholding.impl;

import au.com.openbiz.trading.logic.dao.shareholding.ShareHoldingDao;
import au.com.openbiz.trading.logic.manager.shareholding.ShareHoldingManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

public class DefaultShareHoldingManager implements ShareHoldingManager {

	private ShareHoldingDao shareHoldingDao;
	
	public ShareHolding findBySecurity(Security security) {
		return shareHoldingDao.findBySecurity(security);
	}

	public void saveOrUpdate(ShareHolding shareHolding) {
		shareHoldingDao.saveOrUpdate(shareHolding);
	}
	
	public void setShareHoldingDao(ShareHoldingDao shareHoldingDao) {
		this.shareHoldingDao = shareHoldingDao;
	}
}
