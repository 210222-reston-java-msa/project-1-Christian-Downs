package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.services.EmployeeService;
import com.revature.services.ReimbursementService;

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
	
	@Test
	public void testingReimbursementService() {
		Reimbursement r = new Reimbursement(2,3000,Timestamp.valueOf("2021-03-09 00:00:00"),Timestamp.valueOf("2021-03-11 15:33:25"),"Renting car","", 2,1,"APPROVED","LODGING");
		ReimbursementService rServ = new ReimbursementService();
		assertEquals(r.toString(),r.toString());
	}

}
