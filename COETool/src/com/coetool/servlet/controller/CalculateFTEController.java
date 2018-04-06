package com.coetool.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.coetool.servlet.dao.AccountVersionDAO;
import com.coetool.servlet.dao.AccountVersionDAOImpl;
import com.coetool.servlet.model.AccountVersion;
import com.coetool.servlet.model.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CalculateFTEController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ACTION = "action";
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if("calculatefte".equals(request.getParameter(ACTION)))
		{
			doCalculate(request,response);
		}
		else if("submitfte".equals(request.getParameter(ACTION)))
		{
			updateFTE(request,response);
		}
		else if("searchData".equals(request.getParameter(ACTION)))
		{
			searchData(request,response);
		}
		else if("submitAllfte".equals(request.getParameter(ACTION)))
		{
			updateAllLicense(request,response);
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
	
	private AccountVersion fillAttributes(AccountVersion accVersion,HttpServletRequest request)
	{
		accVersion.setAccId(Integer.parseInt(request.getParameter("account_version")));
				
		return accVersion;
	}
	private String checkNull(String input)
	{
		if(StringUtils.isNotBlank(input))
			return input;
		else
			return null;
				
	}
	protected void doCalculate(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		PrintWriter out=response.getWriter();
        HttpSession session = request.getSession();
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8"); 
        response.setHeader("Cache-Control", "no-cache");
				
		AccountVersion  accVersion = new AccountVersion();
		
		accVersion = fillAttributes(accVersion, request);
		session.setAttribute("accountId", new Integer(accVersion.getAccId()));		
		
		AccountVersionDAO accountVersionDAO = new AccountVersionDAOImpl();
		Integer numbersofFTE = new Integer(0);
		
		int count = 0;
		List<Application> applicationList = null;
		
		try {
			if(accVersion.getAccId() > 0)
				applicationList = accountVersionDAO.getApplicationDetails(accVersion);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(applicationList != null && !applicationList.isEmpty())
		{
			for(Application app :applicationList)
			{
				count += app.getNumOfLicenses();
		
			}
			
			if(count > 0)
			{
				numbersofFTE = new Integer(count);
			}
		}
		out.write(numbersofFTE.toString());
		
	}
	
	protected void updateFTE(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		AccountVersion accountVersion = new AccountVersion();
		int accId = ((Integer)session.getAttribute("accountId")).intValue();
		accountVersion.setAccId(accId);
		String fte_charged = request.getParameter("fteCountVal");
		int fte_count = 0;
		if(StringUtils.isNumeric(request.getParameter("fteCountVal")))
		{
			fte_count = Integer.parseInt(request.getParameter("fteCountVal"));
			accountVersion.setChargableFte(fte_count);
		}
		
		AccountVersionDAO accountVersionDAO = new AccountVersionDAOImpl();
		int result=0;
		try {
			if(accId >0 )
				result = accountVersionDAO.createAccountVersion(accountVersion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result ==0)
		{
			out.write("Account Version Data not Updated.");
		}
		else if(result>0 ) {
			out.write("Data Updated Successfully.");
		}
	}
	
	protected void searchData(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.setContentType("application/json;charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.create();
		AccountVersion  accVersion = new AccountVersion();
		AccountVersionDAO accountVersionDAO = new AccountVersionDAOImpl();
		List<Application> applicationList = null;
		 String output = "";
		if(StringUtils.isNotBlank(request.getParameter("account_version")))
		{
			accVersion = fillAttributes(accVersion, request);
			try {
				if(accVersion.getAccId() > 0)
					applicationList = accountVersionDAO.getApplicationDetails(accVersion);
				else
					applicationList = accountVersionDAO.getAllApplicationDetails();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 if(applicationList != null && !applicationList.isEmpty()) {
				 session.setAttribute("applicationData",applicationList);
			    	
			    	output = gson.toJson(applicationList);
			    	out.write(output);
			    }
			 	else
				{
					output = "Search Data not found.";
					out.write(output);
				}
				
		}
		else
		{
			output = "Search Data not found.";
			out.write(output);
		}
	}
	
	protected void updateAllLicense(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		
		List<Application> applicationList = null;
		Map<Integer,Integer> dataMap = new HashMap<Integer,Integer>();
		AccountVersionDAO accountVersionDAO = new AccountVersionDAOImpl();
		
		int dbResult = 0;
		try {
			applicationList = accountVersionDAO.getAllApplicationDetails();
			if(applicationList != null && !applicationList.isEmpty())
			{
				for(Application app :applicationList)
				{
					if(!dataMap.containsKey(app.getAccountId()))
						dataMap.put(app.getAccountId(), app.getNumOfLicenses());
					else if(dataMap.containsKey(app.getAccountId()))
					{
						int count = dataMap.get(app.getAccountId());
						count += app.getNumOfLicenses();
						dataMap.put(app.getAccountId(), count);
					}
			
				}
			}
			if(dataMap != null && !dataMap.isEmpty())
				dbResult = accountVersionDAO.updateApplicationDetails(dataMap);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dbResult ==0)
		{
			out.write("Account Version Data not Updated.");
		}
		else if(dbResult>0 ) {
			out.write("Data Updated Successfully.");
		}
	}

}
