package au.com.openbiz.trading.logic.loader.impl;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import au.com.openbiz.trading.logic.dao.security.SecurityDao;
import au.com.openbiz.trading.logic.loader.AbstractTemplateLoader;
import au.com.openbiz.trading.persistent.security.Security;

public class SecurityLoaderImpl extends AbstractTemplateLoader {

	private static final Logger LOGGER = Logger.getLogger(SecurityLoaderImpl.class);

	private SecurityDao securityDao;
	private String inputFile;

	@Override
	protected void createNewObject(StringTokenizer stringTokenizer) {
		if(stringTokenizer.countTokens()!=3) {
			LOGGER.error("Each line should contain only 3 elements.");
		} else {
			String code = stringTokenizer.nextToken();
			String country = stringTokenizer.nextToken();
			String description = stringTokenizer.nextToken();
			Security security = new Security(code, country, description);
			securityDao.saveOrUpdate(security);
		}
	}
	
	@Override
	protected String getInputFile() {
		return inputFile;
	}
	
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public void setSecurityDao(SecurityDao securityDao) {
		this.securityDao = securityDao;
	}
	
}
