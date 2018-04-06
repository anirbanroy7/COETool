package com.coetool.servlet.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.coetool.servlet.model.AccountVersion;
import com.coetool.servlet.model.Application;

public interface AccountVersionDAO {

	public int createAccountVersion(AccountVersion accountVersion) throws SQLException;
	
	public List<Application> getApplicationDetails(AccountVersion accountVersion) throws SQLException;
	
	public List<Application> getAllApplicationDetails() throws SQLException;
	
	public int updateApplicationDetails(Map<Integer,Integer> input) throws SQLException;
}
