package au.com.openbiz.trading.logic.loader.impl;

import java.math.BigDecimal;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import au.com.openbiz.trading.logic.dao.currency.CurrencyDao;
import au.com.openbiz.trading.logic.loader.AbstractTemplateLoader;
import au.com.openbiz.trading.persistent.currency.Currency;

public class CurrencyLoaderImpl extends AbstractTemplateLoader {

	private static final Logger LOGGER = Logger.getLogger(CurrencyLoaderImpl.class);
	
	private CurrencyDao currencyDao;
	private String inputFile;
	private int NUMBER_OF_FIELDS = 3;
	
	@Override
	protected void createNewObject(StringTokenizer stringTokenizer) {
		if(stringTokenizer.countTokens() != NUMBER_OF_FIELDS) {
			LOGGER.error("Each line should contain only " + NUMBER_OF_FIELDS + " elements.");
		} else {
			String code = stringTokenizer.nextToken();
			BigDecimal fxRate = new BigDecimal(stringTokenizer.nextToken());
			String description = stringTokenizer.nextToken();
			
			Currency currency = new Currency(code);
			currency.setFxRate(fxRate);
			currency.setDescription(description);
			currencyDao.saveOrUpdate(currency);
		}
	}
	
	@Override
	protected String getInputFile() {
		return inputFile;
	}
	
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}
	
}
