package au.com.openbiz.trading.presentation.inputcontroller;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public final class MainInputController {

	public static final String PROD_ENV = "prod";
	public static final String TEST_ENV = "test";
	public static final String DEV_ENV = "dev";
	public static final String ENV_VARIABLE = "env";
	
	private static final String INPUT_CONTROLLER = "inputController";
	private static final String APPLICATION_CONTEXT_XML = "applicationContext.xml";

	private MainInputController() {}
	
	/**
	 * Main entrance to the presentation layer.
	 */
	public static void main(String[] args) {
		if (!validateArguments(args)) {
			printHelpMessage();
			System.exit(1);
		} else {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
			InputController inputController = (InputController)applicationContext.getBean(INPUT_CONTROLLER);
			inputController.process(args);
		}
	}

	private static void printHelpMessage() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Arguments are not correct. The proper syntax is:\n")
			.append("java -Denv=[dev|prod] -jar tradingsystem-main.jar [initialLoad|snapshot]");
		System.out.println(stringBuilder.toString());
	}

	private static boolean validateArguments(String[] args) {
		String environment = System.getProperty(ENV_VARIABLE);
		/*
		 * Checks whether the amount of arguments is different from 1 or
		 * whether the argument is valid or
		 * whether the env system property has not been set.
		 * In any of those cases will return false which will 
		 * cause to exit the system and print a help message
		 */
		if(args.length != 1 ||
				!StringUtils.hasText(environment) ||
				!Arrays.asList(new String[] {DEV_ENV, PROD_ENV}).contains(environment)
				) {
			return false;
		}
		return true;
	}

}
