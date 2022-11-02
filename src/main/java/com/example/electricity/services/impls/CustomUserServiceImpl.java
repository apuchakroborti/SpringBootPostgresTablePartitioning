package com.example.electricity.services.impls;

import com.example.electricity.dto.request.CustomUserSearchCriteria;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;
import com.example.electricity.dto.CustomUserDto;
import com.example.electricity.models.CustomUser;
import com.example.electricity.repository.CustomUserRepository;
import com.example.electricity.security_oauth2.models.security.Authority;
import com.example.electricity.security_oauth2.models.security.User;
import com.example.electricity.security_oauth2.repository.AuthorityRepository;
import com.example.electricity.security_oauth2.repository.UserRepository;
import com.example.electricity.services.CustomUserService;
import com.example.electricity.specifications.CustomUserSearchSpecifications;
import com.example.electricity.utils.Defs;
import com.example.electricity.utils.Role;
import com.example.electricity.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
public class CustomUserServiceImpl implements CustomUserService {

    Logger logger = LoggerFactory.getLogger(CustomUserServiceImpl.class);

    private final CustomUserRepository customUserRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Qualifier("userPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserServiceImpl(
            CustomUserRepository customUserRepository,
            UserRepository userRepository,
            AuthorityRepository authorityRepository
    ){
        this.customUserRepository = customUserRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    private User addOauthUser(CustomUser employee, String password) throws GenericException {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(employee.getEmail());
            if (optionalUser.isPresent()) {
                logger.error("User already exists, email: {}", employee.getEmail());
                throw new GenericException(Defs.USER_ALREADY_EXISTS);
            }

            User user = new User();
            user.setUsername(employee.getEmail());
            user.setEnabled(true);

            Authority authority = authorityRepository.findByName(Role.EMPLOYEE.getValue());
            user.setAuthorities(Arrays.asList(authority));
            user.setPassword(passwordEncoder.encode(password));

            userRepository.save(user);
            return user;
        }catch (GenericException e){
            throw e;
        }catch (Exception e){
            logger.error("Error occurred while creating oauth user!");
            throw new GenericException("Error occurred while creating oauth user!", e);
        }
    }
    @Override
    public ServiceResponse<CustomUserDto> enrollEmployee(CustomUserDto customUserDto) throws GenericException {
        try {
            Optional<CustomUser> optionalEmployee = customUserRepository.findByUserId(customUserDto.getUserId());
            if (optionalEmployee.isPresent()) throw new GenericException(Defs.USER_ALREADY_EXISTS);

            CustomUser employee = new CustomUser();
            Utils.copyProperty(customUserDto, employee);

            User user = addOauthUser(employee, customUserDto.getPassword());
            employee.setOauthUser(user);
            employee.setStatus(true);

            employee.setCreatedBy(1l);
            employee.setCreateTime(LocalDateTime.now());

            employee = customUserRepository.save(employee);


            Utils.copyProperty(employee, customUserDto);
            return new ServiceResponse(Utils.getSuccessResponse(), customUserDto);
        }catch (GenericException e){
            throw e;
        }catch (Exception e){
            logger.error("Exception occurred while enrolling employee, message: {}", e.getMessage());
            throw new GenericException(e.getMessage(), e);
        }
    }



