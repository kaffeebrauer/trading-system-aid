package au.com.openbiz.trading.logic.dao.watchlist;

import java.util.Set;

import au.com.openbiz.trading.logic.dao.GenericDao;
import au.com.openbiz.trading.persistent.watchlist.WatchList;

public interface WatchListDao extends GenericDao<WatchList, Integer> {

	public Set<WatchList> findAllUniques();
}
