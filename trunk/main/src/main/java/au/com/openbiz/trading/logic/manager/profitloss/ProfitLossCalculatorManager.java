package au.com.openbiz.trading.logic.manager.profitloss;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import au.com.openbiz.trading.logic.manager.profitloss.impl.ProfitLossValueObject;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.persistent.security.Security;

public interface ProfitLossCalculatorManager {

	ProfitLossValueObject calculateProfitLoss(Date from, Date to, Security security);
	
	List<ProfitLossValueObject> calculateProfitLoss(Date from, Date to, Portfolio portfolio);
	
	BigDecimal calculateTotalProfitLoss(final List<ProfitLossValueObject> profitLossVOs);
}
