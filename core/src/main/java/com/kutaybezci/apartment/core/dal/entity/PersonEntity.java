package com.kutaybezci.apartment.core.dal.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class PersonEntity {
	private long personId;
	private String fullName;
	private String gender;
	private Date birthDate;
	private String phone;
	private String email;
	private String notes;
	private boolean active;	
}

