package au.com.openbiz.trading.logic.manager.shareholding;

import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.shareholding.ShareHolding;

public interface ShareHoldingManager {

	void saveOrUpdate(ShareHolding shareHolding);
	
	ShareHolding findBySecurity(Security security);
}
