package com.revature;

import com.revature.DAO.EmployeeDAO;
import com.revature.DAO.EmployeeDAOImpl;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmployeeDAO emdao = new EmployeeDAOImpl();
		
		System.out.println(emdao.getAllEmployees());
	}
}
