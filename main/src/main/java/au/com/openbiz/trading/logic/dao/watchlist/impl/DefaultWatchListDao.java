package au.com.openbiz.trading.logic.dao.watchlist.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.watchlist.WatchListDao;
import au.com.openbiz.trading.persistent.watchlist.WatchList;

public class DefaultWatchListDao 
		extends AbstractGenericDao<WatchList, Integer> 
			implements WatchListDao {

	@SuppressWarnings("unchecked")
	public Set<WatchList> findAllUniques() {
		final List<WatchList> watchlists = getHibernateTemplate().find("from " + WatchList.class.getName());
		return new HashSet<WatchList>(watchlists);
	}
	
}
