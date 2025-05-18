package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.*;
import com.job.lk.webapp.service.ImageUploadService;
import com.job.lk.webapp.service.UserService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final ImageUploadService imageUploadService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestPart(value ="name") String name,
                                           @RequestPart(value ="email") String email,
                                           @RequestPart(value ="password") String password,
                                           @RequestPart(value ="role") String role,
                                           @RequestPart(value = "profilePicture", required = false)MultipartFile profilePicture){
        var userRegisterDTO = UserRegisterRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role).build();

        if(profilePicture != null && !profilePicture.isEmpty()){
            String profilePictureUrl = imageUploadService.uploadFile(profilePicture);
            userRegisterDTO.setProfilePicture(profilePictureUrl);
        }

        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenRecord> login(@RequestBody UserLoginRequest userLoginRequest){

        return ResponseEntity.ok(userService.loginUser(userLoginRequest));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") Long id){

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<JsonResponse> updateUser(@PathVariable(value = "id") Long id,
                                                   @RequestPart(value = "name") String name,
                                                   @RequestPart(value = "email") String email,
                                                   @RequestPart(value = "password") String password,
                                                   @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {

        var userDTO = UpdateUserDTO.builder()
                .name(name)
                .email(email)
                .password(password).build();

        if(profilePicture != null && !profilePicture.isEmpty()){
            String profilePictureUrl = imageUploadService.uploadFile(profilePicture); // Upload image
            userDTO.setProfilePicture(profilePictureUrl);
        }
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                userService.updateUser(id,userDTO)), HttpStatus.OK);
    }
}
