package org.ashan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashan.domain.dto.user.LoginRequest;
import org.ashan.domain.dto.user.LoginResponse;
import org.ashan.domain.dto.user.RegisterRequest;
import org.ashan.domain.entity.User;
import org.ashan.domain.enums.UserStatus;
import org.ashan.repository.UserRepository;
import org.ashan.service.NotificationService;
import org.ashan.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void registerUser(RegisterRequest registerUser) {

        String name = registerUser.getName();
        String email = registerUser.getEmail();
        String password = registerUser.getPassword();

        boolean isUserExist = userRepository.existsByEmail(email);

        if(isUserExist){
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "User with this email already exists"
            );
        }

    }

    @Override
    public LoginResponse  login(LoginRequest loginRequest){

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));

        if(user.getStatus() == UserStatus.INACTIVE){
            throw new DisabledException("User account is inactive");
        }

        boolean isPasswordMatched = passwordEncoder.matches(password, user.getPassword());

        if(isPasswordMatched){
            LoginResponse dto = new LoginResponse();
            dto.setEmail(user.getEmail());
            dto.setName(user.getName());
            dto.setLoginToken(generateLoginToken(user));

            return dto;
        }else{
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    private String generateLoginToken(User user){
        return "CUS"+user.getId()+System.currentTimeMillis();
    }

    public void sendUserWelcomeEmail(){
        User user = userRepository.findFirstByIsInvitationSendFalse();

        if(user !=null){
            String subject = "Welcome to Spring Boot Application";
            boolean isMailSent = notificationService.sendMail(user.getEmail(),subject,user.getName());

            if(isMailSent){
                int isUpdated = userRepository.updateInvitationStatus(user.getId());
                if(isUpdated == 0) {
                    log.error("Email sent successfully. But database error occurred");
                }else{
                    log.info("Email sent successfully");
                }
            }else{
                log.error("An error occurred while sending email");
            }
        }else{
            log.info("No new users");
        }

    }
}
