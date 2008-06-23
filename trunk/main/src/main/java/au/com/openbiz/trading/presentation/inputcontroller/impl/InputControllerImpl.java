package au.com.openbiz.trading.presentation.inputcontroller.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import au.com.openbiz.trading.presentation.command.Command;
import au.com.openbiz.trading.presentation.inputcontroller.InputController;

public class InputControllerImpl implements InputController {

	private Map<String, Command> commandMap = new HashMap<String, Command>();
	private static final Logger LOGGER = Logger.getLogger(InputControllerImpl.class);
	
	public void process(String[] args) {
		String firstArgument = args[0];
		Command command = commandMap.get(firstArgument);
		if(command == null) {
			throw new IllegalArgumentException("[" + firstArgument + "] is not a legal argument.");
		}
		LOGGER.info("About to execute [" + firstArgument + "] command...");
		command.execute();
		LOGGER.info("[" + firstArgument + "] command executed successfully.");
	}

	public void setCommandMap(Map<String, Command> commandMap) {
		this.commandMap = commandMap;
	}
}
