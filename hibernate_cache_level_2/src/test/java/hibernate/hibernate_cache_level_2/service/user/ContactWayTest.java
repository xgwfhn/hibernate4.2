package hibernate.hibernate_cache_level_2.service.user;

import hibernate.hibernate_cache_level_2.dao.ContactWayDao;
import hibernate.hibernate_cache_level_2.entity.user.ContactWay;
import hibernate.hibernate_cache_level_2.entity.user.Contacts;
import hibernate.hibernate_cache_level_2.service.user.ContactWayService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;




/**springmvc和hibernate整合配置   http://blog.csdn.net/yangwenxue_admin/article/details/52423759?locationNum=12
 * 参考  http://blog.csdn.net/feihong247/article/details/7828143
 * 需要junit4.jar  spring-text.jar  spring-context.jar
 * @author Administrator
 *
 *AbstractTransactionalJUnit4SpringContextTests提供了数据库自动回滚，也就是说测试前和测试后数据库是一样的
   AbstractJUnit4SpringContextTests不提供数据库自动回滚，测试会破坏数据库。
 * 创建一个测试用的类，推荐名称为 “被测试类名称 + Test”。
　　测试类应该继承与 AbstractJUnit4SpringContextTests 或 AbstractTransactionalJUnit4SpringContextTests
　　对于 AbstractJUnit4springcontextTests 和 AbstractTransactionalJUnit4SpringContextTests 类的选择：
　　如果再你的测试类中，需要用到事务管理（比如要在测试结果出来之后回滚测试内容），就可以使用AbstractTransactionalJUnit4SpringTests类。事务管理的使用方法和正常使用Spring事务管理是一样的。再此需要注意的是，如果想要使用声明式事务管理，即使用AbstractTransactionalJUnitSpringContextTests类，请在applicationContext.xml文件中加入transactionManager bean：
   <bean id="transactionManager"
	    class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	    <property name="dataSource" ref="dataSource" />
   </bean>
         如果没有添加上述bean，将会抛出NoSuchBeanDefinitionException，指明 No bean named 'transactionManager' is definded.
   	异常:Type mismatch: cannot convert from Class<SpringJUnit4ClassRunner> to Class<? extends Runner>
   	 解决:这是由于博主用的spring3.0,junit.4.4,发现spring不支持junit.4.4这个版本，只需把junit升级到更高版本就不会出现这个错误了。      
   	log4j:WARN No appenders could be found for logger (org.springframework.test.context.junit4.SpringJUnit4ClassRunner).
             解决:@ContextConfiguration(locations = "classpath:springmvc.xml")   如果locations的路径不对 则报该错误或者 UserDao userDao不能注入时
      
            全局事务测试   操作俩个不同的数据库(mysql,oracle  转账例子) 需要设置 jpa的全局事务 
    hibernate jpa和springmvc 整合   http://ll-feng.iteye.com/blog/1908511 
    
              为什么什么编译的时候也会自动执行测试的方法         解决 http://www.cnblogs.com/zhoubang521/p/5200335.html
    spring data jpa http://www.cnblogs.com/xuyuanjia/p/5707681.html          
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc.xml")
//@ContextConfiguration(locations = "classpath:spring-config.xml")
public class ContactWayTest extends AbstractJUnit4SpringContextTests {
	
	private ApplicationContext ac=new ClassPathXmlApplicationContext("springmvc.xml");
	//private ApplicationContext ac=new ClassPathXmlApplicationContext("spring-config.xml");
	@Resource
    private ContactWayService contactWayService;
    
    /*
      	在多的一端加入外键维护关系的缺点，就是在插入多的一端后  要循环更新 外键  如
      	Hibernate: insert into t_contact_way (qq, detailed_address, email, neturl, other_contact_way, portraiture, whchat, zip_code, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)
		Hibernate: insert into t_contacts (qq, email, mobile, name, post, sex, telephone, id) values (?, ?, ?, ?, ?, ?, ?, ?)
		Hibernate: insert into t_contacts (qq, email, mobile, name, post, sex, telephone, id) values (?, ?, ?, ?, ?, ?, ?, ?)
		Hibernate: update t_contacts set contact_way_id=? where id=?
		Hibernate: update t_contacts set contact_way_id=? where id=?
     */
	//缓存信息统计
	@Test
	public void chcacheTotalTest(){
		//System.out.println(ac.getBean("sessionFactory"));
		EntityManagerFactoryInfo entityManagerFactoryInfo =
				(EntityManagerFactoryInfo) applicationContext.getBean("entityManagerFactory");
				EntityManagerFactory emf = entityManagerFactoryInfo.getNativeEntityManagerFactory();
				EntityManagerFactoryImpl emfImp = (EntityManagerFactoryImpl)emf;
				System.out.println(emfImp.getSessionFactory().getStatistics());
	}
	
	@Test
	public void sessionFactoryTest(){
		//System.out.println(ac.getBean("sessionFactory"));
		EntityManagerFactoryInfo entityManagerFactoryInfo =
				(EntityManagerFactoryInfo) applicationContext.getBean("entityManagerFactory");
				EntityManagerFactory emf = entityManagerFactoryInfo.getNativeEntityManagerFactory();
				EntityManagerFactoryImpl emfImp = (EntityManagerFactoryImpl)emf;
				System.out.println(emfImp.getSessionFactory().getStatistics());
	}
	
	@Test
	public void chcacheTest(){
		EntityManagerFactory  factory=(EntityManagerFactory) ac.getBean("entityManagerFactory");
		EntityManager s=factory.createEntityManager();
		ContactWay sw=s.find(ContactWay.class, "ff808081573198e201573198e50e0000");
//		Query query = s.createQuery("select q FROM ContactWay q  where q.id=?");
//		query.setParameter(1, "ff808081573198e201573198e50e0000");
//	    query.setHint("org.hibernate.cacheable", true);
//	    query.getResultList();
		//s.close();
		s.flush();
		s.clear();
		//EntityManager s1=factory.createEntityManager();
		ContactWay sw1=s.find(ContactWay.class, "ff808081573198e201573198e50e0000");
//		Query query1 = s1.createQuery("select q FROM ContactWay q  where q.id=?");
//		query1.setParameter(1, "ff808081573198e201573198e50e0000");
//	    query1.setHint("org.hibernate.cacheable", true);
//	    query1.getResultList();
		s.close();
		
//		EntityManager s0=s1.createEntityManager();
//		s0.createQuery("from ContactWay");
//		s0.close();
	}
	
    @Test
    public void saveTest() { 
    	 ContactWay u=new  ContactWay();
    	 u.setDetailedAddress("海南省陵水");
    	 u.setEmail("xgwfhn@qq.com");
    	 u.setNeturl("www.hainan.net");
    	 Contacts c=new Contacts();
    	 c.setName("黑鸡陪");
    	 c.setEmail("888888@qq.com"); 
    	 c.setMobile("1376789987");
    	 
    	 Contacts c1=new Contacts();
    	 c1.setName("张三");
    	 c1.setEmail("888888@qq.com");
    	 c1.setMobile("1376789987");
    	 
    	 Set<Contacts> sets=new HashSet<Contacts>();
    	 sets.add(c);
    	 sets.add(c1);
    	 
    	 u.setContactsSet(sets);
    	 try {
			contactWayService.addContactWay(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 //contactWayDao.persist(u);//如果实体设置级联关系cascade=CascadeType.PERSIST,只有调用EntityManager的persist才会有效果,而其他的插入方法是无效的,级联的其他关系  也一样,都要调用EntityManager的方法才生效
    }
    
    /*
       	测试二级缓存  是sessionFacotry级别的  可以跨越多个session,所以测试必须在一个sessionFactory内,contactWayService.findContactWay
       	调用后  底层会关闭session
       	设置缓存对象的时间有效期，看看超出该时间后  是否 会重新查询数据库
     */
   @Test
    public void findTest() { 
    	//异常: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.xgw.crm.entity.user.ContactWay.contactsSet, could not initialize proxy - no Session
    	//需要加入 slf4j-api-1.6.1.jar ehcache-core-2.1.0.jar ehcache-spring-annotations-1.1.2.jar
	    //slf4j-log4j12-1.6.1.jar spring-context-support-4.0.6.RELEASE.jar
	    //第一次查询后   该查询对应的数据库记录 此时已经改变时 ，不会发出sql去查询，只能等待该条记录的缓存数据失效时  才会再次发sql查询  所以存在脏数据的情况  使用该缓存  用户要能接受该结果
	   ContactWay cw=null;
		try {
			cw = contactWayService.findContactWay("ff808081573198e201573198e50e0000");
			Thread.sleep(12000);
			System.out.println("-------------begin");
		    contactWayService.findContactWay("ff808081573198e201573198e50e0000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
   
   //按条件缓存实体
   @Test
   public void findContactWay4ConditionTest() { 
	    ContactWay cw=null;
	    try {
			cw = contactWayService.findContactWay4Condition("ff808081573198e201573198e50e0000");
			Thread.sleep(12000);
			System.out.println("-------------begin");
		    contactWayService.findContactWay4Condition("ff808081573198e201573198e50e0000");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
   }
   
   /**
 * 
 * 查询普通属性,hibernate 1级缓存不会不会缓存 只缓存实体查询
 */
@Test
   public void findPropertyTest() { 
   	//异常: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.xgw.crm.entity.user.ContactWay.contactsSet, could not initialize proxy - no Session
		try {
			List<String> list = contactWayService.findProperty("SELECT c.email FROM ContactWay c");
			System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
    
   /*  
        	测试级联关系  CascadeType.REFRESH,应用场景   如果查询某条数据过程的业务处理  要处理的时间约为60秒，但是在这60秒内 数据可能被修改过，
        	这样将导致 被查出的数据  非最新，这时可以查询出数据后  在调用EntityManager.refresh方法  即可获取最新数据，而你可能认为 可以再重新查询一次
        	但这样  因为之前的查询的数据  会放置到一级缓存里，你再重新查询  数据还是不是最新的 
        	
    @Test
    public void  refreshTest() { 
    	//异常:java.lang.IllegalArgumentException: Entity not managed
    	ContactWay cw=contactWayDao.find("ff808081573198e201573198e50e0000");
    	System.out.println("1:"+cw.getDetailedAddress());
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	contactWayDao.refresh(cw);
    	System.out.println("2:"+cw.getDetailedAddress());
    }
    
    
    
    //隔离级别
    
    //load 和get区别
    
    //一级缓存在同一个事务下(同一个session),只支持 实体查询  属性查询  是要发sql查数据
    //save后也会把实体数据保存到一级缓存中,在load该实体  也不会发sql
    
    @Test
    public void firstCashTest() {
    	ContactWay cw=contactWayDao.find("ff808081573198e201573198e50e0000");
    	System.out.println(cw.getDetailedAddress());
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ContactWay cw1=contactWayDao.find("ff808081573198e201573198e50e0000");
    	System.out.println(cw1.getDetailedAddress());
    }*/
}
