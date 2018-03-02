package com.kutaybezci.apartment.shell.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.kutaybezci.apartment.core.bl.PersonBo;
import com.kutaybezci.apartment.core.bl.dto.Gender;
import com.kutaybezci.apartment.core.bl.dto.Person;

@ShellComponent
public class PersonCommand {
	private static final String UNTOUCHED_ARG = "*";
	@Autowired
	private PersonBo personBo;

	@ShellMethod("Create new person")
	public String createPerson(String fullname, @ShellOption(defaultValue = "") String email,
			@ShellOption(defaultValue = "", help = "non numeric characters will be ignored") String phone,
			@ShellOption(defaultValue = "", help = "DD.MM.YYYY") String birthDate,
			@ShellOption(defaultValue = "", help = "(M)ale, (F)emale") String gender) throws ParseException {
		Person person = new Person();
		person.setFullName(fullname);
		person.setEmail(email);
		person.setPhone(phone);
		person.setGender(Gender.valueOf(gender));
		person.setActive(true);
		if (!StringUtils.isBlank(birthDate)) {
			person.setBirthDate(new SimpleDateFormat("dd.MM.yyyy").parse(birthDate));
		}
		String personCode = personBo.create(person);
		return String.format("New person created with id:%s", personCode);
	}

	@ShellMethod("Update existing person")
	public String updatePerson(String personCode, @ShellOption(defaultValue = "*") String fullname,
			@ShellOption(defaultValue = "*") String email,
			@ShellOption(defaultValue = "*", help = "non numeric characters will be ignored") String phone,
			@ShellOption(defaultValue = "*", help = "DD.MM.YYYY") String birthDate) throws ParseException {
		Person person = personBo.getPersonByCode(personCode);
		if (!UNTOUCHED_ARG.equals(fullname)) {
			person.setFullName(fullname);
		}
		if (!UNTOUCHED_ARG.equals(email)) {
			person.setEmail(email);
		}
		if (!UNTOUCHED_ARG.equals(phone)) {
			person.setPhone(phone);
		}
		if (!UNTOUCHED_ARG.equals(birthDate)) {
			if (!StringUtils.isBlank(birthDate)) {
				person.setBirthDate(new SimpleDateFormat("dd.MM.yyyy").parse(birthDate));
			} else {
				person.setBirthDate(null);
			}
		}
		try {
			personBo.update(person);
		} catch (Exception e) {
			return e.getMessage();
		}
		return String.format("Person updated");
	}

}
