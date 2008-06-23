package au.com.openbiz.trading.logic.dao.shareholding.impl;

import java.util.List;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.shareholding.ShareHoldingDao;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

public class DefaultShareHoldingDao extends AbstractGenericDao<ShareHolding, Integer> implements ShareHoldingDao {

	@SuppressWarnings("unchecked")
	public ShareHolding findBySecurity(Security security) {
		final List<ShareHolding> shareHoldings = getHibernateTemplate()
			.find("from " + ShareHolding.class.getName() + " sh where sh.security.id = ?", security.getId());
		
		if(shareHoldings != null && shareHoldings.size() == 1) {
			return shareHoldings.get(0);
		}
		
		return null;
	}
	
}
