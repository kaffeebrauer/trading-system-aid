package au.com.openbiz.trading.logic.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import au.com.openbiz.trading.presentation.inputcontroller.MainInputController;

public abstract class AbstractTemplateLoader implements Loader {

	private static final Logger LOGGER = Logger.getLogger(AbstractTemplateLoader.class);
	
	protected abstract String getInputFile();
	
	protected abstract void createNewObject(StringTokenizer stringTokenizer);
	
	public void load() {
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			LOGGER.debug("[" + this.getClass().getName() +"] loading...");
			fileReader = new FileReader(getInputFileLocation());
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {
				if(!line.startsWith("#")) {
					StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
					createNewObject(stringTokenizer);
				}
			}
			LOGGER.debug("[" + this.getClass().getName() +"] finished loading.");
		} catch (FileNotFoundException fileNotFoundException) {
			LOGGER.error("[" + getInputFile() + "] was not found.", fileNotFoundException);
		} catch (IOException exception) {
			LOGGER.error("While reading [" + getInputFile() + "].", exception);
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				if(fileReader != null) {
					fileReader.close();
				}
			} catch (IOException ioException) {
				LOGGER.error("Failed to close the buffered reader.", ioException);
			}
		}
		
	}
	
	private String getInputFileLocation() {
		StringBuilder inputFileLocation = new StringBuilder(getInputFile());
		if(System.getProperty(MainInputController.ENV_VARIABLE).equals(MainInputController.PROD_ENV)) {
			inputFileLocation.insert(0, System.getProperty("user.dir"));
		} else {
			inputFileLocation.insert(0, "resources/");
		}
		LOGGER.debug("Sourcing from [" + inputFileLocation.toString() + "].");
		return inputFileLocation.toString();
	}
	
}
