package com.kutaybezci.apartment.core.bl;

import java.util.List;

import com.kutaybezci.apartment.core.bl.dto.User;

public interface UserBo {
	List<User> getPersonByPartialName(String partialName);
}
