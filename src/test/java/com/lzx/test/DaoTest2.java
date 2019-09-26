package com.lzx.test;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import com.lzx.bean.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lzx.bean.Employee;
import com.lzx.dao.DepartmentMapper;
import com.lzx.dao.EmployeeMapper;
import com.lzx.service.EmployeeService;

/**
 * @description: 方法二:通过spring-test进行测试 1.@RunWith(SpringJUnit4ClassRunner.class)
 *               2.通过@ContextConfiguration指定spring配置文件 3.直接autowire注入组件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" }) // (实际上也是获取spring上下文)

public class DaoTest2 {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	SqlSession sqlSession;// 批量处理
	Logger logger = Logger.getLogger("DaoTest2.class");
	@Autowired
	EmployeeService employeeService;


	@Test
	public void testDao() {
		EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String uuid = UUID.randomUUID().toString().substring(0, 5) + i;
			User user=new User(null,uuid,"M",uuid+"qq.com",1);
		employeeMapper.insert(user);
		}
		logger.info("插入完成");
	}

	@Test
	public void testSelectAll() {
		EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
		List<Employee> employees = employeeMapper.selectByExample(null);
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}

	@Test
	public void testCheckSameEmployee() {
		boolean result1 = employeeService.checkSameEmployee("万涛");
		logger.info("" + result1);
		boolean result2 = employeeService.checkSameEmployee("selenium");
		logger.info("" + result2);
		boolean result3 = employeeService.checkSameEmployee("cccc");
		logger.info("" + result3);
	}

}
