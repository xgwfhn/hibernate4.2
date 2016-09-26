package hibernate.hibernate_cache_level_2.dao;

import hibernate.hibernate_cache_level_2.entity.user.ContactWay;

import java.util.List;


public interface ContactWayDao extends BaseDao<ContactWay>{
	//public List<ContactWay> queryUser(String openid, String date); 
	public List<String> findProperty(String hql) throws Exception;
}
