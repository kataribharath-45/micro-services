package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;


import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/greeting")
	public String helloWorld() {
		logger.info("HI Inside the EmployeeController");
		return "Welcome to Microservices Demo";
	}
	
	@Operation(summary = "Create A New Employee")
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/save")
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
		logger.info("Started storing the employee objects into the H2 Database:");
		Employee response= employeeService.createEmployee(employee);
		logger.info("Employee objects stored into the H2 Database:");
		return new ResponseEntity<Employee> (response, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Existing Employee")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer id,@Valid @RequestBody Employee employee) {
		logger.info("Started storing the employee objects into the H2 Database:");
		Employee response= employeeService.updateEmployee(id,employee);
		logger.info("Employee objects stored into the H2 Database:");
		return new ResponseEntity<Employee> (response, HttpStatus.OK);	
	}
	
	@Operation(summary = "Update Existing Employee")
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/update")
	public ResponseEntity<Employee> pathEmployee(@RequestParam Integer id,@RequestBody Map<String, Object> updates) {
		logger.info("Started storing the employee objects into the H2 Database:");
		Employee response= employeeService.patchEmployee(id,updates);
		logger.info("Employee objects stored into the H2 Database:");
		return new ResponseEntity<Employee> (response, HttpStatus.OK);	
	}
	
	@Operation(summary = "Get Employee by Id", description = "Returns A Single Employee")
	@PreAuthorize("hasRole('USER')")
	@GetMapping("employee/{id}")
	public ResponseEntity<?> EmployeeByIds(@PathVariable int id) {
		Optional<Employee> response= employeeService.getEmployeeById(id);
		if(response.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(response.get(),HttpStatus.OK);
	}
	
	
	@Operation(summary ="Get All Employees", description = "Return A List Of Employees")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> listOfEmployees(){
		List<Employee> listOfEmployees= employeeService.getAllEmployees();
		
		if(listOfEmployees.size()<= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}
		return ResponseEntity.of(Optional.of(listOfEmployees));
	}
	
	@Operation(summary ="Delete All Employees", description = "Delete A List Of Employees")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/employees")
	public ResponseEntity<?> deleteAll(){
		employeeService.deleteAllEmployees();
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
