package com.kutaybezci.apartment.core.dal;

import java.util.List;

import com.kutaybezci.apartment.core.dal.entity.PersonEntity;

public interface PersonDao {
	long insert(PersonEntity person);

	int update(PersonEntity person);

	PersonEntity query(long personId);

	List<PersonEntity> queryName(String partialName);
}
