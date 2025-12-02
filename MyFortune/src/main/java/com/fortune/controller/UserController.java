package com.fortune.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fortune.code.LoginErrorCode;
import com.fortune.dto.common.AjaxResultDto;
import com.fortune.dto.users.SaveUserDto;
import com.fortune.service.UserService;
import com.fortune.validator.InsertCheck;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

	private final UserService userService;

	@GetMapping("/login/form")
	public String loginForm(LoginErrorCode errorCode, Model model){
		if(errorCode != null)
			model.addAttribute("errorMessage", errorCode.getErrorMessage());

		return "users/Login";
	}

	@GetMapping("/join/form")
	public String joinForm() {
		return "users/Join";
	}

	@GetMapping("/id/exists")
	public AjaxResultDto<Boolean> idExists(String newId) {
		boolean idExists = userService.idExists(newId);
		return AjaxResultDto.success(idExists);
	}

	@PostMapping("/join")
	@ResponseBody
	public AjaxResultDto<Void> join(@RequestBody @Validated({Default.class, InsertCheck.class}) SaveUserDto userDto) {
		try {
			int userIdx = userService.registUser(userDto);
			return userIdx > 0 ? AjaxResultDto.success(null) : AjaxResultDto.failure();
		} catch (IllegalArgumentException e) {
			log.error("사용자 등록 실패.", e);
			return AjaxResultDto.failure();
		}
	}
}
