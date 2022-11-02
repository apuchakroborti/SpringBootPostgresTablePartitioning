package com.example.electricity.dto.request;

import lombok.Data;

@Data
public class CustomUserSearchCriteria {

    private Long id;
    private String email;
    private String phone;
    private String userId;
    private String firstName;
    private String lastName;
}