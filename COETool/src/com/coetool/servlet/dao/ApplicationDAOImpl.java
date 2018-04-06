package com.coetool.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coetool.servlet.jdbc.JDBCUtil;
import com.coetool.servlet.model.Account;
import com.coetool.servlet.model.Application;

public class ApplicationDAOImpl implements ApplicationDAO {
	
	private Connection dbConnection;
	private PreparedStatement pStmt;
	private ResultSet rs;

	
	private String SQL_INSERT_APPLICATION = "INSERT INTO application (app_name,app_fte,contact_person,acc_id) VALUES (?,?,?,?)";
	private String SQL_SELECT = "select app_name,app_fte,contact_person,acc_id from application";
	private String SQL_SELECT_WITH_ID = "select app_name,app_fte,contact_person,acc_id from application where acc_id=?";
	private String SQL_UPDATE = "update application set app_fte=?,contact_person=? where acc_id=?";
	public ApplicationDAOImpl()
	{
		dbConnection = JDBCUtil.getConnection();
	}

	public int createApplication(Application application) throws SQLException {
		int result = 0;
		try {
			
			//dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_INSERT_APPLICATION);
			pStmt.setString(1, application.getApplicationName());
			pStmt.setInt(2, application.getNumOfLicenses());
			pStmt.setString(3, application.getContactPerson());
			pStmt.setInt(4, application.getAccountId());
			result = pStmt.executeUpdate();
			
			/*rs = pstmt.getGeneratedKeys();
            if(rs != null && rs.next()){
                System.out.println("Generated Emp Id: "+rs.getInt(1));
            }*/
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new SQLException(e);
		}
		finally{
            try{
                
                if(pStmt != null) pStmt.close();
                
            } catch(Exception ex){}
        }
		return result;
	}

	
	public Application getApplicationDetails(Application application) throws SQLException {

		Application accountFetch = null;

		try {

			// dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_SELECT_WITH_ID);
			pStmt.setInt(1, application.getAccountId());
			rs = pStmt.executeQuery();

			while (rs.next()) {
				accountFetch = new Application(rs.getInt("acc_id"), rs.getString("app_name"), rs.getInt("app_fte"),rs.getString("contact_person"));

			}
		} catch (Exception e) {

			throw new SQLException(e);
		} finally {
			try {

				if (pStmt != null)
					pStmt.close();

			} catch (Exception ex) {
			}
		}
		return accountFetch;
	}

	public List<Application> getAllApplicationDetails() throws SQLException {
		// TODO Auto-generated method stub

		List<Application> results = null;
		try {

			// dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_SELECT);
			rs = pStmt.executeQuery();
			results = new ArrayList<Application>();
			while (rs.next()) {
				results.add(new Application(rs.getInt("acc_id"), rs.getString("app_name"), rs.getInt("app_fte"),rs.getString("contact_person")));

			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			try {

				if (pStmt != null)
					pStmt.close();

			} catch (Exception ex) {
			}
		}
		return results;
	}
	
	public int updateApplication(Application application) throws SQLException {
		int result = 0;
		try {

			// dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_UPDATE);
			pStmt.setInt(1, application.getNumOfLicenses());
			pStmt.setString(2, application.getContactPerson());
			pStmt.setInt(3, application.getAccountId());
			result = pStmt.executeUpdate();

			/*
			 * rs = pstmt.getGeneratedKeys(); if(rs != null && rs.next()){
			 * System.out.println("Generated Emp Id: "+rs.getInt(1)); }
			 */
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			try {

				if (pStmt != null)
					pStmt.close();

			} catch (Exception ex) {
			}
		}
		return result;
	}

}
