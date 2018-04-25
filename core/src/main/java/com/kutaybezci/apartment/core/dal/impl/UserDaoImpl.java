package com.kutaybezci.apartment.core.dal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kutaybezci.apartment.core.dal.UserDao;
import com.kutaybezci.apartment.core.dal.entity.UserEntity;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	 * `user_id`, `username`, `password`, `person_id`, `enabled`
	 */
	private static final String SQL_INSERT = "insert into user(username, password, person_id, enabled)values(?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE user SET username = ? password = ? person_id = ? enabled = ? WHERE user_id = ?";
	private static final String SQL_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	private static final String SQL_SELECT_BY_ID = "SELECT * from user WHERE user_id=?";
	private static final String SQL_SELECT_BY_NAME = "SELECT * from user WHERE username=?";
	private static final String SQL_SELECT_BY_PARTIALNAME = "SELECT * from user WHERE username like ?";

	@Override
	public long insert(UserEntity user) {
		jdbcTemplate.update(SQL_INSERT,
				new Object[] { user.getUsername(), user.getPassword(), user.getPersonId(), user.isEnabled() });
		long userId = jdbcTemplate.queryForObject(SQL_LAST_INSERT_ID, Long.class);
		user.setUserId(userId);
		return userId;
	}

	@Override
	public int update(UserEntity user) {
		return jdbcTemplate.update(SQL_UPDATE, new Object[] { user.getUsername(), user.getPassword(),
				user.getPersonId(), user.isEnabled(), user.getUserId() });
	}

	@Override
	public UserEntity query(long userId) {
		return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new BeanPropertyRowMapper<UserEntity>(UserEntity.class),
				userId);
	}

	@Override
	public UserEntity query(String userName) {
		return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new BeanPropertyRowMapper<UserEntity>(UserEntity.class),
				userName);
	}

	@Override
	public List<UserEntity> queryName(String partialName) {
		partialName = "%".concat(partialName == null ? "" : partialName).concat("%");
		return jdbcTemplate.query(SQL_SELECT_BY_PARTIALNAME, new BeanPropertyRowMapper<UserEntity>(UserEntity.class),
				partialName);
	}

}
