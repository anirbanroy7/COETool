package com.coetool.servlet.dao;

import java.sql.SQLException;
import java.util.List;

import com.coetool.servlet.model.Account;

public interface AccountDAO {
	
	public int createAccount(Account account) throws SQLException;
	
	public Account getAccountDetails(Account account) throws SQLException;
	
	public List<Account> getAllAccountDetails() throws SQLException;
	
	public int updateAccount(Account account) throws SQLException;

}
