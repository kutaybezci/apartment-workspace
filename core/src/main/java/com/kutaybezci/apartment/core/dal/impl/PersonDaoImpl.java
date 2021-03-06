package com.kutaybezci.apartment.core.dal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kutaybezci.apartment.core.dal.PersonDao;
import com.kutaybezci.apartment.core.dal.entity.PersonEntity;

@Repository
public class PersonDaoImpl implements PersonDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_INSERT = "INSERT INTO person(full_name, gender, birth_date, phone, email, notes, active)values(?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE `apartment`.`person`\n" + "SET\n" + "`full_name` = ?,\n"
			+ "`gender` = ?,\n" + "`birth_date` = ?,\n" + "`phone` = ?,\n" + "`email` = ?,\n" + "`notes` = ?,\n"
			+ "`active` = ?\n" + "WHERE `person_id` = ?";
	private static final String SQL_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM person t WHERE t.person_id=?";
	private static final String SQL_SELECT_BY_NAME = "SELECT * FROM person t WHERE t.full_name like ?";

	@Override
	public long insert(PersonEntity person) {
		jdbcTemplate.update(SQL_INSERT, new Object[] { person.getFullName(), person.getGender(), person.getBirthDate(),
				person.getPhone(), person.getEmail(), person.getNotes(), person.isActive() });
		long personId = jdbcTemplate.queryForObject(SQL_LAST_INSERT_ID, Long.class);
		person.setPersonId(personId);
		return personId;
	}

	@Override
	public int update(PersonEntity person) {
		return jdbcTemplate.update(SQL_UPDATE,
				new Object[] { person.getFullName(), person.getGender(), person.getBirthDate(), person.getPhone(),
						person.getEmail(), person.getNotes(), person.isActive(), person.getPersonId() });
	}

	@Override
	public PersonEntity query(long personId) {
		return (PersonEntity) jdbcTemplate.queryForObject(SQL_SELECT_BY_ID,
				new BeanPropertyRowMapper<PersonEntity>(PersonEntity.class), personId);
	}

	@Override
	public List<PersonEntity> queryName(String partialName) {
		partialName = "%".concat(partialName == null ? "" : partialName).concat("%");
		return jdbcTemplate.query(SQL_SELECT_BY_NAME, new BeanPropertyRowMapper<PersonEntity>(PersonEntity.class),
				partialName);
	}
}
