package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.*;

public interface UserService {
    String registerUser(UserRegisterRequest userRegisterDTO);

    TokenRecord loginUser(UserLoginRequest userLoginRequest);

    UserDTO getUserById(Long id);

    String updateUser(Long id, UpdateUserDTO userDTO);
}
