package com.revature.services;

import java.util.List;

import com.revature.DAO.EmployeeDAO;
import com.revature.DAO.EmployeeDAOImpl;
import com.revature.model.Employee;

public class EmployeeService{
	public static EmployeeDAO eDao = new EmployeeDAOImpl();
	
	
	public static  Employee confirmLogin(String username, String password) {
		Employee e = eDao.findEmployeeByUsername(username);
		
		if(e == null) {
			return null;
		}
		
		if(e.getPassword().equals(password)) {
			return e;
		} else {
			return null;
		}
	}


	public boolean insert(Employee e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(Employee e) {
		// TODO Auto-generated method stub
		return eDao.update(e);
	}


	public Employee findEmployeeByUsername(String username) {
		return eDao.findEmployeeByUsername(username);
	}


	public Employee findEmployeeById(int id) {
		
		return eDao.findEmployeeById(id);
	}


	public List<Employee> getAllEmployees() {
		return eDao.getAllEmployees();
	}

}
