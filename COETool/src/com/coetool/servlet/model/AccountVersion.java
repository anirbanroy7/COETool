package com.coetool.servlet.model;

import java.util.Date;

public class AccountVersion {
	
	private int accId;
	private int versionNo;
	private int chargableFte;
	private Date costCallDate;
	private boolean approved;
	
	public AccountVersion() {}
	
	public AccountVersion(int accId,int versionNo,int chargableFte,Date costCallDate,boolean approved) 
	{
		
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public int getChargableFte() {
		return chargableFte;
	}

	public void setChargableFte(int chargableFte) {
		this.chargableFte = chargableFte;
	}

	public Date getCostCallDate() {
		return costCallDate;
	}

	public void setCostCallDate(Date costCallDate) {
		this.costCallDate = costCallDate;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	
}
