package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.*;
import com.job.lk.webapp.entity.User;
import com.job.lk.webapp.exception.coustom.PasswordUnMatchedError;
import com.job.lk.webapp.exception.coustom.UserAlreadyExist;
import com.job.lk.webapp.exception.coustom.UserNotFoundError;
import com.job.lk.webapp.repository.UserRepository;
import com.job.lk.webapp.service.UserService;
import com.job.lk.webapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String registerUser(UserRegisterRequest userRegisterDTO) {

        if(userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()){
            throw new UserAlreadyExist("User Already Exists.....");
        }

        var user = User.builder()
                .name(userRegisterDTO.getName())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .role(userRegisterDTO.getRole())
                .profilePicture(userRegisterDTO.getProfilePicture()).build();

        userRepository.save(user);
        return "User Register Successfully....";

    }

    @Override
    public TokenRecord loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginRequest.getEmail());

        if(userOptional.isEmpty()){
            throw new UserNotFoundError("User Not Registerd Yet...");
        }
        if(!passwordEncoder.matches(userLoginRequest.getPassword(),userOptional.get().getPassword())){
            throw new PasswordUnMatchedError("Invalid Password....");
        }
        return new TokenRecord(jwtUtil.generateToken(userLoginRequest.getEmail()
                ,userOptional.get().getRole(),userOptional.get().getId()));
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole())
                    .profilePicture(user.getProfilePicture()).build();
        }
        throw new UserNotFoundError("User Not Found With id : "+ id);
    }

    @Override
    public String updateUser(Long id, UpdateUserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundError("User Not Found");
        }
        User user = userOptional.get();
        // Check if the email is being changed and is already used by another user
        if (!user.getEmail().equals(userDTO.getEmail())) {
            Optional<User> userByEmail = userRepository.findByEmail(userDTO.getEmail());
            if (userByEmail.isPresent() && !userByEmail.get().getId().equals(id)) {
                throw new UserAlreadyExist("Email is already in use by another account.");
            }
        }

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userDTO.getProfilePicture() != null) {
            user.setProfilePicture(userDTO.getProfilePicture());
        }

        userRepository.save(user);
        return "User updated successfully!";
    }
}
