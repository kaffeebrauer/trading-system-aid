package au.com.openbiz.trading.presentation.controller.dividend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.commons.helper.decimal.BigDecimalHelper;
import au.com.openbiz.trading.logic.manager.dividend.DividendManager;
import au.com.openbiz.trading.logic.manager.security.SecurityManager;
import au.com.openbiz.trading.persistent.dividend.Dividend;
import au.com.openbiz.trading.persistent.dividend.InvestmentPlanDividend;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.presentation.form.dividend.UpdateDividendForm;
import au.com.openbiz.trading.presentation.utils.ControllerUtils;


public class UpdateDividendController extends CancellableFormController {
	
	private SecurityManager securityManager;
	private DividendManager dividendManager;
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, 
			Object object, BindException bindException) throws Exception {
		UpdateDividendForm form = (UpdateDividendForm)object;

		if(form.getIsInvestmentPlanDividend()) {
			saveInvestmentPlanDividend(form);
			ControllerUtils.publishMessage(request, "Investment Plan Dividend has been successfully saved.");
		} else {
			saveDividend(form);
			ControllerUtils.publishMessage(request, "Dividend has been successfully saved.");
		}
		
		return new ModelAndView(getSuccessView());
	}
	
	private void saveInvestmentPlanDividend(final UpdateDividendForm form) {
		InvestmentPlanDividend investmentPlanDividend = new InvestmentPlanDividend();
		investmentPlanDividend.setId(form.getId());
		populateDividend(investmentPlanDividend, form);
		investmentPlanDividend.setAllotedShares(form.getAllotedShares());
		investmentPlanDividend.setAllotmentSharePrice(form.getAllotmentSharePrice());
		Security security = new Security();
		security.setId(form.getSecurityId());
		dividendManager.saveAndTransform(investmentPlanDividend, security);
	}
	
	private void saveDividend(final UpdateDividendForm form) {
		Dividend dividend = new Dividend();
		dividend.setId(form.getId());
		populateDividend(dividend, form);
		Security security = new Security();
		security.setId(form.getSecurityId());
		dividendManager.saveAndTransform(dividend, security);
	}
	
	private void populateDividend(Dividend dividend, final UpdateDividendForm form) {
		dividend.setFrankedPercentage(form.getFrankedPercentage());
		dividend.setNumberOfShares(form.getNumberOfShares());
		dividend.setPaymentAmountPerShare(form.getPaymentAmountPerShare().setScale(4, BigDecimalHelper.ROUNDING));
		dividend.setPaymentDate(DateHelper.parseDateToTimestamp(form.getPaymentDate(), 
				DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest arg0) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("securities", securityManager.findAll());
		return data;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		UpdateDividendForm form = new UpdateDividendForm();
		final String dividendId = request.getParameter("dividendId");
		if(StringUtils.hasText(dividendId)) {
			Dividend dividend = dividendManager.findById(new Integer(dividendId));
			form.setId(dividend.getId());
			form.setFrankedPercentage(dividend.getFrankedPercentage());
			form.setNumberOfShares(dividend.getNumberOfShares());
			form.setPaymentAmountPerShare(dividend.getPaymentAmountPerShare().setScale(4, BigDecimalHelper.ROUNDING));
			form.setPaymentDate(DateHelper.formatDate(dividend.getPaymentDate(), 
					DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
			form.setPaymentMonthYear(DateHelper.formatDate(dividend.getPaymentDate(), 
					DateHelper.FORMAT_MONTH_YEAR_WITH_SLASHES, true));
			form.setSecurityId(dividend.getShareHolding().getSecurity().getId());
			
			if (dividend instanceof InvestmentPlanDividend) {
				InvestmentPlanDividend investmentPlanDividend = (InvestmentPlanDividend) dividend;
				form.setAllotedShares(investmentPlanDividend.getAllotedShares());
				form.setAllotmentSharePrice(investmentPlanDividend.getAllotmentSharePrice());
				form.setIsInvestmentPlanDividend(Boolean.TRUE);
			} else {
				form.setIsInvestmentPlanDividend(Boolean.FALSE);
			}
		}
		return form;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}	
	public void setDividendManager(DividendManager dividendManager) {
		this.dividendManager = dividendManager;
	}
}
