package com.coetool.servlet.model;

public class Application {

	private String applicationName;
	private int numOfLicenses;
	private String contactPerson;
	private int accountId;
	public Application(){}
	
	public Application(int accountId,String applicationName,int numOfLicenses,String contactPerson)
	{
		this.accountId = accountId;
		this.applicationName = applicationName;
		this.numOfLicenses = numOfLicenses;
		this.contactPerson = contactPerson;
		
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	

	public int getNumOfLicenses() {
		return numOfLicenses;
	}

	public void setNumOfLicenses(int numOfLicenses) {
		this.numOfLicenses = numOfLicenses;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	
}
