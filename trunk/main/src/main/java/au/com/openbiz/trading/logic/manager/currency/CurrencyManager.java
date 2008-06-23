package au.com.openbiz.trading.logic.manager.currency;

import java.util.List;

import au.com.openbiz.trading.persistent.currency.Currency;

public interface CurrencyManager {

	List<Currency> findAll();
	
	Currency findByCode(String code);
}
