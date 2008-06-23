package au.com.openbiz.trading.logic.manager.security;

import java.util.List;

import au.com.openbiz.trading.persistent.security.Snapshot;

public interface SnapshotManager {

	List<Snapshot> findLastNSnapshots(Integer numberOfSnapshots, String securityCode, String securityCountry);
}
