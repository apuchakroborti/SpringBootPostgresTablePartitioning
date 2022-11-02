package com.example.electricity.controllers;

import com.example.electricity.dto.request.PasswordChangeRequestDto;
import com.example.electricity.dto.request.PasswordResetRequestDto;
import com.example.electricity.dto.request.CustomUserSearchCriteria;
import com.example.electricity.dto.response.Pagination;
import com.example.electricity.dto.response.PasswordChangeResponseDto;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;
import com.example.electricity.dto.CustomUserDto;
import com.example.electricity.models.CustomUser;
import com.example.electricity.security_oauth2.services.UserService;
import com.example.electricity.services.CustomUserService;
import com.example.electricity.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class CustomUserController {

    private final CustomUserService customUserService;
    private final UserService userService;

    @Autowired
    CustomUserController(CustomUserService customUserService,
                         UserService userService){
        this.customUserService = customUserService;
        this.userService = userService;
    }

    @PostMapping
    public ServiceResponse<CustomUserDto> signUpUser(@Valid @RequestBody CustomUserDto customUserDto) throws GenericException {
        return customUserService.enrollEmployee(customUserDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ServiceResponse<Page<CustomUserDto>> searchEmployee(CustomUserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException {
        Page<CustomUser>  employeePage = customUserService.getEmployeeList(criteria, pageable);

        return new ServiceResponse(Utils.getSuccessResponse(),
                Utils.toDtoList(employeePage, CustomUserDto.class),
                new Pagination(employeePage.getTotalElements(), employeePage.getNumberOfElements(), employeePage.getNumber(), employeePage.getSize()));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(path = "/{id}")
    public ServiceResponse<CustomUserDto> getEmployeeById(@PathVariable(name = "id") Long id ) throws GenericException {
        return customUserService.findEmployeeById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ServiceResponse<CustomUserDto> updateEmployeeById(@PathVariable(name = "id") Long id, @RequestBody CustomUserDto employeeBean) throws GenericException {
        return customUserService.updateEmployeeById(id, employeeBean);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ServiceResponse<Boolean> deleteEmployeeById(@PathVariable(name = "id") Long id) throws GenericException {
        return customUserService.deleteEmployeeById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/update-password")
    public ServiceResponse<PasswordChangeResponseDto> updatePassword(@RequestBody PasswordChangeRequestDto passwordChangeRequestDto) throws GenericException {
        return userService.changeUserPassword(passwordChangeRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/reset-password")
    public ServiceResponse<PasswordChangeResponseDto> resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) throws GenericException {
        return userService.resetPassword(passwordResetRequestDto);
    }
}
