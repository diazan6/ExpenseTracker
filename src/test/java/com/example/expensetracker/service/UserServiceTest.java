package com.example.expensetracker.service;

import com.example.expensetracker.config.JwtService;
import com.example.expensetracker.data.entity.User;
import com.example.expensetracker.data.repository.UsersRepository;
import com.example.expensetracker.exception.ConflictException;
import com.example.expensetracker.web.dto.userDTO.UserLoginDTO;
import com.example.expensetracker.web.dto.userDTO.UserLoginResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.expensetracker.web.dto.userDTO.UserRegistrationDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class UserServiceTest {
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private UserService userService;

    public UserServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserWithHashedPassword(){
    UserRegistrationDTO userDTO = new UserRegistrationDTO("test123@example.com","test123Username", "SecretPassword");
    when(passwordEncoder.encode("SecretPassword")).thenReturn("hashed_pw");

    userService.createUserAccount(userDTO);
    verify(usersRepository).save(argThat(user ->
            user.getUsername().equals("test123username") &&
            user.getEmail().equals("test123@example.com") &&
            user.getPasswordHash().equals("hashed_pw") &&
            user.getRole().equals("USER")

    ));
    }

    @Test
    void shouldThrowConflictWhenUsernameAlreadyExists(){
        UserRegistrationDTO userToAdd = new UserRegistrationDTO("test123@example.com","test123Username", "SecretPassword");
        User existingUser = new User();
        existingUser.setUsername("test123Username");
        existingUser.setEmail("testemail@mail.com");

        when(usersRepository.findByUsernameOrEmail(userToAdd.getUsername(), userToAdd.getEmail()))
                .thenReturn(Optional.of(existingUser));
        assertThrows(ConflictException.class, () -> userService.createUserAccount(userToAdd));

        verify(usersRepository,never()).save(any());
    }
    @Test
    void shouldThrowConflictWithEmailAlreadyExists(){
        UserRegistrationDTO userToAdd = new UserRegistrationDTO("test123@example.com",
                "test123Username",
                "SecretPassword");
        User existingUser = new User();
        existingUser.setEmail("test123@example.com");
        existingUser.setUsername("testingUsername");
        when(usersRepository.findByUsernameOrEmail(userToAdd.getUsername(),userToAdd.getEmail()))
                .thenReturn(Optional.of(existingUser));
        assertThrows(ConflictException.class, () -> userService.createUserAccount(userToAdd));
        verify(usersRepository,never()).save(any());
    }

    @Test
    void shouldReturnJETTokenOnUserLogin(){
        UserLoginDTO userLoginDTO = new UserLoginDTO("testUsername","testPassword");
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testusername");
        user.setPasswordHash("hashed_pw");

        when(usersRepository.findByUsername("testusername"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("testPassword", "hashed_pw"))
                .thenReturn(true);
        when(jwtService.generateToken(1L))
                .thenReturn("mocked-jwt-token");

        UserLoginResponseDTO userLoginResponseDTO = userService.login(userLoginDTO);
        assertEquals("mocked-jwt-token", userLoginResponseDTO.getToken());
        verify(jwtService).generateToken(1L);

    }
    @Test
    void shouldThrowErrorUserLoginWrongUsername(){
        UserLoginDTO userLoginDTO = new UserLoginDTO("wrongUsername","testPassword");

        when(usersRepository.findByUsername("wrongUsername"))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.login(userLoginDTO));
        verify(jwtService, never()).generateToken(anyLong());
    }
    @Test
    void shouldThrowErrorUserLoginWrongPassword(){
        UserLoginDTO userLoginDTO = new UserLoginDTO("testUsername", "wrongPassword");
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testUsername");
        user.setPasswordHash("hashed_pw");
        when(usersRepository.findByUsername("testUsername"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword","hashed_pw"))
                .thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> userService.login(userLoginDTO));
        verify(jwtService, never()).generateToken(anyLong());
    }

}
