package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ReimbursementDAO;
import com.revature.DAO.ReimbursementDAOImpl;
import com.revature.model.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.util.RequestHelper;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FrontController.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ReimbursementService rService = new ReimbursementService();
		String URI = request.getRequestURI().replace("/project-1/", "");
		log.info("Entered the doGet of front controller for " + URI);
		String statusType = "";
		if(URI.contains("GetAllReimbursementsOfAGivenType")){
			statusType = URI.replace("GetAllReimbursementsOfAGivenType/", "");
			log.info(statusType);
			URI = URI.replace("/"+statusType, "");
		}
		
		//Writers and mappers declared here
		ObjectMapper om = new ObjectMapper();
		String json = "";
		PrintWriter pw = response.getWriter();
		
		
		
		HttpSession ses = request.getSession();
		switch (URI) {
		case "home":
			log.info("Going to the home page of the user");
			String role = ses.getAttribute("role").toString();
			// Could use an if else if tree but decided to use switch for later additSions
			//sends the user to the currect home page
			switch (role) {
				case "Manager":
					log.info(ses.getAttribute("username") + " is a manager");
					// sends the page to the manager home
					request.getRequestDispatcher("/Manager/home.html").forward(request, response);
					break;
				case "Employee":
					log.info("Person is just an employee");
					// seds the page to the employee home
					request.getRequestDispatcher("/Employee/home.html").forward(request, response);
					break;
			}
			break;
		case "AllReimbursements":
			log.info("getting all the reimbursements");
			response.setContentType("text/json");
			// print out the reimbursemensts
			List<Reimbursement> allReimbursements = rService.getAllReimbursements();
			json = om.writeValueAsString(allReimbursements);
			pw.print(json);
			break;

		case "GetAllReimbursementsOfAGivenType":
			response.setContentType("text/json");
			log.info(ses.getAttribute("statusType"));
			List<Reimbursement> statusedReimbursements = rService.getAllReimbursementsOfAGivenStatus(statusType);
			json = om.writeValueAsString(statusedReimbursements);
			pw.print(json);
			break;
		case "NewReimbursementForm":
			log.info("inside the new reimbursement form front controller");
			request.getRequestDispatcher("/Employee/newReimbursementForm.html").forward(request, response);;
			break;
		case "PastReimbursementPage":
			request.getRequestDispatcher("/Employee/pastReimbursementPage.html").forward(request, response);
			break;
		case "PastReimbursement":
			response.setContentType("text/json");
			List<Reimbursement> reimbursements = rService.getAllReimbursementsOfAGivenEmployee(Integer.parseInt(ses.getAttribute("currentUserId").toString()));
			json = om.writeValueAsString(reimbursements);
			pw.print(json);
			break;

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ReimbursementService rService = new ReimbursementService();
		final String URI = request.getRequestURI().replace("/project-1/", "");
		log.info("Entered the doGet of front controller");

		if (URI.contains("ReimbursementApproved")) {
			log.info(URI);
			final String reimbursementId = request.getRequestURI().replace("/project-1/", "").replace(".ReimbursementApproved", "");
			log.info(reimbursementId);
			log.info(Integer.parseInt(request.getSession().getAttribute("currentUserId").toString()));
			rService.approveReimbursement(Integer.parseInt(reimbursementId), Integer.parseInt(request.getSession().getAttribute("currentUserId").toString()));
			
		} else if(URI.contains("ReimbursementUnapproved")) {
			log.info(URI);
			final String reimbursementId = request.getRequestURI().replace("/project-1/", "").replace(".ReimbursementUnapproved", "");
			log.info(reimbursementId);
			log.info(Integer.parseInt(request.getSession().getAttribute("currentUserId").toString()));
			rService.unapproveReimbursement(Integer.parseInt(reimbursementId), Integer.parseInt(request.getSession().getAttribute("currentUserId").toString()));
		} else {
			switch (URI) {
			case "login":
				log.info("attempting to log in");
				RequestHelper.processLogin(request, response);
				break;
			case "DeleteReimbursement":
				BufferedReader reader = request.getReader();
				int id = Integer.parseInt(reader.readLine());
				if(rService.deleteReimbursement(id)) {
					log.info("Success");
				} else {
					log.info("Failed to delete");				
				}
				break;
			case "SubmitReimbursement":
				RequestHelper rh = new RequestHelper();
				rh.submitReimbursement(request, response);
				break;
//			case "logout":
//				RequestHelper.processLogout(request, response);
//				break;
			}
		}
	}

}
