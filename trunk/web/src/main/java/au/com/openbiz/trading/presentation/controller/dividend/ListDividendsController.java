package au.com.openbiz.trading.presentation.controller.dividend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.trading.logic.manager.dividend.DividendManager;

public class ListDividendsController implements Controller {
	
	private DividendManager dividendManager;

	public ModelAndView handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("dividends", dividendManager.findAll());
		return modelAndView;
	}
	
	public void setDividendManager(DividendManager dividendManager) {
		this.dividendManager = dividendManager;
	}

}
