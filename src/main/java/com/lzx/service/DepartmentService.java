package com.lzx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzx.bean.Department;
import com.lzx.bean.Message;
import com.lzx.dao.DepartmentMapper;

@Service
public class DepartmentService {
	@Autowired
    private DepartmentMapper departmentMapper;
	public Message getAllDepts() {
		List<Department> departments=departmentMapper.selectByExample(null);
		return Message.success().add("depts",departments);
	}
}
