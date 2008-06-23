package au.com.openbiz.trading.presentation.controller.reports;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.trading.logic.manager.chart.ChartManager;
import au.com.openbiz.trading.logic.manager.chart.impl.GoogleChartType;
import au.com.openbiz.trading.logic.manager.portfolio.PortfolioManager;
import au.com.openbiz.trading.logic.manager.positionsummary.PositionSummaryManager;
import au.com.openbiz.trading.logic.manager.positionsummary.impl.PositionSummaryTransferObject;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;

public class PositionSummaryController implements Controller {

	private PositionSummaryManager positionSummaryManager;
	private PortfolioManager portfolioManager;
	private ChartManager chartManager;
	private String formView;
	
	private final String PORTFOLIO_ID_PARAM = "portfolioId";

	public ModelAndView handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addAllObjects(referenceData(request));
		modelAndView.setViewName(formView);
		return modelAndView;
	}
	
	private Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("portfolios", portfolioManager.findAll());
		
		if(request.getParameter(PORTFOLIO_ID_PARAM) == null || request.getParameter(PORTFOLIO_ID_PARAM).equals("0")) {
			return model;
		}
		
		Integer portfolioId = new Integer(request.getParameter(PORTFOLIO_ID_PARAM));
		model.put(PORTFOLIO_ID_PARAM, portfolioId);
		
		PositionSummaryTransferObject positionSummaryTransferObject = positionSummaryManager
			.calculatePositionSummary(new Portfolio(portfolioId));
		
		model.put("chartUrl", buildChartUrl(portfolioId));
		model.put("positionSummaryContainerList", positionSummaryTransferObject.getPositionSummaryValueObject());
		model.put("marketValueTotal", positionSummaryTransferObject.getMarketValueTotal());
		model.put("differenceTotal", positionSummaryTransferObject.getDifferenceTotal());
		model.put("boughtAmountTotal", positionSummaryTransferObject.getBoughtAmountTotal());
		model.put("rocAverage", positionSummaryTransferObject.getRocAverage());
		
		return model;
	}
	
	private String buildChartUrl(final Integer portfolioId) {
		return chartManager.buildChartUrl(new Portfolio(portfolioId), GoogleChartType.PIE_3D, "300x125");
	}

	public void setPositionSummaryManager(PositionSummaryManager positionSummaryManager) {
		this.positionSummaryManager = positionSummaryManager;
	}
	public void setPortfolioManager(PortfolioManager portfolioManager) {
		this.portfolioManager = portfolioManager;
	}
	public void setFormView(String formView) {
		this.formView = formView;
	}
	public void setChartManager(ChartManager chartManager) {
		this.chartManager = chartManager;
	}

}
