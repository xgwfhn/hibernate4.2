
package hibernate.hibernate_cache_level_2.dao;

import java.util.Date;
import java.util.List;
import java.util.Map; 

import javax.persistence.LockModeType;

public interface BaseDao<B> {
	
	public B find(Object id);
	
	public List<B> createNamedQuery(String queryName);

	public List<B> createNamedQuery(String queryName, Map<String, Object> paramMap);
	
	public List<B> createNamedQueryByNum(String queryName, Map<Integer, Object> paramMap);
	
	public B getReference(Object id);

	public void persist(B obj);

	public B merge(B obj);
	
	public void refresh(B obj);

	public void flush();

	public List<B> findAll() ;
	
    public void remove(B obj);
	
	public void lock(final B obj, final LockModeType type);
		
}