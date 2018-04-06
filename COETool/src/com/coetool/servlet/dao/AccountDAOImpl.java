package com.coetool.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coetool.servlet.jdbc.JDBCUtil;
import com.coetool.servlet.model.Account;

public class AccountDAOImpl implements AccountDAO {

	private Connection dbConnection;
	private PreparedStatement pStmt;
	private ResultSet rs;

	private String SQL_SELECT = "select acc_id,acc_name,iot,acc_contact,project_id,project_name,ilc_code,license_no from Account";
	private String SQL_SELECT_WITH_ID = "select acc_id,acc_name,iot,acc_contact,project_id,project_name,ilc_code,license_no from Account where acc_id=?";
	private String SQL_INSERT = "INSERT INTO account (acc_name,iot,acc_contact,project_id,project_name,ilc_code,license_no) VALUES (?,?,?,?,?,?,?)";
	private String SQL_UPDATE = "update account set iot=?,acc_contact=? ,ilc_code=?,license_no=? where acc_id=?";

	public AccountDAOImpl() {
		dbConnection = JDBCUtil.getConnection();
	}

	public int createAccount(Account account) throws SQLException {
		int result = 0;
		try {

			// dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_INSERT);
			pStmt.setString(1, account.getAccName());
			pStmt.setString(2, account.getIot());
			pStmt.setString(3, account.getAccContact());
			pStmt.setString(4, account.getProjectId());
			pStmt.setString(5, account.getProjectName());
			pStmt.setString(6, account.getIlcCode());
			pStmt.setString(7, account.getLicenseNo());
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

	public Account getAccountDetails(Account account) throws SQLException {

		Account accountFetch = null;

		try {

			// dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_SELECT_WITH_ID);
			pStmt.setInt(1, account.getAccId());
			rs = pStmt.executeQuery();

			while (rs.next()) {
				accountFetch = new Account(rs.getInt("acc_id"), rs.getString("acc_name"), rs.getString("iot"),
						rs.getString("acc_contact"), rs.getString("project_id"), rs.getString("project_name"),
						rs.getString("ilc_code"), rs.getString("license_no"));

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

	public List<Account> getAllAccountDetails() throws SQLException {
		// TODO Auto-generated method stub

		List<Account> results = null;
		try {

			// dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_SELECT);
			rs = pStmt.executeQuery();
			results = new ArrayList<Account>();
			while (rs.next()) {
				results.add(new Account(rs.getInt("acc_id"), rs.getString("acc_name"), rs.getString("iot"),
						rs.getString("acc_contact"), rs.getString("project_id"), rs.getString("project_name"),
						rs.getString("ilc_code"), rs.getString("license_no")));

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
	
	public int updateAccount(Account account) throws SQLException {
		int result = 0;
		try {

			// dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			pStmt = dbConnection.prepareStatement(SQL_UPDATE);
			pStmt.setString(1, account.getIot());
			pStmt.setString(2, account.getAccContact());
			pStmt.setString(3, account.getIlcCode());
			pStmt.setString(4, account.getLicenseNo());
			pStmt.setInt(5, account.getAccId());
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
