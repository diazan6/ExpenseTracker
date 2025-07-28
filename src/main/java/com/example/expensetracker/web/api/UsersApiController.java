package com.example.expensetracker.web.api;

import com.example.expensetracker.service.UserService;
import com.example.expensetracker.web.dto.userDTO.UserLoginDTO;
import com.example.expensetracker.web.dto.userDTO.UserLoginResponseDTO;
import com.example.expensetracker.web.dto.userDTO.UserRegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UsersApiController {
    private final UserService userService;

    public UsersApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        userService.createUserAccount(userRegistrationDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDTO loginUser(@RequestBody UserLoginDTO userLoginDTO){
        return userService.login(userLoginDTO);
    }
}
