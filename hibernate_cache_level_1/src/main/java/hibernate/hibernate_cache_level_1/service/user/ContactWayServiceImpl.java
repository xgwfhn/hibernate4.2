package hibernate.hibernate_cache_level_1.service.user;

import java.util.List;

import hibernate.hibernate_cache_level_1.dao.ContactWayDao;
import hibernate.hibernate_cache_level_1.entity.user.ContactWay;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
	public ContactWay findContactWay(String id)throws Exception{
		ContactWay cw=contactWayDao.find(id);//发了sql语句去查询数据库
		
		ContactWay cw1=contactWayDao.find(id);//发了sql语句去查询数据库
		return cw;
	}
	
	public List<String> findProperty(String hql)throws Exception{
		 contactWayDao.findProperty(hql);
		return contactWayDao.findProperty(hql);
	}
}
