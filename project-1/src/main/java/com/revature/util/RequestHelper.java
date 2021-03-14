package com.revature.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Employee;
import com.revature.model.LoginTemplate;
import com.revature.model.Reimbursement;
import com.revature.services.EmployeeService;
import com.revature.services.ReimbursementService;

public class RequestHelper {
	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.info("Entered the login function inside the requestHelper class");
		BufferedReader reader = request.getReader();
		StringBuilder s = new StringBuilder();
		
		String line = reader.readLine();
		while(line!= null) {
			//addes the current line to the stringBuilder
			s.append(line);
			
			log.info(s);
			//pushes the reader to the next line
			line = reader.readLine();
		}
		
		String body = s.toString();
		
		log.info(body);
		
		LoginTemplate loginAttempt = mapper.readValue(body, LoginTemplate.class);
		
		String username = loginAttempt.getUsername();
		String password = loginAttempt.getPassword();
		
		log.info("user attempted to login with username: " + username);
		
		Employee e =  EmployeeService.confirmLogin(username,password);
		
		if(e != null) {
			log.info(e.toString());
			HttpSession session = request.getSession();
			
			session.setAttribute("username", username);
			session.setAttribute("role", e.getRole());
			session.setAttribute("firstName", e.getFirstName());
			session.setAttribute("currentUserId", e.getId());
			
			PrintWriter pw = response.getWriter();
			
			response.setContentType("application/json");
			
			pw.println(mapper.writeValueAsString(e));
		} else {
			log.info("couldn't find the user");
			response.setStatus(204);
		}
		
	}

	public static void processLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		session.removeAttribute("username");
//		session.removeAttribute("role");
//		session.removeAttribute("firstName");
//		session.removeAttribute("currentUserId");
//		log.info("cleared all attributes");
//		response.sendRedirect("");
	}

	public void submitReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder s = new StringBuilder();
		
		String line = reader.readLine();
		while(line!= null) {
			//addes the current line to the stringBuilder
			s.append(line);
			
			log.info(s);
			//pushes the reader to the next line
			line = reader.readLine();
		}
		
		String body = s.toString();
		
		//log.info(body);
		Reimbursement reimbursement = mapper.readValue(body, Reimbursement.class);
		reimbursement.setAuthorId(Integer.parseInt( request.getSession().getAttribute("currentUserId").toString()));
		
		log.info(reimbursement);
		
		if(reimbursement!=null) {
			ReimbursementService rService = new ReimbursementService();
			if(rService.insert(reimbursement))
				response.setStatus(200);
			else 
				response.setStatus(204);
		} else {
			response.setStatus(204);
		}
		
		
		
	}

}
