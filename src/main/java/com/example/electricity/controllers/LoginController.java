package com.example.electricity.controllers;

import com.example.electricity.dto.CustomUserDto;
import com.example.electricity.dto.request.LoginRequestDto;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;
import com.example.electricity.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    /*
    * this API is for checking the user is valid by username and password
    * @response: employee details
    * */
    @PostMapping
    public ServiceResponse<CustomUserDto> checkLoginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) throws GenericException {
        return loginService.checkLoginUser(loginRequestDto);
    }
}
