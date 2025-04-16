package com.fortune.controller;

import com.fortune.code.LoginErrorCode;
import com.fortune.dto.common.AjaxResultDto;
import com.fortune.dto.users.RegistUserDto;
import com.fortune.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Validated
public class UserController {

	private final UserService userService;

	@RequestMapping("/login/form")
	public String loginForm(LoginErrorCode errorCode, Model model){
		if(errorCode != null)
			model.addAttribute("errorMessage", errorCode.getErrorMessage());

		return "users/Login";
	}

	@RequestMapping("/join/form")
	public String joinForm() {
		return "users/Join";
	}

	@GetMapping("/id/exists")
	public AjaxResultDto<Boolean> idExists(String newId) {
		boolean idExists = userService.idExists(newId);
		return AjaxResultDto.success(idExists);
	}

	@PostMapping("/join")
	public AjaxResultDto<Void> join(@RequestBody @Valid RegistUserDto userDto) {
		int userIdx = userService.registUser(userDto);
		return userIdx > 0 ? AjaxResultDto.success(null) : AjaxResultDto.failure();
	}
}
