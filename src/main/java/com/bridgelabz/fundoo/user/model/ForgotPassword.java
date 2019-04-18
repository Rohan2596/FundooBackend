package com.bridgelabz.fundoo.user.model;

public class ForgotPassword {

	private String oldpassword;
	private String newpassword;
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	@Override
	public String toString() {
		return "ForgotPassword [oldpassword=" + oldpassword + ", newpassword=" + newpassword + "]";
	}
	
	
}
