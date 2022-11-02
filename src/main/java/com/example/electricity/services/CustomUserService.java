package com.example.electricity.services;

import com.example.electricity.dto.request.CustomUserSearchCriteria;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;
import com.example.electricity.dto.CustomUserDto;
import com.example.electricity.models.CustomUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomUserService {
    ServiceResponse<CustomUserDto> enrollEmployee(CustomUserDto user) throws GenericException;
    CustomUserDto findByUsername(String username) throws GenericException;
    ServiceResponse<CustomUserDto> findEmployeeById(Long id) throws GenericException;
    ServiceResponse<CustomUserDto> updateEmployeeById(Long id, CustomUserDto employeeBean) throws GenericException;
    Page<CustomUser> getEmployeeList(CustomUserSearchCriteria criteria, Pageable pageable) throws GenericException;
    ServiceResponse<Boolean>  deleteEmployeeById(Long id) throws GenericException;
}
