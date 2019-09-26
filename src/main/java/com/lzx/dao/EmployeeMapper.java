package com.lzx.dao;

import com.lzx.bean.Employee;
import com.lzx.bean.EmployeeExample;
import java.util.List;

import com.lzx.bean.User;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empId);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);
    
    List<Employee> selectByExampleWithDept(EmployeeExample example);
    
    Employee selectByPrimaryKey(Integer empId);
    
    Employee selectByPrimaryKeyWithDept(Integer empId);
    
    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    void insert(User m);
}