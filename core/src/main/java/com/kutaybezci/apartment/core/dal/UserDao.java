package com.kutaybezci.apartment.core.dal;

import java.util.List;

import com.kutaybezci.apartment.core.dal.entity.UserEntity;

public interface UserDao {
	long insert(UserEntity user);

	int update(UserEntity user);

	UserEntity query(long userId);

	UserEntity query(String userName);

	List<UserEntity> queryName(String partialName);
}
