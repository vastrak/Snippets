package com.vastrak.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.vastrak.validate.RequiredFieldException;
import com.vastrak.validate.Validator;


public class EmployeeTest {

	private static final Log logger = LogFactory.getLog(EmployeeTest.class);

	@Test
	public void testRequiredAnnotation() {

		Employee employee = new Employee(); // all fields are null

		Throwable thrown = catchThrowable(() -> {
			Validator.validateNotNulls(employee);
		});
		assertThat(thrown).isInstanceOf(RequiredFieldException.class).hasNoCause();

	}

	@Test(expected=RequiredFieldException.class)
	public void testFieldNameNull() throws Exception {

		Employee employee = new Employee();
		employee.setEmployee_id(1L);
		employee.setName(null);	// name can't be null

		Validator.validateNotNulls(employee);

	}

	@Test
	public void testFieldAgeNull() throws Exception {

		Employee employee = new Employee();
		employee.setEmployee_id(1L);
		employee.setName("Luis");
		employee.setAge(null);  // age can't be null

		try {
			Validator.validateNotNulls(employee);
		} catch (RequiredFieldException | IllegalAccessException ex) {
			assertTrue(ex instanceof RequiredFieldException);
			logger.info(">>>> " + ex.getMessage());
			// The field age of class com.vastrak.model.Employee can't be null
		}

	}
	
	@Test
	public void testFieldSalaryNull() throws Exception {
		
		Employee employee = new Employee();
		employee.setEmployee_id(1L);
		employee.setName("Luis");
		employee.setAge(25);
		employee.setSalary(null); // <- The salary field can be null because it is not required!
		
		// then expect true 
		assertThat(Validator.validateNotNulls(employee)).isTrue();
		
	}

}
