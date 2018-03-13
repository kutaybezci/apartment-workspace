package com.kutaybezci.apartment.core.bl;

import java.util.List;

import com.kutaybezci.apartment.core.bl.dto.Person;

public interface PersonBo {
	String create(Person person);

	void update(Person person) throws Exception;

	Person getPersonByCode(String personCode);

	List<Person> getPersonByName(String partialName);
}
