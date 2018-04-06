package com.coetool.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.coetool.servlet.dao.ApplicationDAO;
import com.coetool.servlet.dao.ApplicationDAOImpl;
import com.coetool.servlet.model.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApplicationController extends HttpServlet {
	
	private static final long serialVersionUID = -4602272917509602701L;
	
	private static final String ACTION = "action";
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		if("createApplication".equals(request.getParameter(ACTION)))
		{
			createApplication(request,response);
		}
		else if("searchApplication".equals(request.getParameter(ACTION)))
		{
			searchApplication(request,response);
		}
		else if("editApplication".equals(request.getParameter(ACTION)))
		{
			editApplication(request,response);
		}
		else if("updateApplication".equals(request.getParameter(ACTION)))
		{
			updateApplication(request,response);
		}
		else if("backToHome".equals(request.getParameter(ACTION)))
		{
			backToHome(request,response);
		}

	}


	private void updateApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String error;
		Application application = new Application();
		PrintWriter out = response.getWriter();
		application = fillAttributes(application, request);
		ApplicationDAO aoolicationDAO = new ApplicationDAOImpl();
		
		int result=0;
		try {
			
			if(application.getAccountId() >0)
				result = aoolicationDAO.updateApplication(application);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result == 0 ) {
			error = "Error in Application Updation .";
			out.write(error);
		} else {
			String msg = "Application  updated  successfully . ";
			out.write(msg);
			/*session.setAttribute("message", msg);
			session.removeAttribute("error");
			response.sendRedirect("index2.jsp");*/
		}

		
	}


	private void editApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ApplicationDAO applicationDAO = new ApplicationDAOImpl();
		 String accIdStr = request.getParameter("rowDataIdApplication");
		 int accId =0;
		 Application application = new Application();   
		 Application applicationResult =  null;
		    try {
		    		if(StringUtils.isNumeric(accIdStr))
		    		{
		    			accId = Integer.parseInt(accIdStr);
		    			application.setAccountId(accId);
		    		}
		    		else
		    		{
		    			application.setAccountId(accId);
		    		}
		    		
		    		if(accId>0)
		    			applicationResult= applicationDAO.getApplicationDetails(application);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    request.setAttribute("editDataApplication", applicationResult);
		    RequestDispatcher rd = request.getRequestDispatcher("editApplicationResult.jsp");
		    rd.forward(request, response);
		
	}


	private void searchApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.create();
	    List<Application> results = null;
	    
	    String output = "";
	    ApplicationDAO applicationDAO = new ApplicationDAOImpl();
	    
	    try {
	    	results = applicationDAO.getAllApplicationDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    /*request.setAttribute("searchData", results);
	    RequestDispatcher rd = request.getRequestDispatcher("searchResult.jsp");
	    rd.forward(request, response);*/
	    if(results != null && !results.isEmpty()) {
	    	
	    	output = gson.toJson(results);
	    }
	    out.write(output);
		
	}


	private void createApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String error;
		PrintWriter out = response.getWriter();
		Application application = new Application();
		
		application = fillAttributes(application, request);
		
		
		ApplicationDAO applicationDAO = new ApplicationDAOImpl();

		int result=0;
		try {
			if(application.getApplicationName() != null && application.getContactPerson() != null)
				result = applicationDAO.createApplication(application);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result == 0 ) {
			error = "Enter Valid Application Details.";
			out.write(error);
		} else {
			String msg = "Application  :"+application.getApplicationName() + "  successfully added. ";
			out.write(msg);
			
		}

		
	}


	/*protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String error;
				
		Application application = new Application();
		
		application = fillAttributes(application, request);
		
		HttpSession session = request.getSession();
		ApplicationDAO applicationDAO = new ApplicationDAOImpl();

		int result=0;
		try {
			if(application.getApplicationName() != null && application.getContactPerson() != null)
				result = applicationDAO.createApplication(application);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result == 0 ) {
			error = "Enter Valid Account Details.";
			session.setAttribute("error", error);
			session.removeAttribute("message");
			response.sendRedirect("index2.jsp");
		} else {
			String msg = "Application : "+application.getApplicationName() + "  successfully added. ";
			session.setAttribute("message", msg);
			session.removeAttribute("error");
			response.sendRedirect("index2.jsp");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if ("logout".equalsIgnoreCase(request.getParameter("param"))) {
			HttpSession session = request.getSession();
			if(session != null){
			session.invalidate();
			}
			response.sendRedirect("index.jsp");
		}
	}
*/	
	private Application fillAttributes(Application application,HttpServletRequest request)
	{
		
		application.setApplicationName(checkNull(request.getParameter("appName")));
		application.setNumOfLicenses(Integer.parseInt(request.getParameter("appFte")));
		application.setContactPerson(checkNull(request.getParameter("contactPerson")));
		application.setAccountId(Integer.parseInt(request.getParameter("account")));
				
		return application;
	}
	private String checkNull(String input)
	{
		if(StringUtils.isNotBlank(input))
			return input;
		else
			return null;
				
	}
	protected void backToHome(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd = request.getRequestDispatcher("index2.jsp");
	    rd.forward(request, response);
	}

}
