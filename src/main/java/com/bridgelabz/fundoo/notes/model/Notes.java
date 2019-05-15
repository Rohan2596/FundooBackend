package com.bridgelabz.fundoo.notes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.labels.model.Labels;

@Component
@Entity
@Table
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="noteid")
	private long noteid;
	
	private long userId;
    private long labelId; 

	private String title;

	private String description;

	private LocalDateTime modifiedDate;
	private LocalDateTime createdDate;
	private boolean isPin;
	private boolean isTrash;
	private boolean isArchieve;
@ManyToMany(cascade=CascadeType.ALL)
private List<Labels> NLabels;

	
	
	public long getLabelId() {
	return labelId;
}

public void setLabelId(long labelId) {
	this.labelId = labelId;
}

	public long getId() {
		return noteid;
	}

	public void setId(long id) {
		this.noteid = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserid(long userid) {
		this.userId = userid;
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

	public List<Labels> getNLabels() {
		return NLabels;
	}

	public void setNLabels(List<Labels> nLabels) {
		NLabels = nLabels;
	}

	@Override
	public String toString() {
		return "Notes [noteid=" + noteid + ", userId=" + userId + ", labelId=" + labelId + ", title=" + title
				+ ", description=" + description + ", modifiedDate=" + modifiedDate + ", createdDate=" + createdDate
				+ ", isPin=" + isPin + ", isTrash=" + isTrash + ", isArchieve=" + isArchieve + ", NLabels=" + NLabels
				+ "]";
	}




}
