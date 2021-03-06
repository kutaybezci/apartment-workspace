package com.kutaybezci.apartment.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping(value = { "/login" })
	public String login() {
		return "login.html";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

	@RequestMapping("/login-page")
	public String loginPage() {
		return "";
	}
}
