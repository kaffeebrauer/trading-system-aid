package au.com.openbiz.trading.logic.manager.watchlist.impl;

import java.util.Set;

import au.com.openbiz.trading.logic.dao.watchlist.WatchListDao;
import au.com.openbiz.trading.logic.manager.watchlist.WatchListManager;
import au.com.openbiz.trading.persistent.watchlist.WatchList;

public class DefaultWatchListManager implements WatchListManager {

	private WatchListDao watchListDao;
	
	public Set<WatchList> findAll() {
		return watchListDao.findAllUniques();
	}

	public WatchList findById(Integer id) {
		return watchListDao.findById(id);
	}

	public void saveOrUpdate(WatchList watchList) {
		watchListDao.saveOrUpdate(watchList);
	}

	public void setWatchListDao(WatchListDao watchListDao) {
		this.watchListDao = watchListDao;
	}
}
