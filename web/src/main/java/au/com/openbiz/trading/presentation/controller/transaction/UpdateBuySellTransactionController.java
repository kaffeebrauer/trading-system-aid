package au.com.openbiz.trading.presentation.controller.transaction;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;

import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.transaction.BuySellTransaction;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.SellTransaction;
import au.com.openbiz.trading.presentation.form.transaction.UpdateBuySellTransactionForm;
import au.com.openbiz.trading.presentation.utils.ControllerUtils;


public class UpdateBuySellTransactionController extends CancellableFormController {

	private static final Logger LOGGER = Logger.getLogger(UpdateBuySellTransactionController.class);
	private TransactionManager transactionManager;
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("buyTransactions", transactionManager.findAllByTransactionType(BuyTransaction.class));
		data.put("sellTransactions", transactionManager.findAllByTransactionType(SellTransaction.class));
		data.put("buySellTransactions", transactionManager.findAllBuySellTransaction());
		return data;
	}
	
	@Override
	/*
	 * The idea is that you can save N buy Transaction against 1 sell Transaction or
	 * 1 buy Transaction against M sell Transactions.
	 * If the user needs N against M, it will have to go N or M times through the saving process.
	 * N and M should be greater than 0.
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		UpdateBuySellTransactionForm form = (UpdateBuySellTransactionForm) command;
		
		String[] buyTransactionIds = form.getBuyTransactionIds().split(",");
		String[] sellTransactionIds = form.getSellTransactionIds().split(",");
		
		if(buyTransactionIds.length > 1 && sellTransactionIds.length > 1) {
			throw new IllegalArgumentException("The contract allows to receive either N buys and 1 sell or 1 buy and N sells, not N and M at the same time.");
		}
		
		if(buyTransactionIds.length == 0 || sellTransactionIds.length == 0) {
			throw new IllegalArgumentException("The contract does not allow to send 0 buys or 0 sells.");
		}
		
		if(buyTransactionIds.length == 1 && sellTransactionIds.length == 1) {
			saveBuySellTransaction(buyTransactionIds[0], sellTransactionIds[0]);
		} 
		else if(buyTransactionIds.length > 1 && sellTransactionIds.length == 1) {
			for(String buyTransactionId : buyTransactionIds) {
				saveBuySellTransaction(buyTransactionId, sellTransactionIds[0]);
			}
		}
		else if(buyTransactionIds.length == 1 && sellTransactionIds.length > 1) {
			for(String sellTransactionId : sellTransactionIds) {
				saveBuySellTransaction(buyTransactionIds[0], sellTransactionId);
			}
		}
		
		ControllerUtils.publishMessage(request, "The buy sell associations has been saved successfully.");
		LOGGER.info("buyTrIds: " + form.getBuyTransactionIds());
		LOGGER.info("sellTrIds: " + form.getSellTransactionIds());
		return new ModelAndView(getSuccessView());
	}
	
	private void saveBuySellTransaction(String buyTransactionId, String sellTransactionId) {
		BuySellTransaction buySellTransaction = new BuySellTransaction();
		buySellTransaction.setBuyTransaction(new BuyTransaction(new Integer(buyTransactionId)));
		buySellTransaction.setSellTransaction(new SellTransaction(new Integer(sellTransactionId)));
		transactionManager.saveBuySellTransaction(buySellTransaction);
	}
	
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
