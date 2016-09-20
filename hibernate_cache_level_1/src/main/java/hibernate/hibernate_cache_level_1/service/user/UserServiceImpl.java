package hibernate.hibernate_cache_level_1.service.user;

import hibernate.hibernate_cache_level_1.dao.UserDao;
import hibernate.hibernate_cache_level_1.entity.user.User;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public void addUser(User u) throws Exception {
		userDao.persist(u);
	}

}
