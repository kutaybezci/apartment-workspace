package com.kutaybezci.apartment.core;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kutaybezci.apartment.core.bl.converter.PersonDtoToEntity;
import com.kutaybezci.apartment.core.bl.dto.Gender;
import com.kutaybezci.apartment.core.bl.dto.Person;
import com.kutaybezci.apartment.core.bl.impl.PersonBoImpl;
import com.kutaybezci.apartment.core.dal.entity.PersonEntity;

public class PersonTest {
	private Person validPerson;
	private PersonBoImpl personBoImpl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		personBoImpl = new PersonBoImpl();
		validPerson = new Person();
		validPerson.setFullName("ASD ASD");
		validPerson.setActive(true);
		Date now = new Date();
		Date twentyYearsAgo = DateUtils.addYears(now, -20);
		validPerson.setBirthDate(twentyYearsAgo);
		validPerson.setEmail("asd@asd.com");
		validPerson.setGender(Gender.Male);
		validPerson.setNotes("Bla Bla Bla");
		validPerson.setPhone("123456789");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidate() {
		personBoImpl.validate(validPerson);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testValidateNullPerson() {
		personBoImpl.validate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullName() {
		validPerson.setFullName(null);
		personBoImpl.validate(validPerson);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		validPerson.setFullName("");
		personBoImpl.validate(validPerson);
	}

	@Test
	public void testNullEmail() {
		validPerson.setEmail(null);
		personBoImpl.validate(validPerson);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongEmail() {
		validPerson.setEmail("wrong_email");
		personBoImpl.validate(validPerson);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongPersonCode() {
		validPerson.setPersonCode("wrong_personCode");
		personBoImpl.validate(validPerson);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShortPhone() {
		validPerson.setPhone("12345");
		personBoImpl.validate(validPerson);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLongPhone() {
		validPerson.setPhone("123457890123456789012345");
		personBoImpl.validate(validPerson);
	}

	@Test
	public void testPersonDtoToEntity() {
		validPerson.setFullName("çişli");
		validPerson.setPhone("ASD123ASD");
		PersonEntity personEntity = new PersonDtoToEntity().convert(validPerson);
		assertEquals(personEntity.getFullName(), "ÇİŞLİ");
		assertEquals(personEntity.getPhone(), "123");
	}

}
