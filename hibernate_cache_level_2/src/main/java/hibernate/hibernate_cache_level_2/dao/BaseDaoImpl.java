
package hibernate.hibernate_cache_level_2.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository 
public class BaseDaoImpl<B> implements BaseDao<B> {
	
	private Class<B> queryClass;
	@PersistenceContext
	public EntityManager entityManager;
	
	
	public BaseDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Class<B> getQueryClass() {
		return queryClass;
	}

	public void setQueryClass(Class<B> queryClass) {
		this.queryClass = queryClass;
	}
			
	public EntityManager getEntityManager() {
	    return this.entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
	    this.entityManager = entityManager;
	}	 
		
	public BaseDaoImpl(Class<B> queryClass) {
		super();
		this.queryClass = queryClass;
	}
	
	public B getNewInstance() {
		try {
			return getQueryClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Error creating new instance of : " + getQueryClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error creating new instance of : " + getQueryClass().getName(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#findById(java.lang.Object)
	 */
	public B find(Object id) {
		return (B) entityManager.find(queryClass, id);
	}
	  public List<B> createQuery(String query) {
		    Query q = entityManager.createQuery(query);
		    return (List<B>) q.getResultList();
		  }
	  
	  public List<B> findAll() {
		    Query q = entityManager.createQuery("from "+queryClass.getName()+" c");
		    return (List<B>) q.getResultList();
		  }
	  

	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#createNamedQuery(java.lang.String)
	 */
	public List<B> createNamedQuery(String queryName) {		
		return entityManager.createNamedQuery(queryName).getResultList();
	}

	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#createNamedQuery(java.lang.String, java.util.Map)
	 */
	public List<B> createNamedQuery(String queryName, Map<String, Object> paramMap) {
		 Query query = entityManager.createNamedQuery(queryName);
		 for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			 query.setParameter(entry.getKey(), entry.getValue());
		 } 
		 return query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#createNamedQueryByNum(java.lang.Integer, java.util.Map)
	 */
	public List<B> createNamedQueryByNum(String queryName, Map<Integer, Object> paramMap) {
		 Query query = entityManager.createNamedQuery(queryName);
		 for (Map.Entry<Integer, Object> entry : paramMap.entrySet()) {
			 query.setParameter(entry.getKey(), entry.getValue());
		 } 
		 
		 return query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#getReference(java.lang.Object)
	 */
	public B getReference(Object id) {
		return entityManager.getReference(queryClass, id);
	}

	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#persist(B)
	 */
	public void  persist(B obj) {
		entityManager.persist(obj);
	}

	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#merge(B)
	 */
	public B merge(B obj) {
		return entityManager.merge(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#refresh(B)
	 */
	public void refresh(B obj) {
		entityManager.refresh(obj);
	}	
	
	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#refresh(B)
	 */
	public void flush() {
		entityManager.flush();
	}	
	

	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#remove(B)
	 */
	public void remove(B obj) {
//		entityManager.merge(obj);
		entityManager.remove(entityManager.merge(obj));
	}
	/* (non-Javadoc)
	 * @see com.hna.wechat.dao.BaseDao#lock(B,LockModeType)
	 */
	public void lock(final B obj, final LockModeType type) {	
		entityManager.lock(obj, type);
	}

		

	
}
