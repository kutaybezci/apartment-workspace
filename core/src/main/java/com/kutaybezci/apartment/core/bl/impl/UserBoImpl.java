package com.kutaybezci.apartment.core.bl.impl;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.kutaybezci.apartment.core.bl.UserBo;
import com.kutaybezci.apartment.core.bl.dto.User;
import com.kutaybezci.apartment.core.dal.UserDao;
import com.kutaybezci.apartment.core.dal.entity.UserEntity;

@Service
public class UserBoImpl implements UserBo {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ConversionService conversionService;

	@Override
	public List<User> getPersonByPartialName(String partialName) {
		partialName = StringUtils.upperCase(partialName, Locale.forLanguageTag("tr-TR"));
		List<UserEntity> userEntities = userDao.queryName(partialName);
		return userEntities.stream().map(p -> conversionService.convert(p, User.class)).collect(Collectors.toList());
	}

}
