package com.accenture.edj.controller;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.accenture.edj.bean.EmployeeBean;
import com.accenture.edj.dao.EmployeeDAO;
import com.accenture.edj.service.EmployeeService;



@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	

	@GetMapping(value="/getDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<EmployeeBean>> getEmployeeDetails(){
		return new ResponseEntity<Collection<EmployeeBean>>(service.getEmployeeDetails(),HttpStatus.OK);
	}
	
	
	@GetMapping(value="/getDetailsById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeBean> getEmployeeDetailsById(@PathVariable("id") Integer myId){
		EmployeeBean bean = service.getEmployeeDetailsById(myId);
		if(bean != null) {
			return new ResponseEntity<EmployeeBean>(bean,HttpStatus.FOUND);
		}
		return new ResponseEntity<EmployeeBean>(HttpStatus.BAD_REQUEST);	
	}
	
	@PostMapping(value = "/addEmp", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Integer> saveEmployee(@RequestBody EmployeeBean bean){
		EmployeeBean bean1 = service.addEmployee(bean);
		return new ResponseEntity<Integer>(bean1.getAdvisor_id(), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/updateEmp",consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeBean> updateEmployee(@RequestBody EmployeeBean bean){
		if(service.getEmployeeDetailsById(bean.getAdvisor_id())!=null) {
			EmployeeBean bean2 = service.updateEmployee(bean);
			return new ResponseEntity<EmployeeBean>(bean, HttpStatus.OK);
		}
		return new ResponseEntity<EmployeeBean>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(value="/deleteById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEmp(@PathVariable("id") int id){
		if(service.getEmployeeDetailsById(id) == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		EmployeeBean bean = service.deleteEmployee(id);
		System.out.println(bean.getAdvisor_id()+" "+bean.getFirst_name());
		return new ResponseEntity<String>("Deleted the employee record with ID "+id, HttpStatus.OK);
	}
	
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
//
//    @GetMapping("/home")
//    public String homePage() {
//        return "home";
//    }
//
//    @GetMapping("/user")
//    public String userPage() {
//        return "userPage";
//    }
//
//    @GetMapping("/admin")
//    public String adminPage() {
//        return "adminPage";
//    }

		
}
