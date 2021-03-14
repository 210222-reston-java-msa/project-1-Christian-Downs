package com.revature.DAO;

import java.util.List;

import com.revature.model.Employee;

public interface EmployeeDAO {
	public boolean insert(Employee e);
	public boolean update(Employee e);
	
	public Employee findEmployeeByUsername(String username);
	public Employee findEmployeeById(int id);
	public List<Employee> getAllEmployees();
}
