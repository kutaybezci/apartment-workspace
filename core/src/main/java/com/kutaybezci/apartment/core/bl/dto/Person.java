package com.kutaybezci.apartment.core.bl.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Person implements Serializable {

	private static final long serialVersionUID = 6103800940928849986L;
	private String personCode;
	private String fullName;
	private Gender gender;
	private Date birthDate;
	private String phone;
	private String email;
	private String notes;
	private boolean active;
}
