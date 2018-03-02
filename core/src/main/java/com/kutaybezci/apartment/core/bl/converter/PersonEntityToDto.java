package com.kutaybezci.apartment.core.bl.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.kutaybezci.apartment.core.bl.dto.Gender;
import com.kutaybezci.apartment.core.bl.dto.Person;
import com.kutaybezci.apartment.core.dal.entity.PersonEntity;

public class PersonEntityToDto implements Converter<PersonEntity, Person> {

	public Person convert(PersonEntity personEntity) {
		Person person = new Person();
		person.setActive(personEntity.isActive());
		if (personEntity.getBirthDate() != null) {
			// person.setBirthDate(new Date(personEntity.getBirthDate().getTime()));
			person.setBirthDate(personEntity.getBirthDate());
		}
		person.setEmail(personEntity.getEmail());
		person.setFullName(personEntity.getFullName());
		if (!StringUtils.isBlank(personEntity.getGender())) {
			person.setGender(Gender.valueOf(personEntity.getGender()));
		}
		person.setNotes(personEntity.getNotes());
		person.setPersonCode(String.valueOf(personEntity.getPersonId()));
		person.setPhone(personEntity.getPhone());
		return person;
	}

}
