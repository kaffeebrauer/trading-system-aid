package au.com.openbiz.trading.logic.manager.security.impl;

import java.util.List;

import au.com.openbiz.trading.logic.dao.security.SnapshotDao;
import au.com.openbiz.trading.logic.manager.security.SnapshotManager;
import au.com.openbiz.trading.persistent.security.Snapshot;

public class DefaultSnapshotManager implements SnapshotManager {

	private SnapshotDao snapshotDao;
	
	public List<Snapshot> findLastNSnapshots(Integer numberOfSnapshots, String securityCode, String securityCountry) {
		return snapshotDao.findLastNSnapshots(numberOfSnapshots, securityCode, securityCountry);
	}
	
	public void setSnapshotDao(SnapshotDao snapshotDao) {
		this.snapshotDao = snapshotDao;
	}

}
