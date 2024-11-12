package com.accenture.edj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.accenture.edj.bean.EmployeeBean;
import com.accenture.edj.dao.EmployeeDAO;
import com.accenture.edj.entity.Employee;

@Service
public class EmployeeService {

	
	@Autowired
	private EmployeeDAO dao;
	
	
	public List<EmployeeBean> getEmployeeDetails(){
		List<Employee> list = dao.findAll();
		List<EmployeeBean> listBean = new ArrayList<>();
		for(Employee entity : list) {
			EmployeeBean bean = new EmployeeBean();
			BeanUtils.copyProperties(entity, bean);
			listBean.add(bean);
		}
		return listBean;
	}
	

	public EmployeeBean getEmployeeDetailsById(Integer id) {
		Optional<Employee> entity = dao.findById(id);
		EmployeeBean bean = null;
		if(entity.isPresent()) {
			bean = new EmployeeBean();
			BeanUtils.copyProperties(entity.get(), bean);
		}
		
		return bean;
	}
	

	public EmployeeBean addEmployee(EmployeeBean bean) {
		Employee entity = new Employee();
		BeanUtils.copyProperties(bean, entity);
		Employee savedEmployee = dao.save(entity);
		BeanUtils.copyProperties(savedEmployee, bean);
		return bean;
	}
	
	
	public EmployeeBean updateEmployee(EmployeeBean bean) {
		Optional<Employee> updatedEmployee = dao.findById(bean.getAdvisor_id());
		if(updatedEmployee.isPresent()) {
			Employee emp1 = updatedEmployee.get();
			BeanUtils.copyProperties(bean, emp1);
			dao.save(emp1);	
		}
		else {
			System.out.println("Employee with id "+bean.getAdvisor_id()+"not present in db");
		}
		return bean;
	}
	
	
	public EmployeeBean deleteEmployee(Integer id) {
		 Optional<Employee> emp = dao.findById(id);
		 EmployeeBean bean = new EmployeeBean();
		 if(emp.isPresent()) {
			 BeanUtils.copyProperties(emp, bean);
			 dao.delete(emp.get());
		 }
		 
		 return bean;
	}

}
