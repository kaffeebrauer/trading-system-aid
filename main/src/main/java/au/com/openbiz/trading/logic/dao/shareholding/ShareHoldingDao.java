package au.com.openbiz.trading.logic.dao.shareholding;

import au.com.openbiz.trading.logic.dao.GenericDao;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

public interface ShareHoldingDao extends GenericDao<ShareHolding, Integer> {

	ShareHolding findBySecurity(Security security);
}
