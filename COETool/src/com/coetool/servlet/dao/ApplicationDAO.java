package com.coetool.servlet.dao;

import java.sql.SQLException;
import java.util.List;

import com.coetool.servlet.model.Application;

public interface ApplicationDAO {

	public int createApplication(Application application) throws SQLException;
	
	public Application getApplicationDetails(Application application) throws SQLException;
	
	public List<Application> getAllApplicationDetails() throws SQLException;
	
	public int updateApplication(Application application) throws SQLException;
}
