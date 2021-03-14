package com.revature.DAO;

import java.util.List;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;

public interface ReimbursementDAO {
	public boolean insert(Reimbursement re);
	public boolean updateStatus(Reimbursement re, int approversId);
	
	public Reimbursement getReimbursementByID(int id);
	

	public List<Reimbursement> getAllReimbursements();
	public List<Reimbursement> getAllReimbursmentsWithASpecificStatus(String status);
	
	//Strech Goals
	public List<Reimbursement> getAllReimbursementsByEmployee(int id);
	public List<Reimbursement> getAllUnapprovedReimbursementsByEmployee(Employee e);
	public boolean deleteReimbursement(int id);
	
}
