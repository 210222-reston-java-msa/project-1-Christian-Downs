package com.revature.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.util.ConnectionUtil;

import jdk.internal.org.jline.utils.Log;

public class ReimbursementDAOImpl implements ReimbursementDAO{
	private static Logger log = Logger.getLogger(ReimbursementDAOImpl.class);
	
	@Override
	public boolean insert(Reimbursement re) {
		
		
		PreparedStatement stmt = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO employeereimbusement.reimbursement\r\n"
					+ "( reimb_amount, reimb_submitted,  reimb_description, reimb_receipt, reimb_author, reimb_status_id, reimb_type_id) "+
					"Values(?,           ?,             ?,                  ?,             ?,               ?,               ?);";
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, re.getAmount());
			stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setString(3, re.getDescription());
			stmt.setString(4, re.getReceipt());
			stmt.setInt(5, re.getAuthorId());
			stmt.setInt(6, 1);
			switch (re.getType().toLowerCase()) {
			case "lodging":
				stmt.setInt(7, 1);
				break;
			case "travel":
				stmt.setInt(7, 2);
				break;
			case "food":
				stmt.setInt(7, 3);
				break;
			case "other":
			default:
				stmt.setInt(7, 4);
				break;
			}
			int result = stmt.executeUpdate();
			if(result ==0) {
				log.info("we failed");
				return false;
			} else {
				log.info("We inserted the reimbursement!");
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStatus(Reimbursement re, int approversId) {
		int statusNumber = 1;
		if (re.getStatus().toLowerCase().equals("approved")) {
			statusNumber = 2;

		} else if (re.getStatus().toLowerCase().equals("unapproved")) {

			statusNumber = 3;
		}
		
		PreparedStatement stmt = null;
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE reimbursement SET "+
					"reimb_status_id= "+statusNumber + ", reimb_resolved=?, reimb_resolver="+ approversId  
							+ " Where reimb_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setInt(2, re.getId());
			int result = stmt.executeUpdate();
			if(result==0) {
				log.info("didn't update anything");
				return false;
			}
			else {
				log.info("updated a reimbursement");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public Reimbursement getReimbursementByID(int id) {
		Reimbursement output = new Reimbursement();
		PreparedStatement stmt = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, (select reimb_status from reimbursement_status where reimb_status_id = r.reimb_status_id) as reimb_status, (select reimb_type from reimbursement_type where reimb_type_id = r.reimb_type_id) as reimb_type from reimbursement r where reimb_id = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Reimbursement r = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
						);
				log.info(r);
				output=(r);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return output;
	}

	@Override
	public List<Reimbursement> getAllReimbursements() {
		List<Reimbursement> output = new ArrayList<Reimbursement>();
		
		PreparedStatement stmt = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, (select reimb_status from reimbursement_status where reimb_status_id = r.reimb_status_id) as reimb_status, (select reimb_type from reimbursement_type where reimb_type_id = r.reimb_type_id) as reimb_type from reimbursement r";
			
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Reimbursement r = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
						);
				log.info(r);
				output.add(r);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return output;
	}
	@Override
	public List<Reimbursement> getAllReimbursmentsWithASpecificStatus(String status) {
		List<Reimbursement> output = new ArrayList<Reimbursement>();
		
		int statusId = 1;
		switch(status.toLowerCase()) {
		case "pending":
			statusId = 1;
			break;
		case "approved":
			statusId =2;
			break;
		case "unapproved":
			statusId =3;
			break;
		}
		
		PreparedStatement stmt = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, (select reimb_status from reimbursement_status where reimb_status_id = r.reimb_status_id) as reimb_status, (select reimb_type from reimbursement_type where reimb_type_id = r.reimb_type_id) as reimb_type from reimbursement r"+
						" WHERE reimb_status_id = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, statusId);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Reimbursement r = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
						);
				log.info(r);
				output.add(r);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return output;
	}
	
	
	
	
	//Strech Goals
	@Override
	public List<Reimbursement> getAllReimbursementsByEmployee(int id) {
		List<Reimbursement> output = new ArrayList<Reimbursement>();
		PreparedStatement stmt = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, (select reimb_status from reimbursement_status where reimb_status_id = r.reimb_status_id) as reimb_status, (select reimb_type from reimbursement_type where reimb_type_id = r.reimb_type_id) as reimb_type from reimbursement r"+
						" WHERE reimb_author = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Reimbursement r = new Reimbursement(
						rs.getInt("reimb_id"),
						rs.getDouble("reimb_amount"),
						rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getString("reimb_receipt"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getString("reimb_status"),
						rs.getString("reimb_type")
						);
				log.info(r);
				output.add(r);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return output;
	}

	@Override
	public List<Reimbursement> getAllUnapprovedReimbursementsByEmployee(Employee e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteReimbursement(int id) {
		PreparedStatement stmt = null;
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM reimbursement WHERE "+
					"reimb_id =?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			int result = stmt.executeUpdate();
			if(result==0) {
				log.info("didn't delete anything");
				return false;
			}
			else {
				log.info("deleted a reimbursement");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}



}
