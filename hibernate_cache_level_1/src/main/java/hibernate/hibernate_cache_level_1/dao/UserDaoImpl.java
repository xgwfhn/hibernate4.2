
/*
  	@Service用于标注业务层组件
	@Controller用于标注控制层组件（如struts中的action）
	@Repository用于标注数据访问组件，即DAO组件
	@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
	属于spring的内容，Spring2.5为我们引入了组件自动扫描机制，他在类路径下寻找标注了上述注解的类，并把这些类纳入进spring容器中管理。令一种方式技术xml加载
	
	而@Entity 是属于jpa hibernate实现  映射的内容
 */
package hibernate.hibernate_cache_level_1.dao;

import hibernate.hibernate_cache_level_1.entity.user.User;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao  {
	public UserDaoImpl(){
		super(User.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> queryUser(String openid, String date) { 
		String hql="from FlightChangeLuckyUser  where openid=? and substring(createtime,1,10)=?";	 			
		Query q =entityManager.createQuery(hql);
		q.setParameter(1, openid);
		q.setParameter(2, date);
		 List<User> list=q.getResultList();
		
		return list;
	}
}
