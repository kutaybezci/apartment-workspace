package com.kutaybezci.apartment.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kutaybezci.apartment.core.bl.UserBo;
import com.kutaybezci.apartment.core.bl.dto.User;

@Controller
public class UserController {
	@Autowired
	private UserBo userBo;

	@RequestMapping(value = { "/user" })
	public String user() {
		return "user.html";
	}

	@ModelAttribute("userQueryResult")
	public List<User> getUserList(@RequestParam(required = false, name = "username") String partialName) {
		List<User> userList = userBo.getPersonByPartialName(partialName);
		return userList;
	}
}
