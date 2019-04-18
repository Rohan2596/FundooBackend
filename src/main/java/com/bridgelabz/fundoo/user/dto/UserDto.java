package com.bridgelabz.fundoo.user.dto;

public class UserDto {
private String name;
private String emailId;
private String password;
private String phNumber;

public UserDto(String name, String emailId, String password, String phNumber) {
	super();
	this.name = name;
	this.emailId = emailId;
	this.password = password;
	this.phNumber = phNumber;
}

@Override
public String toString() {
	return "UserDto [name=" + name + ", emailId=" + emailId + ", password=" + password + ", phNumber=" + phNumber + "]";
}


public UserDto() {
	super();
	
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhNumber() {
	return phNumber;
}
public void setPhNumber(String phNumber) {
	this.phNumber = phNumber;
}

}
