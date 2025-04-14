package com.fortune.controller;

import com.fortune.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Validated
public class UserController {

	private final UserService userService;

	@RequestMapping("/login/form")
	public String loginForm() {
		return "users/Login";
	}

	@RequestMapping("/join/form")
	public String joinForm() {
		return "users/Join";
	}

	@GetMapping("/id/exists")
	public String idExists(String newId) {

	}
}
