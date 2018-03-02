package com.kutaybezci.apartment.core.bl.converter;

import java.sql.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.kutaybezci.apartment.core.bl.dto.Person;
import com.kutaybezci.apartment.core.dal.entity.PersonEntity;

public class PersonDtoToEntity implements Converter<Person, PersonEntity> {

	public PersonEntity convert(Person person) {
		PersonEntity personEntity = new PersonEntity();
		personEntity.setActive(person.isActive());
		if (person.getBirthDate() != null) {
			personEntity.setBirthDate(new Date(person.getBirthDate().getTime()));
		}
		personEntity.setEmail(person.getEmail());
		personEntity.setFullName(StringUtils.upperCase(person.getFullName(), Locale.forLanguageTag("tr-TR")));
		if (person.getGender() != null) {
			personEntity.setGender(person.getGender().getCode());
		}
		personEntity.setNotes(person.getNotes());
		if (!StringUtils.isEmpty(person.getPersonCode())) {
			personEntity.setPersonId(Long.valueOf(person.getPersonCode()));
		}
		personEntity.setPhone(StringUtils.getDigits(person.getPhone()));
		return personEntity;
	}

}
