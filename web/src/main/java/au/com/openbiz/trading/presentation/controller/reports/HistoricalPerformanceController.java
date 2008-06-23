package au.com.openbiz.trading.presentation.controller.reports;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.trading.logic.manager.portfolio.PortfolioManager;
import au.com.openbiz.trading.logic.manager.profitloss.ProfitLossCalculatorManager;
import au.com.openbiz.trading.logic.manager.profitloss.impl.ProfitLossValueObject;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;

public class HistoricalPerformanceController implements Controller {

	private final String PORTFOLIO_ID_PARAM = "portfolioId";
	private final String FROM_PARAM = "from";
	private final String TO_PARAM = "to";
	private final String FROM_MONTH_YEAR_PARAM = "fromMonthYear";
	private final String TO_MONTH_YEAR_PARAM = "toMonthYear";
	
	private ProfitLossCalculatorManager profitLossCalculatorManager;
	private PortfolioManager portfolioManager;
	private String formView;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(formView);
		modelAndView.addObject("portfolios", portfolioManager.findAll());

		List<ProfitLossValueObject> profitLossVOs = null;
		Portfolio portfolio = null;
		Date fromDate = null;
		Date toDate = null;

		if(request.getParameter(FROM_PARAM) != null) {
			String from = request.getParameter(FROM_PARAM);
			fromDate = DateHelper.parseDate(from, DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
			modelAndView.addObject(FROM_PARAM, from);
			modelAndView.addObject(FROM_MONTH_YEAR_PARAM, 
				DateHelper.formatDate(fromDate, DateHelper.FORMAT_MONTH_YEAR_WITH_SLASHES, true));
		}
		
		if(request.getParameter(TO_PARAM) != null) {
			String to = request.getParameter(TO_PARAM);
			toDate = DateHelper.parseDate(to, DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true);
			modelAndView.addObject(TO_PARAM, to);
			modelAndView.addObject(TO_MONTH_YEAR_PARAM, 
					DateHelper.formatDate(toDate, DateHelper.FORMAT_MONTH_YEAR_WITH_SLASHES, true));
		}
		
		if(request.getParameter(PORTFOLIO_ID_PARAM) != null && !request.getParameter(PORTFOLIO_ID_PARAM).equals("0")) {
			Integer portfolioId = new Integer(request.getParameter(PORTFOLIO_ID_PARAM));
			portfolio = portfolioManager.findById(portfolioId);
			modelAndView.addObject(PORTFOLIO_ID_PARAM, portfolioId);
		}
		
		if(fromDate != null && toDate != null && portfolio != null) {
			profitLossVOs = profitLossCalculatorManager.calculateProfitLoss(fromDate, toDate, portfolio);
			modelAndView.addObject("profitLossVOs", profitLossVOs);
			modelAndView.addObject("totalProfitLoss", profitLossCalculatorManager.calculateTotalProfitLoss(profitLossVOs));
		}
		return modelAndView;
	}
	
	public void setProfitLossCalculatorManager(ProfitLossCalculatorManager profitLossCalculatorManager) {
		this.profitLossCalculatorManager = profitLossCalculatorManager;
	}
	public void setPortfolioManager(PortfolioManager portfolioManager) {
		this.portfolioManager = portfolioManager;
	}
	public void setFormView(String formView) {
		this.formView = formView;
	}
}
