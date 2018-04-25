package com.kutaybezci.apartment.core.bl.converter;

import org.springframework.core.convert.converter.Converter;

import com.kutaybezci.apartment.core.bl.dto.User;
import com.kutaybezci.apartment.core.dal.entity.UserEntity;

public class UserEntityToDto implements Converter<UserEntity, User> {

	public User convert(UserEntity userEntity) {
		User user = new User();
		user.setUsername(userEntity.getUsername());
		user.setEnabled(userEntity.isEnabled());
		user.setPassword(userEntity.getPassword());
		user.setUserId(String.valueOf(userEntity.getUserId()));
		return user;
	}
}
