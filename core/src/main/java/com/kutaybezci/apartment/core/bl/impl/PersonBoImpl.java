package com.kutaybezci.apartment.core.bl.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kutaybezci.apartment.core.bl.PersonBo;
import com.kutaybezci.apartment.core.bl.dto.Person;
import com.kutaybezci.apartment.core.dal.PersonDao;
import com.kutaybezci.apartment.core.dal.entity.PersonEntity;

@Service
public class PersonBoImpl implements PersonBo {
	@Autowired
	private PersonDao personDao;
	@Autowired
	private ConversionService conversionService;

	public void validate(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("Validated person cannot be null");
		}
		if (StringUtils.isBlank(person.getFullName())) {
			throw new IllegalArgumentException("Person fullName cannot be blank");
		}

		if (!StringUtils.isBlank(person.getEmail())) {
			if (!EmailValidator.getInstance().isValid(person.getEmail())) {
				throw new IllegalArgumentException("Person email address is not valid");
			}
		}
		if (person.getBirthDate() != null) {
			Date today = new Date();
			if (person.getBirthDate().after(today)) {
				throw new IllegalArgumentException("Person birthdate cannot be in the future");
			}
			Date ago200years = DateUtils.addYears(today, -200);
			if (person.getBirthDate().before(ago200years)) {
				throw new IllegalArgumentException("Person birthdate must be within 200 years");
			}
		}
		if (!StringUtils.isEmpty(person.getPersonCode())) {
			try {
				Long.valueOf(person.getPersonCode());
			} catch (NumberFormatException nex) {
				throw new IllegalArgumentException("Person code must be numeric");
			}
		}
		if (!StringUtils.isBlank(person.getPhone())) {
			int size = StringUtils.getDigits(person.getPhone()).length();
			if (size < 7 || size > 20) {
				throw new IllegalArgumentException("Phone number must be between 7 and 20 digits");
			}
		}
	}

	@Transactional
	public String create(Person person) {
		validate(person);
		PersonEntity personEntity = conversionService.convert(person, PersonEntity.class);
		person.setPersonCode(String.valueOf(personDao.insert(personEntity)));
		return person.getPersonCode();
	}

	@Transactional
	public void update(Person person) throws Exception {
		validate(person);
		PersonEntity personEntity = conversionService.convert(person, PersonEntity.class);
		if (personDao.update(personEntity) == 0) {
			throw new Exception("Person code not found operation failed");
		}
	}

	public Person getPersonByCode(String personCode) {
		PersonEntity personEntity = personDao.query(Long.valueOf(personCode));
		return conversionService.convert(personEntity, Person.class);
	}

	public List<Person> getPersonByName(String partialName) {
		partialName = StringUtils.upperCase(partialName, Locale.forLanguageTag("tr-TR"));
		List<PersonEntity> personEntities = personDao.queryName(partialName);
		return personEntities.stream().map(p -> conversionService.convert(p, Person.class))
				.collect(Collectors.toList());
	}

}
