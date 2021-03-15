package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.revature.model.Employee;
import com.revature.services.EmployeeService;

public class DriverTest {

	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer
	}

	@Test
	public void test() {
		assertTrue(true);
	}
	
	
	@Test
	public void testingEmployeeService() {
		Employee e = new Employee(1,"Test","password","test","person","test.person@example.com","Manager");
		
		EmployeeService eServ = new EmployeeService();
		assertEquals(e.toString(),eServ.findEmployeeById(1).toString());
	}

}
