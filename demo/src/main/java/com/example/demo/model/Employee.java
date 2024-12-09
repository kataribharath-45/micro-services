package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "\"Employee\"")
public class Employee {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Employee Unique Identifier", example = "1")
	private Integer id;
	
	@NotNull(message = "firstName Cannot be null")
	@Schema(description = "Employee firstName", example = "Katari")
	private String firstName;
	
	@NotNull(message = "lastName Cannot be null")
	@Schema(description = "Employee lastName", example = "Bharath")
	private String lastName;
	
	@AssertTrue(message = "Working must be true")
	@Schema(description = "Employee WorkingStatus", example = "true")
	private boolean working;
	
	@Min(value = 18 , message = "Age Should not be less than 18")
	@Max(value = 150, message = "Age Should not be more than 150")
	@Schema(description = "Employee age", example = "25")
	private int age;
	
	@Email(message = "Email Should be Valid")
	@Schema(description = "Employee EmailId", example = "bharathkatari7@gmail.com")
	private String email;
	
	public Employee() {
		
	}
	
	public Employee(Integer id, String firstName, String lastName, boolean working, int age, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.working = working;
		this.age = age;
		this.email = email;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
