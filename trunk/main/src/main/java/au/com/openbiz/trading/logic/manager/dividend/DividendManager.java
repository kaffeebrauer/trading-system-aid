package au.com.openbiz.trading.logic.manager.dividend;

import java.util.List;

import au.com.openbiz.trading.persistent.dividend.Dividend;
import au.com.openbiz.trading.persistent.dividend.InvestmentPlanDividend;
import au.com.openbiz.trading.persistent.security.Security;

public interface DividendManager {

	void saveOrUpdate(Dividend dividend);
	
	List<Dividend> findAll();
	
	Dividend findById(Integer id);
	
	void saveAndTransform(InvestmentPlanDividend investmentPlanDividend, Security security);
	
	void saveAndTransform(Dividend dividend, Security security);
}
