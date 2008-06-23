package au.com.openbiz.trading.logic.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractGenericDao<T, I extends Serializable> 
	extends HibernateDaoSupport implements GenericDao<T, I> {

	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	protected AbstractGenericDao() {
		this.clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void delete(I id) {
		delete(findById(id));
	}

	public void delete(T t) {
		getHibernateTemplate().delete(t);
	}
	
	public void deleteAll() {
		for(T t : findAll()) {
			delete(t);
		}
	}
	
	public void deleteAll(List<T> listT) {
		for(T t : listT) {
			delete(t);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getHibernateTemplate().loadAll(clazz);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T t) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Example example = Example.create(t).ignoreCase().enableLike(MatchMode.ANYWHERE);
				return session.createCriteria(clazz).add(example).list();
			}
		};
		return (List<T>)getHibernateTemplate().execute(callback);
	}

	@SuppressWarnings("unchecked")
	public T findById(I id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	public void saveOrUpdate(T t) {
		getHibernateTemplate().saveOrUpdate(t);
	}

}
