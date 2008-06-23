package au.com.openbiz.trading.logic.dao.transaction.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.transaction.TransactionDao;
import au.com.openbiz.trading.persistent.security.Security;
import au.com.openbiz.trading.persistent.transaction.BuyTransaction;
import au.com.openbiz.trading.persistent.transaction.SellTransaction;
import au.com.openbiz.trading.persistent.transaction.Transaction;

public class TransactionDaoImpl extends AbstractGenericDao<Transaction, Integer> implements TransactionDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Transaction> findAll() {
		return (List<Transaction>)CollectionUtils.union(
				findAllByTransactionType(BuyTransaction.class), findAllByTransactionType(SellTransaction.class));
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAllByTransactionType(Class<T> clazz) {
		return (List<T>)getHibernateTemplate().find("from " + Transaction.class.getName() 
				+ " t where t.class=" + clazz.getSimpleName());
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAllByTransactionTypeAndCurrency(final Class<T> clazz, final String currencyCode) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(clazz)
					.setFetchMode("technicalAnalysis", FetchMode.JOIN)
					.createCriteria("currency")
						.add(Expression.eq("code", currencyCode)).list();
			}
		};
		return (List)getHibernateTemplate().execute(callback);
	}
	
	public Transaction findByReferenceNumber(final String referenceNumber) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String queryString = "from " + Transaction.class.getName() + " t " 
					+ "left join fetch t.sellTransactions " 
					+ "where t.referenceNumber = :referenceNumber";
				return session.createQuery(queryString)
					.setParameter("referenceNumber", referenceNumber)
					.uniqueResult();
			}
		};
		return (Transaction) getHibernateTemplate().execute(callback);
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsByPortfolioId(Integer portfolioId) {
		return (List<Transaction>) getHibernateTemplate().find("from " + Transaction.class.getName()
				+ " t where t.portfolio.id=? ", portfolioId);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findTransactionsByPortfolioIdAndTransactionType(Integer portfolioId, Class<T> clazz) {
		StringBuilder builder = new StringBuilder();
		builder
			.append("from " + Transaction.class.getName() + " t ")
			.append("inner join fetch t.security s ")
			.append("inner join fetch s.portfolio p ")
			.append("where t.class=" + clazz.getSimpleName() + " and ")
			.append("p.id=?");
		
		return (List<T>) getHibernateTemplate().find(builder.toString(), portfolioId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsWithoutPortfolioAssignedByCurrency(String currencyCode) {
		return (List<Transaction>)getHibernateTemplate().find("from " + Transaction.class.getName()
				+ " t where t.portfolio is null and t.currency.code=?", currencyCode);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactionsBySecurity(Security security) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from " + Transaction.class.getName() + " t ")
			.append("where t.security=?");
		return (List<Transaction>)getHibernateTemplate().find(queryString.toString(), security);
	}
}
