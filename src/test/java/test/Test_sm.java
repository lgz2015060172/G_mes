package test;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gz.dao.UsersMapper;
import com.gz.model.Users;

public class Test_sm {

	private UsersMapper usersMapper;
	//获取spring配置文件
	private static ApplicationContext bean=new ClassPathXmlApplicationContext("spring\\applicationContext.xml");
	
	@Test
	public void test_sm() {
		
		usersMapper=bean.getBean(UsersMapper.class);
	Users users=Users.builder().id(4).account("556").paaword("789").build();
	usersMapper.insert(users);
		}
	}
