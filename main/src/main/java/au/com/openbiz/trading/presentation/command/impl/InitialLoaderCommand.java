package au.com.openbiz.trading.presentation.command.impl;

import au.com.openbiz.trading.logic.loader.Loader;
import au.com.openbiz.trading.presentation.command.Command;

public class InitialLoaderCommand implements Command {

	private Loader securityLoader;
	private Loader currencyLoader;
	private Loader transactionLoader;
	
	public void execute() {
		currencyLoader.load();
		securityLoader.load();
		transactionLoader.load();
	}

	public void setSecurityLoader(Loader securityLoader) {
		this.securityLoader = securityLoader;
	}
	
	public void setCurrencyLoader(Loader currencyLoader) {
		this.currencyLoader = currencyLoader;
	}
	
	public void setTransactionLoader(Loader transactionLoader) {
		this.transactionLoader = transactionLoader;
	}
}
