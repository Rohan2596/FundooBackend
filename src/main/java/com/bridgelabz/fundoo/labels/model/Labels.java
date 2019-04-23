package com.bridgelabz.fundoo.labels.model;

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
public class Labels {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long labelId;
    private long userId;
	@NotEmpty(message = "please enter valid Name")
	@NotNull(message = "Please enter  valid lable name")
	private String labelName;
	private LocalDateTime modifiedDateTime;
	private LocalDateTime createdDateTime;

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Labels [labelId=" + labelId + ", userId=" + userId + ", labelName=" + labelName + ", modifiedDateTime="
				+ modifiedDateTime + ", createdDateTime=" + createdDateTime + "]";
	}

	

}
