package hibernate.hibernate_cache_level_1.service.user;

import hibernate.hibernate_cache_level_1.dao.UserDao;
import hibernate.hibernate_cache_level_1.entity.user.User;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserTest extends AbstractJUnit4SpringContextTests {
	@Resource
    private UserDao userDao;
    
    @Test
    public void saveTest() { 
    	User u=new User();
    	u.setFirst_name("333333");
    	u.setLast_name("444444444444");
    	userDao.persist(u);//此处要在该方法上加@Transactional上事务
    	User u1=userDao.find("1");
    	System.out.println(u1.getFirst_name());
    }
}
