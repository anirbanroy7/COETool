package com.coetool.servlet.model;

public class Account {

	private int accId;
	private String accName;
	private String iot;
	private String accContact;
	private String projectId;
	private String projectName;
	private String ilcCode;
	private String licenseNo;
	private boolean active;
	
	public Account() {}
	
	public Account(int accId,String accName,String iot,String accContact,String projectId,String projectName,String ilcCode,String licenseNo)
	{
		this.accId = accId;
		this.accName = accName;
		this.iot = iot;
		this.accContact = accContact;
		this.projectId = projectId;
		this.projectName = projectName;
		this.ilcCode = ilcCode;
		this.licenseNo = licenseNo;
	}
	
	public Account(int accId,String accName,String iot,String accContact,String projectId,String projectName,String ilcCode,String licenseNo,boolean active)
	{
		this.accId = accId;
		this.accName = accName;
		this.iot = iot;
		this.accContact = accContact;
		this.projectId = projectId;
		this.projectName = projectName;
		this.ilcCode = ilcCode;
		this.licenseNo = licenseNo;
		this.active = active;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getIot() {
		return iot;
	}

	public void setIot(String iot) {
		this.iot = iot;
	}

	public String getAccContact() {
		return accContact;
	}

	public void setAccContact(String accContact) {
		this.accContact = accContact;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getIlcCode() {
		return ilcCode;
	}

	public void setIlcCode(String ilcCode) {
		this.ilcCode = ilcCode;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
