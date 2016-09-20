package hibernate.hibernate_cache_level_1.dao;

import hibernate.hibernate_cache_level_1.entity.user.User;

import java.util.List;

public interface UserDao extends BaseDao<User>  {
	public List<User> queryUser(String openid, String date); 
}
