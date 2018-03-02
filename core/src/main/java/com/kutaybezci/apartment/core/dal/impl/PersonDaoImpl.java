package com.kutaybezci.apartment.core.dal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kutaybezci.apartment.core.dal.PersonDao;
import com.kutaybezci.apartment.core.dal.entity.PersonEntity;

@Repository
public class PersonDaoImpl implements PersonDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_INSERT = "insert into person(full_name, gender, birth_date, phone, email, notes, active)values(?,?,?,?,?,?,?)";
	private static final String SQL_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

	public long insert(PersonEntity person) {
		jdbcTemplate.update(SQL_INSERT, new Object[] { person.getFullName(), person.getGender(), person.getBirthDate(),
				person.getPhone(), person.getEmail(), person.getNotes(), person.isActive() });
		long personId= jdbcTemplate.queryForObject(SQL_LAST_INSERT_ID, Long.class);
		person.setPersonId(personId);
		return personId;
	}

}
