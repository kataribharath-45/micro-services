package com.example.demo.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.controller.EmployeeController;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

//import com.example.demo.model.User;


@Service
public class EmployeeService{
	
	Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	
//	private static  List<User> users = new ArrayList<User>();
//	
//	public User saveUser(User user) {
//	
//		users.add(user);
//		
//		return user;
//		
//	}
	
//	public List<User> list(){
//		
//		return users;
//	}
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	
	public Employee createEmployee(Employee emp) {
		
		logger.info("Inside EmployeeService saving the employee object");
		return employeeRepository.save(emp);
		
	}
	
	public Optional<Employee> getEmployeeById(int id) {
		
		logger.info("EmployeeService Inside the getEmployeeById: "+id);
		
		Optional<Employee> optional= employeeRepository.findById(id);
		if(optional.isEmpty()) {
			logger.info("EmployeeService Inside the get employee by employeeId where id is not found:  "+id);
			return null;
		}
		logger.info("EmployeeService Inside the get employee by employeeId where id is found:  "+id);
		return optional;
	}
	
	public List<Employee> getAllEmployees() {
		
		return employeeRepository.findAll();
		
	}
	
	public void deleteAllEmployees() {
		
		employeeRepository.deleteAll();;
		
	}
	
	public Employee updateEmployee(Integer id, Employee emp) {
		
		Optional<Employee> optional= employeeRepository.findById(id);
		if(optional.isPresent()) {
			Employee e = optional.get();
			e.setFirstName(emp.getFirstName());
			e.setLastName(emp.getLastName());
			e.setAge(emp.getAge());
			e.setEmail(emp.getEmail());
			e.setWorking(emp.isWorking());
			return employeeRepository.save(e);
		}
		return null;
	}
	
	public Employee patchEmployee(Integer id, Map<String,Object> updates) {
		
		Optional<Employee> optional= employeeRepository.findById(id);
		if(optional.isPresent()) {
			Employee e = optional.get();
			
			if(updates.containsKey("firstName")) {
				e.setFirstName((String)updates.get("firstName"));
			}
			
			if(updates.containsKey("lastName")) {
				e.setLastName((String)updates.get("lastName"));
			}
		
			return employeeRepository.save(e);
		}
		return null;
	}
	


}
