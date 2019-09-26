package com.lzx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzx.bean.Employee;
import com.lzx.bean.Message;
import com.lzx.service.EmployeeService;


@Controller
public class EmployeeController {
	Logger logger = Logger.getLogger("EmployeeController.class");
	@Autowired
	EmployeeService employeeService;


//	@GetMapping(value = "emps")
//	public String showEmployees(@RequestParam(defaultValue = "1", name = "pn") Integer pn,Model model) {
//		PageHelper.startPage(pn, 5);// 后面紧跟的查询为分页查询
//		List<Employee> employees = employeeService.findAllEmployee();
//		PageInfo pageInfo=new PageInfo(employees,5);//用pageInfo封装然后交给页面
//		model.addAttribute("pageInfo",pageInfo);
//		return "show";
//	}

	@GetMapping(value = "/emps")
	@ResponseBody
	public Message showEmployeesWithJson(@RequestParam(defaultValue = "1", name = "pn") Integer pn, Model model) {
		PageHelper.startPage(pn, 5);// 后面紧跟的查询为分页查询
		List<Employee> employees = employeeService.findAllEmployee();
		PageInfo pageInfo = new PageInfo(employees, 5);// 用pageInfo封装然后交给页面
		return Message.success().add("pageInfo", pageInfo);
	}


	@PostMapping(value = "/emp")
	@ResponseBody
	public Message saveEmployee(@Valid Employee employee, BindingResult result) {
		// System.out.println(employee);
		if (result.hasErrors()) {// 后端校验失败,返回校验失败的信息
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				map.put(error.getField(), error.getDefaultMessage());
			}
			return Message.fail().add("errorField", map);
		} else {
			employeeService.saveEmployee(employee);
			return Message.success();
		}
	}


	@PostMapping(value = "/checkSameEmployee")
	@ResponseBody
	public Message checkSameEmployee(@RequestParam("empName") String empName) {
		if (employeeService.checkSameEmployee(empName)) {// 用户名不存在
//    		logger.info(empName+"不存在");
			return Message.success();
		} else {
//    		logger.info(empName+"存在");
			return Message.fail();
		}
	}


	@GetMapping(value = "/emp/{id}")
	@ResponseBody
	public Message getEmployee(@PathVariable("id") Integer id) {
		Employee employee = employeeService.getEmployee(id);
		return Message.success().add("employee", employee);
	}

	@PutMapping(value = "/emp/{empId}")
	@ResponseBody
	public Message saveUpdateEmployee(Employee employee) {
		// System.out.println(employee);
		logger.info(employee.toString());
		employeeService.updateEmployee(employee);
		return Message.success();
	}

	@DeleteMapping(value = "/emp/{empIds}")
	@ResponseBody
	public Message deleteEmployee(@PathVariable("empIds") String empIds) {
		if (empIds.contains("-")) {
			String[] ids = empIds.split("-");
			List<Integer> idsList = new ArrayList<>();
			for (String id : ids) {
				idsList.add(Integer.parseInt(id));
			}
			employeeService.deleteBatch(idsList);
		} else {
			employeeService.deleteEmployee(Integer.parseInt(empIds));
		}
		return Message.success();
	}

}
