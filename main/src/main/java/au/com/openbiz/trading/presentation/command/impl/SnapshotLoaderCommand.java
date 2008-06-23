package au.com.openbiz.trading.presentation.command.impl;

import au.com.openbiz.trading.logic.manager.security.SecurityPriceManager;
import au.com.openbiz.trading.presentation.command.Command;

public class SnapshotLoaderCommand implements Command {
	
	private SecurityPriceManager securityPriceManager;

	public void execute() {
		securityPriceManager.createSnapshot();
	}

	public void setSecurityPriceManager(
			SecurityPriceManager securityPriceManager) {
		this.securityPriceManager = securityPriceManager;
	}
}
