package au.com.openbiz.trading.presentation.controller.transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.SellTransaction;

public class ListTransactionsController implements Controller {
	
	private TransactionManager transactionManager;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		if(request.getParameter("transactionType") != null) {
			String transactionType = request.getParameter("transactionType").toString();
			if(transactionType.equalsIgnoreCase("sell")) {
				modelAndView.addObject("transactions", transactionManager.findAllByTransactionType(SellTransaction.class));
			} else {
				modelAndView.addObject("transactions", transactionManager.findAllByTransactionType(BuyTransaction.class));
			}
			modelAndView.addObject("transactionType", transactionType);
		}
		
		return modelAndView;
	}
	
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
