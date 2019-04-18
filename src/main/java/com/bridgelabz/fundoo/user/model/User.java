
package com.bridgelabz.fundoo.user.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty(message = "Please provide valid Name")
	@NotNull(message = "Please provide Valid Name")
	private String name;

	@Email(message = "Please provide Valid Emailid")
	private String emailId;

	@NotNull(message = "Please provide Valid Phone number")
	@NotEmpty(message = "Please provide Valid Phone Number")
	private String phNumber;

	@NotNull(message = "Please provide Valid Password")
	@NotEmpty(message = "Please provide Valid Password")
	private String password;

	private boolean isVerified = false;
	private LocalDateTime registeredDate;
	private LocalDateTime modifiedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPhNumber() {
		return phNumber;
	}

	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public LocalDateTime getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public User() {
		super();

	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", emailId=" + emailId + ", phNumber=" + phNumber + ", password="
				+ password + ", isVerified=" + isVerified + ", registeredDate=" + registeredDate + ", modifiedDate="
				+ modifiedDate + "]";
	}

}