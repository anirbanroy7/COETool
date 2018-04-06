package com.coetool.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coetool.servlet.jdbc.JDBCUtil;
import com.coetool.servlet.model.AccountVersion;
import com.coetool.servlet.model.Application;

public class AccountVersionDAOImpl implements AccountVersionDAO {
	
	private String SQL_SELECT_WITH_ID = "select * from Application where acc_id=?";
	private String SQL_SELECT = "select * from Application";
	private String SQL_INSERT = "insert into Account_Version (acc_id,chargable_fte,version_no,cost_call_date) values (?,?,?,?)";
	
	private String MAX_VERSION_NO = "select max(version_no) from Account_Version where acc_id=?";
	private Connection dbConnection;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public AccountVersionDAOImpl()
	{
		dbConnection = JDBCUtil.getConnection();
	}
	
	public int getMaxVersionNo(AccountVersion accountVersion)
	{
		int result = 0;
		try {
			stmt = dbConnection.prepareStatement(MAX_VERSION_NO);
			stmt.setInt(1,accountVersion.getAccId());
			rs =stmt.executeQuery();
			while(rs.next())
			{
				if(rs.getString(1)!= null)
					result = Integer.parseInt(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}
		
		return result;
	}

	public int createAccountVersion(AccountVersion accountVersion) throws SQLException {
		int result = 0;
		try {
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			
			 int version_no = getMaxVersionNo(accountVersion);
			 version_no = version_no + 1;
			//dbConnection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
			stmt = dbConnection.prepareStatement(SQL_INSERT);
			stmt.setInt(1, accountVersion.getAccId());
			stmt.setInt(2, accountVersion.getChargableFte());
			stmt.setInt(3, version_no);
			stmt.setDate(4, sqlDate);
			result = stmt.executeUpdate();
			
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
                
                if(stmt != null) stmt.close();
                
            } catch(Exception ex){}
        }
		return result;
	}

	public List<Application> getApplicationDetails(AccountVersion accountVersion) throws SQLException {
		
		List<Application> list = null;
		try
		{
			list = new ArrayList<Application>();
			stmt = dbConnection.prepareStatement(SQL_SELECT_WITH_ID);
			stmt.setInt(1,accountVersion.getAccId());
			rs =stmt.executeQuery();
			Application application = null;
			while(rs.next())
			{
				application = new Application(rs.getInt("acc_id"),rs.getString("app_name"),rs.getInt("app_fte"),rs.getString("contact_person"));
				list.add(application);
				
			}
		}
		catch(Exception e)
		{
			throw new SQLException(e);
		}
		return list;
	}
	
	
public List<Application> getAllApplicationDetails() throws SQLException {
		
		List<Application> list = null;
		try
		{
			list = new ArrayList<Application>();
			stmt = dbConnection.prepareStatement(SQL_SELECT);
			
			rs =stmt.executeQuery();
			Application application = null;
			while(rs.next())
			{
				application = new Application(rs.getInt("acc_id"),rs.getString("app_name"),rs.getInt("app_fte"),rs.getString("contact_person"));
				list.add(application);
				
			}
		}
		catch(Exception e)
		{
			throw new SQLException(e);
		}
		return list;
	}

	@Override
	public int updateApplicationDetails(Map<Integer, Integer> input) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		int resultArr[] = null; 
		AccountVersion accountVersion = null;
		if(input != null && !input.isEmpty())
		{
			int i =0;
			 resultArr = new int[input.size()];
			 accountVersion = new AccountVersion();
			 for (Map.Entry<Integer,Integer> entry : input.entrySet()) {
		            System.out.println("Key = " + entry.getKey() +
		                             ", Value = " + entry.getValue());
		           			 
		            accountVersion.setAccId(entry.getKey());
		            accountVersion.setChargableFte(entry.getValue());
		            resultArr[i] = createAccountVersion(accountVersion);
		            i++;
			 }
			 for(int j=0;j<resultArr.length;j++)
			 {
				 result = resultArr[j];
				 if(result == 0)
					 break;
			 }
			 
		}
		return result;
	}

}