    @Override
    public CustomUserDto findByUsername(String username) throws GenericException{

        try {
            Optional<CustomUser> optionalEmployee = customUserRepository.findByEmail(username);
            if (!optionalEmployee.isPresent() || optionalEmployee.get().getStatus().equals(false)) {
                return null;
            }
            CustomUserDto customUserDto = new CustomUserDto();
            Utils.copyProperty(optionalEmployee.get(), customUserDto);
            return customUserDto;
        }catch (Exception e){
            logger.error("Error while finding employee by username: {}", username);
            throw new GenericException(e.getMessage());
        }
    }
    @Override
    public ServiceResponse<CustomUserDto> findEmployeeById(Long id) throws GenericException{
        try {
            Optional<CustomUser> optionalUser = customUserRepository.findById(id);

            if (!optionalUser.isPresent() || optionalUser.get().getStatus().equals(false)) {
                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        "employeeId", Defs.EMPLOYEE_NOT_FOUND,
                        "Please check employee id is correct"), null);
            } else {
                CustomUserDto customUserDto = new CustomUserDto();
                Utils.copyProperty(optionalUser.get(), customUserDto);
                return new ServiceResponse(Utils.getSuccessResponse(), customUserDto);
            }
        }catch (Exception e){
            logger.error("Error occurred while fetching employee by id: {}", id);
            throw new GenericException("Error occurred while fetching employee by id", e);
        }
    }

    @Override
    public ServiceResponse<CustomUserDto> updateEmployeeById(Long id, CustomUserDto customUserDto) throws GenericException{
        try {
            Optional<CustomUser> loggedInEmployee = customUserRepository.getLoggedInEmployee();
            if (loggedInEmployee.isPresent() && !loggedInEmployee.get().getId().equals(id)) {
                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        null, Defs.NO_PERMISSION_TO_DELETE,
                        "Please check you have permission to do this operation!"), null);
            }

            Optional<CustomUser> optionalEmployee = customUserRepository.findById(id);
            if (!optionalEmployee.isPresent() || optionalEmployee.get().getStatus().equals(false)){
                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        "employeeId", Defs.EMPLOYEE_NOT_FOUND,
                        "Please check employee id is correct"), null);
            }


            CustomUser employee = optionalEmployee.get();
            if (!Utils.isNullOrEmpty(customUserDto.getFirstName())) {
                employee.setFirstName(customUserDto.getFirstName());
            }
            if (!Utils.isNullOrEmpty(customUserDto.getLastName())) {
                employee.setLastName(customUserDto.getLastName());
            }
            employee = customUserRepository.save(employee);

            Utils.copyProperty(employee, customUserDto);

            return new ServiceResponse(Utils.getSuccessResponse(), customUserDto);
        }catch (Exception e){
         logger.error("Exception occurred while updating employee, id: {}", id);
         throw new GenericException(e.getMessage(), e);
        }
    }

    @Override
    public Page<CustomUser> getEmployeeList(CustomUserSearchCriteria criteria, @PageableDefault(value = 10) Pageable pageable) throws GenericException{
        Optional<CustomUser> loggedInEmployee = customUserRepository.getLoggedInEmployee();
        Long id = null;
        if(loggedInEmployee.isPresent()){
            id = loggedInEmployee.get().getId();
        }

        Page<CustomUser> userPage = customUserRepository.findAll(
                CustomUserSearchSpecifications.withId(id==null ? criteria.getId() : id)
                        .and(CustomUserSearchSpecifications.withFirstName(criteria.getFirstName()))
                        .and(CustomUserSearchSpecifications.withLastName(criteria.getLastName()))
                        .and(CustomUserSearchSpecifications.withEmail(criteria.getEmail()))
                        .and(CustomUserSearchSpecifications.withPhone(criteria.getPhone()))
                        .and(CustomUserSearchSpecifications.withStatus(true))
                ,pageable
        );
        return userPage;
    }

    @Override
    public ServiceResponse<Boolean> deleteEmployeeById(Long id) throws GenericException{
        try {
            Optional<CustomUser> loggedInEmployee = customUserRepository.getLoggedInEmployee();
            Optional<CustomUser> optionalEmployee = customUserRepository.findById(id);
            if (loggedInEmployee.isPresent() && optionalEmployee.isPresent() &&
                    !loggedInEmployee.get().getId().equals(optionalEmployee.get().getId())) {

                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        null, Defs.NO_PERMISSION_TO_DELETE,
                        "Please check you have permission to do this operation!"), null);
            }
            if (!optionalEmployee.isPresent()) {
                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        "employeeId", Defs.EMPLOYEE_NOT_FOUND,
                        "Please check employee id is correct"), null);
            }

            CustomUser employee = optionalEmployee.get();
            employee.setStatus(false);
            try {
                employee = customUserRepository.save(employee);
                User user = employee.getOauthUser();
                user.setEnabled(false);
                userRepository.save(user);
            } catch (Exception e) {
                logger.error(Defs.EXCEPTION_OCCURRED_WHILE_SAVING_USER_INFO+", message: {}", e.getMessage());
                throw new GenericException(Defs.EXCEPTION_OCCURRED_WHILE_SAVING_USER_INFO, e);
            }
            return new ServiceResponse(Utils.getSuccessResponse(), true);
        } catch (GenericException e){
            throw e;
        }catch (Exception e){
            logger.error("Exception occurred while deleting user, user id: {}", id);
            throw new GenericException(e.getMessage(), e);
        }
    }
}
