package au.com.openbiz.trading.logic.dao.currency;

import au.com.openbiz.trading.logic.dao.GenericDao;
import au.com.openbiz.trading.persistent.currency.Currency;

public interface CurrencyDao extends GenericDao<Currency, Integer> {

	public Currency findByCode(String code);

}