package au.com.openbiz.trading.logic.dao.dividend.impl;

import java.util.List;

import au.com.openbiz.trading.logic.dao.AbstractGenericDao;
import au.com.openbiz.trading.logic.dao.dividend.DividendDao;
import au.com.openbiz.trading.persistent.dividend.Dividend;

public class DefaultDividendDao 
	extends AbstractGenericDao<Dividend, Integer> 
	implements DividendDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Dividend> findAll() {
		StringBuilder queryString = new StringBuilder();
		queryString.append("from Dividend ");
		return (List<Dividend>)getHibernateTemplate().find(queryString.toString());
	}
}
