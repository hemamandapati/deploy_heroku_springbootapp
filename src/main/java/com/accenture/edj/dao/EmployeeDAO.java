package com.accenture.edj.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.edj.entity.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Integer>{

	
	
}
