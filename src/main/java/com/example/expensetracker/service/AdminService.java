package com.example.expensetracker.service;

import com.example.expensetracker.config.JwtService;
import com.example.expensetracker.data.entity.Expense;
import com.example.expensetracker.data.entity.User;
import com.example.expensetracker.data.repository.ExpensesRepository;
import com.example.expensetracker.data.repository.UsersRepository;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.web.dto.adminDTO.AdminExpenseDTO.AdminAllExpensesDTO;
import com.example.expensetracker.web.dto.adminDTO.AdminExpenseDTO.AdminAllExpensesWrapperDTO;
import com.example.expensetracker.web.dto.adminDTO.AdminUserDTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {
    private static final String role = "ADMIN";
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExpensesRepository expensesRepository;
    private final JwtService jwtService;

    public AdminService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, ExpensesRepository expensesRepository, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.expensesRepository = expensesRepository;
        this.jwtService = jwtService;
    }

    public void createAdminAccount(RegisterAdminDTO registerAdminDTO){
        User user = new User();

        user.setRole(role);
        user.setUsername(registerAdminDTO.getUsername());
        user.setEmail(registerAdminDTO.getEmail());
        String hashedPassword = passwordEncoder.encode(registerAdminDTO.getRawPassword());
        user.setPasswordHash(hashedPassword);

        usersRepository.save(user);
    }

    public AdminLoginResponseDTO loginAdminAccount(AdminLoginDTO adminLoginDTO){
        User user = usersRepository.findByUsername(adminLoginDTO.getUsername())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(adminLoginDTO.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUserId());
        return new AdminLoginResponseDTO(token);
    }

    public AdminAllExpensesWrapperDTO getAllExpenses(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        Page<Expense> adminAllExpensesDTOPage;
        List<AdminAllExpensesDTO> allExpensesDTOS;

        adminAllExpensesDTOPage = expensesRepository.findAll(pageable);
        allExpensesDTOS = adminAllExpensesDTOPage.stream()
                .map(expenses -> new AdminAllExpensesDTO(
                        expenses.getExpenseId(),
                        expenses.getDescription(),
                        expenses.getAmount(),
                        expenses.getCategory(),
                        expenses.getDate(),
                        new AdminUserSummaryDTO(
                            expenses.getUser().getUserId(),
                                expenses.getUser().getEmail(),
                                expenses.getUser().getUsername()
                        )
                )).toList();

        return new AdminAllExpensesWrapperDTO(allExpensesDTOS, adminAllExpensesDTOPage.getNumber(),adminAllExpensesDTOPage.getTotalPages(),
                adminAllExpensesDTOPage.getTotalElements(),adminAllExpensesDTOPage.getSize()
        );
    }

    public AdminAllExpensesDTO getExpenseById(Long id){

        Expense expense = checkIfExpenseExists(id);
        return new AdminAllExpensesDTO(expense.getExpenseId(),expense.getDescription(),expense.getAmount(),
                expense.getCategory(),expense.getDate(),
                new AdminUserSummaryDTO(expense.getUser().getUserId(), expense.getUser().getEmail(),expense.getUser().getUsername()));
    }
    public void deleteExpenseById(Long id){
        checkIfExpenseExists(id);
        expensesRepository.deleteById(id);
    }

    public AdminAllUsersWrapperDTO getAllUsers(int size, int page){
        PageRequest pageable = PageRequest.of(size,page);
        Page<User> usersPage;
        List<AdminUserSummaryDTO> userSummary;

        usersPage = usersRepository.findAll(pageable);
        userSummary = usersPage.stream()
                .map(user -> new AdminUserSummaryDTO(
                        user.getUserId(),
                        user.getEmail(),
                        user.getUsername()

                )).toList();
        return new AdminAllUsersWrapperDTO(userSummary, usersPage.getNumber(),usersPage.getTotalPages(),usersPage.getTotalElements()
        ,usersPage.getSize());
    }

    public AdminUserDTO getSingleUser(Long id){
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Doesn't exist"));
        return new AdminUserDTO(user.getEmail(),user.getUsername());

    }

    public void deleteUserById(Long id){
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));
        usersRepository.deleteById(id);
    }



    //Helper method to see if the expense exists without having to do calls in other methods DRY
    public Expense checkIfExpenseExists(Long id){
        return expensesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense Not Found"));
    }
}

