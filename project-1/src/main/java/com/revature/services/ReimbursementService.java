package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.DAO.ReimbursementDAO;
import com.revature.DAO.ReimbursementDAOImpl;
import com.revature.model.Reimbursement;

public class ReimbursementService {
	
	public static ReimbursementDAO rDAO = new ReimbursementDAOImpl();
	public static Logger log = Logger.getLogger(ReimbursementService.class);
	
	public boolean approveReimbursement(int idOfReimbursement, int idOfApprover) {
		log.info("inside the approve reimbursement service class");
		Reimbursement r = rDAO.getReimbursementByID(idOfReimbursement);
		r.setStatus("Approved");
		
		return rDAO.updateStatus(r,idOfApprover);
	}

	public boolean unapproveReimbursement(int idOfReimbursement, int idOfUnapprover) {
		log.info("inside the approve reimbursement service class");
		Reimbursement r = rDAO.getReimbursementByID(idOfReimbursement);
		r.setStatus("Unapproved");
		
		return rDAO.updateStatus(r,idOfUnapprover);
		
	}
	
	public List<Reimbursement> getAllReimbursements(){
		log.info("Inside the service layer to get all reimbursements");
		
		ReimbursementDAO reimDAO = new ReimbursementDAOImpl();
		return reimDAO.getAllReimbursements(); 
	}
	
	public List<Reimbursement> getAllReimbursementsOfAGivenStatus(String status){
		log.info("Entered the service layer");
		return rDAO.getAllReimbursmentsWithASpecificStatus(status);
	}
	
	public List<Reimbursement> getAllReimbursementsOfAGivenEmployee(int id){
		log.info("Entered the service layer");
		return rDAO.getAllReimbursementsByEmployee(id);
	}

	public boolean deleteReimbursement(int id) {
		
		return rDAO.deleteReimbursement(id);
	}

	public boolean insert(Reimbursement reimbursement) {
		
		return rDAO.insert(reimbursement);
		
	}

}
