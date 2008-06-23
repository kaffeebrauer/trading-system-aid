package au.com.openbiz.trading.presentation.controller.portfolio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.trading.logic.manager.portfolio.PortfolioManager;

public class ListPortfoliosController implements Controller {

	private PortfolioManager portfolioManager;
	
	public ModelAndView handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("portfolios", portfolioManager.findAll());
		return modelAndView;
	}
	
	public void setPortfolioManager(PortfolioManager portfolioManager) {
		this.portfolioManager = portfolioManager;
	}

}
