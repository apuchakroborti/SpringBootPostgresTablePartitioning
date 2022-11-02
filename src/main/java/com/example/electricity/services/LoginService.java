package com.example.electricity.services;


import com.example.electricity.dto.CustomUserDto;
import com.example.electricity.dto.request.LoginRequestDto;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;

public interface LoginService {
    ServiceResponse<CustomUserDto> checkLoginUser(LoginRequestDto loginRequestDto) throws GenericException;

}
