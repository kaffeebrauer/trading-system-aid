package au.com.openbiz.trading.presentation.controller.watchlist;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;

import au.com.openbiz.commons.helper.string.StringHelper;
import au.com.openbiz.trading.logic.manager.security.SecurityManager;
import au.com.openbiz.trading.logic.manager.watchlist.WatchListManager;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.watchlist.WatchList;
import au.com.openbiz.trading.presentation.form.watchlist.UpdateWatchListForm;

public class UpdateWatchlistController extends CancellableFormController {

	private WatchListManager watchListManager;
	private SecurityManager securityManager;
	
	private final String WATCH_LIST_ID = "id";
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		UpdateWatchListForm form = (UpdateWatchListForm)command;
		
		WatchList watchList = null;
		if(isWatchListUpdate(form)) {
			watchList = watchListManager.findById(form.getWatchListId());
		} else {
			watchList = new WatchList();
		}
		
		watchList.setName(form.getName());
		watchList.setDescription(form.getDescription());
		
		if(form.getSecurityIds() != null && form.getSecurityIds().length() > 0) {
			String[] securityIds = form.getSecurityIds().split(",");
			for(int i=0; i<securityIds.length; i++) {
				Security security = securityManager.findById(new Integer(securityIds[i]));
				watchList.addSecurity(security);
			}
		}
		
		watchListManager.saveOrUpdate(watchList);
		return new ModelAndView(getSuccessView());
	}
	
	private boolean isWatchListUpdate(UpdateWatchListForm form) {
		return form.getWatchListId() != null && !form.getWatchListId().equals(new Integer(0));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		WatchList watchList = null;
		if(request.getParameter(WATCH_LIST_ID) != null) {
			Integer watchListId = new Integer(request.getParameter(WATCH_LIST_ID));
			watchList = watchListManager.findById(watchListId);
			model.put("alreadyAssignedSecurities",	watchList.getSecurities());
		}
		SortedSet<Security> allSecurities = securityManager.findAll();
		if(watchList != null && !watchList.getSecurities().isEmpty()) {
			model.put("securities", CollectionUtils.subtract(allSecurities, watchList.getSecurities()));
		} else {
			model.put("securities", allSecurities);
		}
		return model;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		UpdateWatchListForm form = new UpdateWatchListForm();
		WatchList watchList = null;
		if(request.getParameter(WATCH_LIST_ID) != null) {
			Integer watchListId = new Integer(request.getParameter(WATCH_LIST_ID));
			watchList = watchListManager.findById(watchListId);
			form.setWatchListId(watchListId);
			form.setName(watchList.getName());
			form.setDescription(watchList.getDescription());
			form.setSecurityIds(StringHelper.createCommaDelimitedStringOfIds(watchList.getSecurities()));
		}
		return form;
	}
	
	public void setWatchListManager(WatchListManager watchListManager) {
		this.watchListManager = watchListManager;
	}
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
}
