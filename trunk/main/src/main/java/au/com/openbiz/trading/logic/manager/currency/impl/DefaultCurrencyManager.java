package au.com.openbiz.trading.logic.manager.currency.impl;

import java.util.List;

import au.com.openbiz.trading.logic.dao.currency.CurrencyDao;
import au.com.openbiz.trading.logic.manager.currency.CurrencyManager;
import au.com.openbiz.trading.persistent.currency.Currency;

public class DefaultCurrencyManager implements CurrencyManager {

	private CurrencyDao currencyDao;
	
	public List<Currency> findAll() {
		return currencyDao.findAll();
	}
	
	public Currency findByCode(String code) {
		return currencyDao.findByCode(code);
	}
	
	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

}
