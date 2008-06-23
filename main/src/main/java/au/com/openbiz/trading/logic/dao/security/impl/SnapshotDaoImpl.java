package au.com.openbiz.trading.logic.dao.security.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.security.SnapshotDao;
import au.com.openbiz.trading.persistent.security.Snapshot;

public class SnapshotDaoImpl extends AbstractGenericDao<Snapshot, Integer> implements SnapshotDao {

	@SuppressWarnings("unchecked")
	public List<Snapshot> findLastNSnapshots(final Integer numberOfSnapshots, final String securityCode, final String securityCountry) {
		String queryString = "SELECT snapshot FROM " + Snapshot.class.getName() + " snapshot "
			+ "INNER JOIN FETCH snapshot.buyTransaction buyTransaction "
			+ "INNER JOIN FETCH buyTransaction.security security "
			+ "WHERE security.code = ? "
			+ "	AND security.country = ? "
			+ "   AND DAYOFWEEK(snapshot.timestamp) = 6 "
			+ "ORDER BY snapshot.snapshotNumber DESC ";
		Object[] values = new Object[] {securityCode, securityCountry};
		List<Snapshot> snapshots = getHibernateTemplate().find(queryString, values);
		if(snapshots.size() > numberOfSnapshots) {
			return snapshots.subList(1, numberOfSnapshots);
		}
		return snapshots;
	}
	
	@SuppressWarnings("unchecked")
	public Snapshot findLastSnapshotByBuyTransactionId(final Integer buyTransactionId) {
		return (Snapshot)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String queryString = "from " + Snapshot.class.getName() 
					+ " s where s.buyTransaction.id = :buyTransactionId order by s.snapshotNumber desc";
				return session.createQuery(queryString)
					.setParameter("buyTransactionId", buyTransactionId)
					.setMaxResults(1)
					.uniqueResult();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List reportLastSnapshots() {
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder queryString = new StringBuilder("select ")
				.append("sec.code, ")
				.append("tra.quantity, ")
				.append("tra.price, ")
				.append("sna.last_price, ")
				.append("sna.difference, ")
				.append("sna.return_on_capital, ")
				.append("sna.trailing_stop_loss, ")
				.append("sna.channel_taken, ")
				.append("sna.number_weeks_since_buy ")
				.append("from snapshot sna, security sec, transaction tra, ")
				.append("(select buy_transaction_id, max(snapshot_number) as snapshot_number from snapshot group by 1) snaview ")
				.append("where sna.buy_transaction_id = tra.id and ")
				.append("tra.security_id = sec.id and ")
				.append("sna.buy_transaction_id = snaview.buy_transaction_id and ")
				.append("sna.buy_transaction_id not in (select buy_transaction_fk from buyselltransaction) and ")
				.append("sna.snapshot_number = snaview.snapshot_number ");
				return session.createSQLQuery(queryString.toString()).list();
			}
		});
	}
}
