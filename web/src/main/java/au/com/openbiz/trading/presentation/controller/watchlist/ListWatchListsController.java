package au.com.openbiz.trading.presentation.controller.watchlist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import au.com.openbiz.trading.logic.manager.watchlist.WatchListManager;

public class ListWatchListsController implements Controller {

	private WatchListManager watchListManager;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("watchLists", watchListManager.findAll());
		return modelAndView;
	}

	public void setWatchListManager(WatchListManager watchListManager) {
		this.watchListManager = watchListManager;
	}
}
