package au.com.openbiz.trading.logic.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, I extends Serializable> {

	void saveOrUpdate(T t);
	
	void delete(T t);
	
	void delete(I id);
	
	void deleteAll();
	
	void deleteAll(List<T> listT);
	
	T findById(I id);
	
	List<T> findAll();
	
	List<T> findByExample(T t);
}
