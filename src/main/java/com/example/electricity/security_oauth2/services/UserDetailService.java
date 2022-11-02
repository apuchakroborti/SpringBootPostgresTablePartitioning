package com.example.electricity.security_oauth2.services;


import com.example.electricity.dto.request.PasswordChangeRequestDto;
import com.example.electricity.dto.request.PasswordResetRequestDto;
import com.example.electricity.dto.response.PasswordChangeResponseDto;
import com.example.electricity.dto.response.ServiceResponse;
import com.example.electricity.exceptions.GenericException;
import com.example.electricity.models.CustomUser;
import com.example.electricity.repository.CustomUserRepository;
import com.example.electricity.security_oauth2.models.security.User;
import com.example.electricity.security_oauth2.repository.AuthorityRepository;
import com.example.electricity.security_oauth2.repository.UserRepository;
import com.example.electricity.utils.Defs;
import com.example.electricity.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserDetailService implements UserService, UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserDetailService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

   @Autowired
   @Qualifier("userPasswordEncoder")
   private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        logger.info("loadUserByUsername called");
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isPresent()){
            if(!optionalUser.get().isEnabled()){
                throw new UsernameNotFoundException(Defs.USER_INACTIVE+": "+username);
            }
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(Defs.USER_NOT_FOUND+": "+username);
    }

    @Override
    public ServiceResponse<PasswordChangeResponseDto> changeUserPassword(PasswordChangeRequestDto requestDto) throws GenericException {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currentUsername = currentUser.getUsername();

            UserDetails userDetails = loadUserByUsername(currentUsername);
            String currentPassword = userDetails.getPassword();
            User user = (User) userDetails;

            if (passwordEncoder.matches(requestDto.getCurrentPassword(), currentPassword)) {
                user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
                userRepository.save(user);
            } else {
                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        "password", Defs.PASSWORD_MISMATCHED,
                        "Please check password is correct"), null);
            }

            return new ServiceResponse(Utils.getSuccessResponse(),
                    new PasswordChangeResponseDto(true, Defs.PASSWORD_CHANGED_SUCCESSFUL));
        }catch (Exception e){
            logger.error("Error occurred while updating password");
            throw new GenericException("Error occurred while updating password", e);
        }
    }

    @Override
    public ServiceResponse<PasswordChangeResponseDto> resetPassword(PasswordResetRequestDto passwordResetRequestDto) throws GenericException{

        try {
            Optional<CustomUser> optionalEmployee = customUserRepository.findByEmail(passwordResetRequestDto.getUsername());
            if (!optionalEmployee.isPresent() || optionalEmployee.get().getStatus().equals(false)) {
                return new ServiceResponse<>(Utils.getSingleErrorBadRequest(
                        new ArrayList<>(),
                        "username", Defs.USER_NOT_FOUND,
                        "Please check username is correct"), null);
            }

            UserDetails userDetails = loadUserByUsername(passwordResetRequestDto.getUsername());
            User user = (User) userDetails;

            user.setPassword(passwordEncoder.encode("apu12345"));
            userRepository.save(user);

            return new ServiceResponse(Utils.getSuccessResponse(),
                    new PasswordChangeResponseDto(true,
                            Defs.PASSWORD_CHANGED_SUCCESSFUL + ": the new Password is: apu12345"));
        }catch (Exception e){
            logger.error("Error occurred while resetting password");
            throw new GenericException("Error occurred while resetting password", e);
        }
    }
}