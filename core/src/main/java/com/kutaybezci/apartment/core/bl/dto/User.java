package com.kutaybezci.apartment.core.bl.dto;

import lombok.Data;

@Data
public class User {
	private String userId;
	private String username;
	private String password;
	private Person person;
	private boolean enabled;
}
