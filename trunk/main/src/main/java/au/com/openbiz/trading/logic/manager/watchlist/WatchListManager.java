package au.com.openbiz.trading.logic.manager.watchlist;

import java.util.Set;

import au.com.openbiz.trading.persistent.watchlist.WatchList;

public interface WatchListManager {

	WatchList findById(Integer id);
	
	Set<WatchList> findAll();
	
	void saveOrUpdate(WatchList watchList);
}
