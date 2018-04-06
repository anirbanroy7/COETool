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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.coetool.servlet.dao.AccountDAO;
import com.coetool.servlet.dao.AccountDAOImpl;
import com.coetool.servlet.model.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class AccountController extends HttpServlet {

	private static final long serialVersionUID = -4602272917509602701L;
	private static final String ACTION = "action";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		if("createAccount".equals(request.getParameter(ACTION)))
		{
			createAccount(request,response);
		}
		else if("searchAccount".equals(request.getParameter(ACTION)))
		{
			searchAccount(request,response);
		}
		else if("editAccount".equals(request.getParameter(ACTION)))
		{
			editAccount(request,response);
		}
		else if("updateAccount".equals(request.getParameter(ACTION)))
		{
			updateAccount(request,response);
		}
		else if("backToHome".equals(request.getParameter(ACTION)))
		{
			backToHome(request,response);
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
	
	private Account fillAttributes(Account account,HttpServletRequest request)
	{
		
		account.setAccName(checkNull(request.getParameter("accountName")));
		account.setAccContact(checkNull(request.getParameter("accountContact")));
		account.setProjectId(checkNull(request.getParameter("projectId")));
		account.setProjectName(checkNull(request.getParameter("projectName")));
		account.setIlcCode(checkNull(request.getParameter("ilcCode")));
		account.setLicenseNo(checkNull(request.getParameter("licenseNo")));
		account.setIot(checkNull(request.getParameter("iot")));
		
		return account;
	}
	private String checkNull(String input)
	{
		if(StringUtils.isNotBlank(input))
			return input;
		else
			return null;
				
	}
	
	protected void createAccount(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String error;
		
		Account account = new Account();
		
		account = fillAttributes(account, request);
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		AccountDAO accountDAO = new AccountDAOImpl();

		int result=0;
		try {
			if(account.getAccName() != null && account.getProjectId() != null)
				result = accountDAO.createAccount(account);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result == 0 ) {
			error = "Enter Valid Account Details.";
			out.write(error);
		} else {
			String msg = "Account  :"+account.getAccName() + "  successfully added. ";
			out.write(msg);
			/*session.setAttribute("message", msg);
			session.removeAttribute("error");
			response.sendRedirect("index2.jsp");*/
		}

	}
	
	protected void searchAccount(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
        response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.create();
	   
	    List<Account> results = null;
	    
	    String output = "";
	    AccountDAO accountDAO = new AccountDAOImpl();
	    
	    try {
	    	results = accountDAO.getAllAccountDetails();
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
	protected void editAccount(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		 AccountDAO accountDAO = new AccountDAOImpl();
		 String accIdStr = request.getParameter("rowDataId");
		 int accId =0;
		 Account account = new Account();   
		 Account accountResult =  null;
		    try {
		    		if(StringUtils.isNumeric(accIdStr))
		    		{
		    			accId = Integer.parseInt(accIdStr);
		    			account.setAccId(accId);
		    		}
		    		else
		    		{
		    			account.setAccId(accId);
		    		}
		    		
		    		if(accId>0)
		    			accountResult= accountDAO.getAccountDetails(account);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    request.setAttribute("editDataAccount", accountResult);
		    RequestDispatcher rd = request.getRequestDispatcher("editAccountResult.jsp");
		    rd.forward(request, response);
	}
	
	protected void updateAccount(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		String error;
		Account account = new Account();
		PrintWriter out = response.getWriter();
		account = fillAttributes(account, request);
		AccountDAO accountDAO = new AccountDAOImpl();
		String accIdStr = request.getParameter("accId");
		int accId =0;
		int result=0;
		try {
			if(StringUtils.isNumeric(accIdStr))
    		{
    			accId = Integer.parseInt(accIdStr);
    			account.setAccId(accId);
    		}
    		else
    		{
    			account.setAccId(accId);
    		}
			if(account.getAccId() >0)
				result = accountDAO.updateAccount(account);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result == 0 ) {
			error = "Error in Account Updation .";
			out.write(error);
		} else {
			String msg = "Account  updated  successfully . ";
			out.write(msg);
			/*session.setAttribute("message", msg);
			session.removeAttribute("error");
			response.sendRedirect("index2.jsp");*/
		}


	}
	
	protected void backToHome(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd = request.getRequestDispatcher("index2.jsp");
	    rd.forward(request, response);
	}
}