package com.kutaybezci.apartment.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BalanceController {
	@RequestMapping("/balance")
	public String balance() {
		return "balance.html";
	}

}
