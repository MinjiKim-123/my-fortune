package com.fortune.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {

	@RequestMapping("/login/form")
	public String loginForm() {
		return "users/Login";
	}
}
