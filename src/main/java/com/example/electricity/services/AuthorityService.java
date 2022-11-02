package com.example.electricity.services;

import com.example.electricity.dto.AuthorityModel;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;


public interface AuthorityService {
    ServiceResponse<AuthorityModel> addNewAuthority(AuthorityModel authorityModel) throws GenericException;
}
