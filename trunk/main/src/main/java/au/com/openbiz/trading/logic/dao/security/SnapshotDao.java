package au.com.openbiz.trading.logic.dao.security;

import java.util.List;

import au.com.openbiz.trading.logic.dao.GenericDao;
import au.com.openbiz.trading.persistent.security.Snapshot;

public interface SnapshotDao extends GenericDao<Snapshot, Integer> {

	@SuppressWarnings("unchecked")
	public Snapshot findLastSnapshotByBuyTransactionId(Integer buyTransactionId);

	@SuppressWarnings("unchecked")
	public List reportLastSnapshots();

	List<Snapshot> findLastNSnapshots(final Integer numberOfSnapshots, final String securityCode, final String securityCountry);
	
}