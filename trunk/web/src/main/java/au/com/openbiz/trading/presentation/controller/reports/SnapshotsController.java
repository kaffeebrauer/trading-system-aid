package au.com.openbiz.trading.presentation.controller.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.trading.logic.manager.security.SnapshotManager;
import au.com.openbiz.trading.logic.manager.transaction.TransactionManager;
import au.com.openbiz.trading.persistent.security.Snapshot;

public class SnapshotsController implements Controller {

	private SnapshotManager snapshotManager;
	private TransactionManager transactionManager;
	
	private final String SECURITY_CODE = "securityCode";
	private final String SECURITY_COUNTRY = "securityCountry";
	private final String BUY_TRANSACTION_ID = "buyTransactionId";
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		if(request.getParameter(SECURITY_CODE) != null 
				&& request.getParameter(SECURITY_COUNTRY) != null
				&& request.getParameter(BUY_TRANSACTION_ID) != null) {
			
			String securityCode = request.getParameter(SECURITY_CODE).toString();
			String securityCountry = request.getParameter(SECURITY_COUNTRY).toString();
			String buyTransactionId = request.getParameter(BUY_TRANSACTION_ID).toString();
			
			List<Snapshot> snapshots = snapshotManager.findLastNSnapshots(10, securityCode, securityCountry);
			modelAndView.addObject("snapshots", snapshots);
			modelAndView.addObject("buyTransaction", transactionManager.findById(new Integer(buyTransactionId)));
		}
		return modelAndView;
	}

	public void setSnapshotManager(SnapshotManager snapshotManager) {
		this.snapshotManager = snapshotManager;
	}
	
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
