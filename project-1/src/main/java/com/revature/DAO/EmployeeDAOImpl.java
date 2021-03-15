package com.revature.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.util.ConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO {
	
	private static Logger log = Logger.getLogger(EmployeeDAOImpl.class);
	
	@Override
	public boolean insert(Employee e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Employee e) {
		PreparedStatement stmt = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE employeereimbusement.users\r\n"
					+ "	SET user_email=?,user_first_name=?,username=?,user_last_name=?\r\n"
					+ "	WHERE users_id="+e.getId()+";";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, e.getEmail());
			stmt.setString(2,e.getFirstName());
			stmt.setString(3, e.getUsername());
			stmt.setString(4, e.getLastName());
			int result = stmt.executeUpdate();
			if(result == 0) {
				log.info("didn't update anything");
				return false;
			} else {
				log.info("Updated a profile");
				return true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public Employee findEmployeeByUsername(String username) {
		Employee output = new Employee();
		
		PreparedStatement stmt = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select * from users where users.username = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				
				output.setFirstName(rs.getString("user_first_name"));
				output.setLastName(rs.getString("user_last_name"));
				output.setId(rs.getInt("users_id"));
				output.setUsername(rs.getString("username"));
				output.setPassword(rs.getString("pass_word"));
				output.setEmail(rs.getString("user_email"));
				output.setRole(getUserRoleByUserRoleID(rs.getInt("user_role_id")));
				log.info(output);
				return output;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Employee findEmployeeById(int id) {
		Employee output = new Employee();
		
		PreparedStatement stmt = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select * from users where users.users_id = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				
				output.setFirstName(rs.getString("user_first_name"));
				output.setLastName(rs.getString("user_last_name"));
				output.setId(rs.getInt("users_id"));
				output.setUsername(rs.getString("username"));
				output.setPassword(rs.getString("pass_word"));
				output.setEmail(rs.getString("user_email"));
				output.setRole(getUserRoleByUserRoleID(rs.getInt("user_role_id")));
				log.info(output);
				return output;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> output = new ArrayList<Employee>();
		
		PreparedStatement stmt = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "select * from users";
			stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Object temp = rs;
				log.info(rs.getString("users_id"));
				output.add(new Employee(
						rs.getInt("users_id"),
						rs.getString("username"),
						rs.getString("pass_word"),
						rs.getString("user_first_name"),
						rs.getString("user_last_name"),
						rs.getString("user_email"),
						getUserRoleByUserRoleID(rs.getInt("user_role_id"))
						));
				
				int id = rs.getInt("users_id");
			}
			return output;
			
		} catch (SQLException e) {
			log.error("There was an error getting all the employees");
			e.printStackTrace();
		}
		
		
		return null;
	}

	private String getUserRoleByUserRoleID(int roleID) {
		PreparedStatement stmt = null;
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "Select user_role from user_roles where user_roles_id = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, roleID);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				return rs.getString("user_role");
			}
			
		} catch (SQLException e) {
			log.info("Error with role acquirer");
			e.printStackTrace();
		}
		return "No assigned role";
	}

}
