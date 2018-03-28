package com.kutaybezci.apartment.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kutaybezci.apartment.core.bl.PersonBo;
import com.kutaybezci.apartment.core.bl.dto.Person;

@RestController
class PersonController {
	@Autowired
	private PersonBo personBo;

	@RequestMapping(path = "/person/queryName/{partialName}")
	public List<Person> queryName(@PathVariable("partialName") String partialName) {
		return personBo.getPersonByName(partialName);
	}

	@RequestMapping(path = "/person/get/{personCode}")
	public Person getPerson(@PathVariable("personCode") String personCode) {
		return personBo.getPersonByCode(personCode);
	}

	@RequestMapping(path = "/person/create", method = RequestMethod.POST)
	public Person createPerson(@RequestBody Person person) {
		String personCode = personBo.create(person);
		return personBo.getPersonByCode(personCode);
	}

}
