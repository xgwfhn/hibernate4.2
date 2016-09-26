package hibernate.hibernate_cache_level_2.service.user;

import hibernate.hibernate_cache_level_2.entity.user.ContactWay;

import java.util.List;



public interface ContactWayService {
	public void addContactWay(ContactWay c) throws Exception;
	public ContactWay findContactWay(String id)throws Exception;
	
	/**
	 * 查询普通属性,hibernate 1级缓存不会不会缓存   只缓存实体查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<String> findProperty(String hql)throws Exception;
	
	/**按条件缓存单个实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ContactWay findContactWay4Condition(String id)throws Exception;
}
