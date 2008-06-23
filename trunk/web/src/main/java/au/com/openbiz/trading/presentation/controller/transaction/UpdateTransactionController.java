package au.com.openbiz.trading.presentation.controller.transaction;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;

import au.com.openbiz.commons.helper.date.DateHelper;
import au.com.openbiz.trading.logic.manager.currency.CurrencyManager;
import au.com.openbiz.trading.logic.manager.security.SecurityManager;
import au.com.openbiz.trading.logic.manager.technicalanalysis.TechnicalAnalysisManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.technicalanalysis.TechnicalAnalysis;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.SellTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;
import au.com.openbiz.trading.presentation.form.transaction.UpdateTransactionForm;
import au.com.openbiz.trading.presentation.utils.ControllerUtils;


public class UpdateTransactionController extends CancellableFormController {

	private TransactionManager transactionManager;
	private SecurityManager securityManager;
	private CurrencyManager currencyManager;
	private TechnicalAnalysisManager technicalAnalysisManager;
	
	private final String ACTION_PARAM = "action";
	private final String TRANSACTION_TYPE_PARAM = "transactionType";
	private final String UPDATE_ACTION_PARAM = "update";
	
	private final String BUY_OPERATION = "buy";

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("securities", securityManager.findAll());
		data.put("currencies", currencyManager.findAll());
		return data;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		if (StringUtils.hasText(request.getParameter(ACTION_PARAM))
				&& StringUtils.hasText(request.getParameter(TRANSACTION_TYPE_PARAM))) {
			UpdateTransactionForm form = new UpdateTransactionForm();
			form.setAction(request.getParameter(ACTION_PARAM).toString());
			form.setTransactionType(request.getParameter(TRANSACTION_TYPE_PARAM).toString());
			
			if (form.getAction().equals(UPDATE_ACTION_PARAM)) {
				String id = request.getParameter("id").toString();
				Transaction transaction = transactionManager.findById(new Integer(id));
				form.setTransactionId(transaction.getId());
				form.setSecurityId(transaction.getSecurity().getId());
				form.setQuantity(transaction.getQuantity());
				form.setPrice(transaction.getPrice());
				form.setTimestamp(DateHelper.formatDate(transaction.getTimestamp(), 
						DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
				form.setTimestampMonthYear(DateHelper.formatDate(transaction.getTimestamp(), 
						DateHelper.FORMAT_MONTH_YEAR_WITH_SLASHES, true));
				form.setReferenceNumber(transaction.getReferenceNumber());
				form.setBrokerage(transaction.getBrokerage());
				form.setCurrencyCode(transaction.getCurrency().getCode());
				
				if(form.getTransactionType().equalsIgnoreCase(BUY_OPERATION)) {
					form.setTechnicalAnalysisId(transaction.getTechnicalAnalysis().getId());
					form.setStopLoss(transaction.getTechnicalAnalysis().getStopLoss());
					form.setLowerChannel(transaction.getTechnicalAnalysis().getLowerChannel());
					form.setUpperChannel(transaction.getTechnicalAnalysis().getUpperChannel());
				} 
			}
			return form;
		} else {
			return new UpdateTransactionForm();
		}
		
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		UpdateTransactionForm form = (UpdateTransactionForm) command;
		Transaction transaction;
		if(form.getTransactionId() != null) {
			if(form.getTransactionType().equalsIgnoreCase(BUY_OPERATION)) {
				transaction = (BuyTransaction)transactionManager.findById(form.getTransactionId());
				TechnicalAnalysis technicalAnalysis = technicalAnalysisManager.findById(form.getTechnicalAnalysisId());
				technicalAnalysis.setStopLoss(form.getStopLoss());
				technicalAnalysis.setLowerChannel(form.getLowerChannel());
				technicalAnalysis.setUpperChannel(form.getUpperChannel());
				technicalAnalysisManager.save(technicalAnalysis);
			} else {
				transaction = (SellTransaction)transactionManager.findById(form.getTransactionId());
			}
		} else {
			if(form.getTransactionType().equalsIgnoreCase(BUY_OPERATION)) {
				transaction = new BuyTransaction();
				TechnicalAnalysis technicalAnalysis = new TechnicalAnalysis();
				technicalAnalysis.setStopLoss(form.getStopLoss());
				technicalAnalysis.setLowerChannel(form.getLowerChannel());
				technicalAnalysis.setUpperChannel(form.getUpperChannel());
				technicalAnalysisManager.save(technicalAnalysis);
				transaction.setTechnicalAnalysis(technicalAnalysis);
			} else {
				transaction = new SellTransaction();
			}
			transaction.setSecurity(securityManager.findById(form.getSecurityId()));
			transaction.setCurrency(currencyManager.findByCode(form.getCurrencyCode()));
		}
		
		transaction.setTimestamp(DateHelper.parseDateToTimestamp(form.getTimestamp(), 
				DateHelper.FORMAT_DAY_MONTH_YEAR_WITH_SLASHES, true));
		transaction.setQuantity(form.getQuantity());
		transaction.setPrice(form.getPrice());
		transaction.setReferenceNumber(form.getReferenceNumber());
		transaction.setBrokerage(form.getBrokerage());
		transactionManager.saveTransactionAndShareHolding(transaction);
		
		ControllerUtils.publishMessage(request, "The transaction has been saved successfully.");
		modelAndView.setViewName(getSuccessView());
		return modelAndView;
	}
	
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public void setCurrencyManager(CurrencyManager currencyManager) {
		this.currencyManager = currencyManager;
	}
	
	public void setTechnicalAnalysisManager(TechnicalAnalysisManager technicalAnalysisManager) {
		this.technicalAnalysisManager = technicalAnalysisManager;
	}
}
