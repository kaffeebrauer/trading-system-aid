package au.com.openbiz.trading.presentation.controller.reports;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.transaction.BuySellTransaction;

public class ClosedTransactionsController implements Controller {

	private TransactionManager transactionManager;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		final List<BuySellTransaction> buySellTransactions = transactionManager.findAllBuySellTransaction();
		List<ClosedTransactionValueObject> closedTransactionVOs = new ArrayList<ClosedTransactionValueObject>();
		for(BuySellTransaction buySellTransaction : buySellTransactions) {
			closedTransactionVOs.add(new ClosedTransactionValueObject(buySellTransaction));
		}
		modelAndView.addObject("closedTransactions", closedTransactionVOs);
		return modelAndView;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
