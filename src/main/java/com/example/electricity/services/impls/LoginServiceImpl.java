package com.example.electricity.services.impls;

import com.example.electricity.dto.CustomUserDto;
import com.example.electricity.dto.request.LoginRequestDto;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;
import com.example.electricity.models.CustomUser;
import com.example.electricity.repository.CustomUserRepository;
import com.example.electricity.security_oauth2.models.security.User;
import com.example.electricity.security_oauth2.repository.UserRepository;
import com.example.electricity.services.LoginService;
import com.example.electricity.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    CustomUserRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier("userPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public ServiceResponse<CustomUserDto> checkLoginUser(LoginRequestDto loginRequestDto) throws GenericException {
        try {
            CustomUserDto customUserDto = new CustomUserDto();

            Optional<User> optionalUser = userRepository.findByUsername(loginRequestDto.getUsername());
            if (optionalUser.isPresent()) {
                if (passwordEncoder.matches(loginRequestDto.getPassword(), optionalUser.get().getPassword())) {
                    Optional<CustomUser> optionalEmployee = employeeRepository.findByEmail(loginRequestDto.getUsername());
                    if (optionalEmployee.isPresent()) {
                        Utils.copyProperty(optionalEmployee.get(), customUserDto);
                    } else {
                        customUserDto.setFirstName("Admin");
                        customUserDto.setLastName("Admin");
                    }
                } else {
                    return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                            new ArrayList<>(),
                            "username, password", null,
                            "Please check username or password is incorrect"), null);
                }
            } else {
                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        "username, password", null,
                        "Please check username or password is incorrect"), null);
            }
            return new ServiceResponse(Utils.getSuccessResponse(), customUserDto);
        }catch (Exception e){
            logger.error("Exception occurred while checking login user, message: {}", e.getMessage());
            throw new GenericException(e.getMessage(), e);
        }
    }
}
