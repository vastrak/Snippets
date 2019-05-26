package com.vastrak.model;

import com.vastrak.validate.Required;

/**
 * 
 * @author Christian
 *
 */
public class Employee {
	
	@Required  // field required!
	private Long employee_id;
	@Required
	private String name;
	@Required
	private Integer age;
	private Double salary;
	
	public Employee() {
	
	}

	public Long getEmployee_id() {
		return employee_id;
	}


	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public Double getSalary() {
		return salary;
	}


	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if((o != null) && (o instanceof Employee)) {
			return ((Employee) o).getEmployee_id().equals(this.employee_id);
		}
		return false;
		
	}

	@Override
	public int hashCode() {
		return this.employee_id.hashCode();
	}


	@Override
	public String toString() {
		return "Employee [employee_id=" + employee_id + ", name=" + name + ", age=" + age + ", salary=" + salary + "]\n";
	}
	

	
}
