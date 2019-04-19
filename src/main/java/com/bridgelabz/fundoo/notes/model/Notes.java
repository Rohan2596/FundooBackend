package com.bridgelabz.fundoo.notes.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull(message = "Please provide valid Title")
	@NotEmpty(message = "Please provide valid Title")
	private String title;

	private String description;

	private LocalDateTime modifiedDate;
	private LocalDateTime createdDate;
    private boolean isPin;
    private boolean isTrash;
    private boolean isArchieve;				


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}


	public boolean isPin() {
		return isPin;
	}


	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}


	public boolean isTrash() {
		return isTrash;
	}


	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}


	public boolean isArchieve() {
		return isArchieve;
	}


	public void setArchieve(boolean isArchieve) {
		this.isArchieve = isArchieve;
	}


	@Override
	public String toString() {
		return "Notes [id=" + id + ", title=" + title + ", description=" + description + ", modifiedDate="
				+ modifiedDate + ", createdDate=" + createdDate + ", isPin=" + isPin + ", isTrash=" + isTrash
				+ ", isArchieve=" + isArchieve + "]";
	}


	

}
