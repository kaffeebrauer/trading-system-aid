package au.com.openbiz.trading.presentation.controller.portfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import au.com.openbiz.commons.helper.string.StringHelper;
import au.com.openbiz.trading.logic.manager.currency.CurrencyManager;
import au.com.openbiz.trading.logic.manager.portfolio.PortfolioManager;
import au.com.openbiz.trading.logic.manager.security.SecurityManager;
import au.com.openbiz.trading.persistent.portfolio.Portfolio;
import au.com.openbiz.trading.presentation.form.portfolio.UpdatePortfolioForm;
import au.com.openbiz.trading.presentation.utils.ControllerUtils;


public class UpdatePortfolioController extends AbstractWizardFormController {

	private final String PORTFOLIO_ID_PARAM = "portfolioId";
	
	private PortfolioManager portfolioManager;
	private CurrencyManager currencyManager;
	private SecurityManager securityManager;
	private String cancelView;
	private String successView;
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		UpdatePortfolioForm form = new UpdatePortfolioForm();
		if(request.getParameter("id") != null) { //It is an update
			Integer portfolioId = new Integer(request.getParameter("id"));
			form.setPortfolioId(portfolioId);
			Portfolio portfolio = portfolioManager.findById(portfolioId);
			form.setName(portfolio.getName());
			form.setDescription(portfolio.getDescription());
			form.setCurrencyCode(portfolio.getCurrency().getCode());
			form.setSecurityIds(StringHelper.createCommaDelimitedStringOfIds(portfolio.getSecurities()));
		}
		return form;
	}
	
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		UpdatePortfolioForm form = (UpdatePortfolioForm)command;
		
		Portfolio portfolio = new Portfolio();
		if(form.getPortfolioId() != null && form.getPortfolioId() > 0) {
			portfolio.setId(form.getPortfolioId());
		}
		portfolio.setName(form.getName());
		portfolio.setDescription(form.getDescription());
		
		List<Integer> securityIdList = new ArrayList<Integer>();
		if(form.getSecurityIds() != null && form.getSecurityIds().length() > 0) {
			String[] securityIds = form.getSecurityIds().split(",");
			for(int i=0; i<securityIds.length; i++) {
				securityIdList.add(new Integer(securityIds[i]));
			}
		}
		portfolioManager.saveOrUpdate(portfolio, form.getCurrencyCode(), securityIdList);
		ControllerUtils.publishMessage(request, "The portfolio " + portfolio.getName() + " has been saved successfully.");
		return new ModelAndView(successView);
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request, int page) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("currencies", currencyManager.findAll());
		data.put("securities", securityManager.findSecuritiesWithoutPortfolio());
		if(StringUtils.hasText(request.getParameter(PORTFOLIO_ID_PARAM))) {
			Integer portfolioId = new Integer(request.getParameter(PORTFOLIO_ID_PARAM));
			Portfolio portfolio = new Portfolio();
			portfolio.setId(portfolioId);
			data.put("alreadyAssignedSecurities", securityManager.findByPortfolio(portfolio));
		}
		return data;
	}
	
	@Override
	protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		return new ModelAndView(cancelView);
	}

	public void setPortfolioManager(PortfolioManager portfolioManager) {
		this.portfolioManager = portfolioManager;
	}
	public void setCurrencyManager(CurrencyManager currencyManager) {
		this.currencyManager = currencyManager;
	}
	public void setCancelView(String cancelView) {
		this.cancelView = cancelView;
	}
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	public void setSuccessView(String successView) {
		this.successView = successView;
	}
	
}
