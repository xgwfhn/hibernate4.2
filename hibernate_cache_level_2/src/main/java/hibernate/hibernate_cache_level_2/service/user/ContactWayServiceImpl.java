package hibernate.hibernate_cache_level_2.service.user;

import java.util.List;

import hibernate.hibernate_cache_level_2.dao.ContactWayDao;
import hibernate.hibernate_cache_level_2.entity.user.ContactWay;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/*
 @Cacheable支持如下几个参数：
key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL。例如：
memCachedService.storeUserAddress("user", "BeiJing");
所以对应的key为：service.MemcachedService-storeUserAddress_user_BeiJing
name：存储位置。在本来中remote表示使用memcached服务器。
condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL。
expire：过期时间，单位为秒。  //这个是别的版本 com.springcache.annotation.Cacheable 才有该属性

最后总结一下：当执行到一个被@Cacheable注解的方法时，Spring首先检查condition条件是否满足，如果不满足，执行方法，返回；
如果满足，在name所命名的缓存空间中查找使用key存储的对象，如果找到，将找到的结果返回，如果没有找到执行方法，将方法的返回值以key-value对象的方式存入name缓存中，然后方法返回。
 */

@Service
public class ContactWayServiceImpl implements ContactWayService {
	private static Logger logger = Logger.getLogger(ContactWayServiceImpl.class);
	
	@Autowired
	private ContactWayDao contactWayDao;
	
	@Transactional
	public void addContactWay(ContactWay c) throws Exception {
		contactWayDao.persist(c);  
	}

	/* 
	 	因为底层开启了俩个不同的session,所以俩次都去查询数据库了,实际应用中 可以配置spring的openSessionInView在渲染页面后才关闭来保证  只开始一个session
	 */
	@Cacheable(value = { "testCache" })
	public ContactWay findContactWay(String id)throws Exception{
		ContactWay cw=contactWayDao.find(id);//发了sql语句去查询数据库
		
		//contactWayDao.find(id);//发了sql语句去查询数据库
		return cw;
	}
	
	/*
	 	condition：触发条件，只有满足条件的情况才会缓存，默认为空，支持SpEL
	 	SpEl  :支持 eq("=="),ne("!="),le("<="),lt("<"),gt(">"),ge(">="),div("/"),mod("%"),not("!")，正则表达式及instanceof操作    
	 */
	@Cacheable(value = { "testCache" },condition="#id=='ff808081573198e201573198e50e0000'")
	public ContactWay findContactWay4Condition(String id)throws Exception{
		ContactWay cw=contactWayDao.find(id);
		return cw;
	}
	
	//@CacheEvict
	
	public List<String> findProperty(String hql)throws Exception{
		 contactWayDao.findProperty(hql);
		return contactWayDao.findProperty(hql);
	}
}
