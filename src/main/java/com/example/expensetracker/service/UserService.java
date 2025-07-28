package com.example.expensetracker.service;

import com.example.expensetracker.config.JwtService;
import com.example.expensetracker.data.entity.User;
import com.example.expensetracker.data.repository.UsersRepository;
import com.example.expensetracker.exception.ConflictException;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.web.dto.userDTO.UserLoginDTO;
import com.example.expensetracker.web.dto.userDTO.UserLoginResponseDTO;
import com.example.expensetracker.web.dto.userDTO.UserRegistrationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {

        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void createUserAccount(UserRegistrationDTO userRegistrationDTO){

        if(usersRepository.findByUsernameOrEmail(userRegistrationDTO.getUsername(),userRegistrationDTO.getEmail()).isPresent()){
            throw new ConflictException("Username or Email is already taken");
        }
        User newUser = new User();
        //Here we have the email and username
        newUser.setUsername(userRegistrationDTO.getUsername().toLowerCase());
        newUser.setEmail(userRegistrationDTO.getEmail().toLowerCase());
        //Now we will set the role to USER
        newUser.setRole("USER");
        //Now we will hash the password
        String hashedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
        newUser.setPasswordHash(hashedPassword);
        usersRepository.save(newUser);
    }

    public UserLoginResponseDTO login(UserLoginDTO dto) {

        User user = usersRepository.findByUsername(dto.getUsername().toLowerCase())
                .orElseThrow(() ->
                        new NotFoundException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUserId());
        return new UserLoginResponseDTO(token);
    }
}
