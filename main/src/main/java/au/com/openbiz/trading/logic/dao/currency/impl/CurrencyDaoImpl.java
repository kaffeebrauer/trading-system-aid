package au.com.openbiz.trading.logic.dao.currency.impl;

import java.util.List;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.currency.CurrencyDao;
import au.com.openbiz.trading.persistent.currency.Currency;

public class CurrencyDaoImpl extends AbstractGenericDao<Currency, Integer> implements CurrencyDao {

	public void saveOrUpdate(Currency currency) {
		if (this.findByCode(currency.getCode()) == null) {
			getHibernateTemplate().saveOrUpdate(currency);
		}
	}
	
	public Currency findByCode(final String code) {
		List<Currency> currencies = findByExample(new Currency(code));
		if(currencies != null && currencies.size() == 1) {
			return currencies.get(0);
		}
		return null;
	}
	
}
