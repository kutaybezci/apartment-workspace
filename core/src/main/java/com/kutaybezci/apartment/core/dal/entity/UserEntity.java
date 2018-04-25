package com.kutaybezci.apartment.core.dal.entity;

import lombok.Data;

@Data
public class UserEntity {
	private long userId;
	private String username;
	private String password;
	private Long personId;
	private boolean enabled;
}
