package hibernate.hibernate_cache_level_2.dao;

import hibernate.hibernate_cache_level_2.entity.user.User;

import java.util.List;

public interface UserDao extends BaseDao<User>  {
	public List<User> queryUser(String openid, String date); 
}
