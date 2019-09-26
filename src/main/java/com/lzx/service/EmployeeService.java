package com.lzx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzx.bean.Employee;
import com.lzx.bean.EmployeeExample;
import com.lzx.bean.EmployeeExample.Criteria;
import com.lzx.dao.EmployeeMapper;

@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper;

	public List<Employee> findAllEmployee() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * @param
	 * @return void
	 * @description 新增用户
	 */
	public void saveEmployee(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * @param
	 * @return boolean
	 * @description 数据库中是否有empName,有返回false.没有返回true
	 */
	public boolean checkSameEmployee(String empName) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}

	public Employee getEmployee(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	public void updateEmployee(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	public void deleteEmployee(Integer empId) {
		employeeMapper.deleteByPrimaryKey(empId);
	}

	public void deleteBatch(List<Integer> ids) {

		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);

	}
}
