package com.example.electricity.security_oauth2.services;


import com.example.electricity.dto.request.PasswordChangeRequestDto;
import com.example.electricity.dto.request.PasswordResetRequestDto;
import com.example.electricity.dto.response.PasswordChangeResponseDto;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;

public interface UserService {
    ServiceResponse<PasswordChangeResponseDto> changeUserPassword(PasswordChangeRequestDto passwordChangeRequestDto) throws GenericException;
    ServiceResponse<PasswordChangeResponseDto> resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws GenericException;
}