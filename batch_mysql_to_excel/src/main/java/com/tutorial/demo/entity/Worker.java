package com.tutorial.demo.entity;

public class Worker {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String dept;
	
	private Integer salary;

	public Worker(Long id, String firstName, String lastName, String dept, Integer salary) {
	
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dept = dept;
		this.salary = salary;
	}

	public Worker() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dept=" + dept
				+ ", salary=" + salary + "]";
	}
	
	
	

}
