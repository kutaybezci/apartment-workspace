package com.kutaybezci.apartment.shell.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.kutaybezci.apartment.core.bl.PersonBo;
import com.kutaybezci.apartment.core.bl.dto.Person;

@ShellComponent
public class PersonCommand {
	@Autowired
	private PersonBo personBo;

	@ShellMethod("Create new person")
	public String createPerson(String fullname, @ShellOption(defaultValue = "") String email,
			@ShellOption(defaultValue = "", help = "non numeric characters will be ignored") String phone,
			@ShellOption(defaultValue = "", help = "DD.MM.YYYY") String birthDate) throws ParseException {
		Person person = new Person();
		person.setFullName(fullname);
		person.setEmail(email);
		person.setPhone(phone);
		if (!StringUtils.isBlank(birthDate)) {
			person.setBirthDate(new SimpleDateFormat("DD.MM.YYYY").parse(birthDate));
		}
		String personCode = personBo.create(person);
		return String.format("New person created with id:%s", personCode);
	}
}
