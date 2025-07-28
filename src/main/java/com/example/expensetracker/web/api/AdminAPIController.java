package com.example.expensetracker.web.api;

import com.example.expensetracker.service.AdminService;
import com.example.expensetracker.web.dto.adminDTO.AdminExpenseDTO.AdminAllExpensesDTO;
import com.example.expensetracker.web.dto.adminDTO.AdminExpenseDTO.AdminAllExpensesWrapperDTO;
import com.example.expensetracker.web.dto.adminDTO.AdminUserDTO.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminAPIController {
    private final AdminService adminService;

    public AdminAPIController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdminUsers(@RequestBody RegisterAdminDTO registerAdminDTO){
        adminService.createAdminAccount(registerAdminDTO);

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AdminLoginResponseDTO adminLogin(@RequestBody AdminLoginDTO loginAdminDTO){
    return adminService.loginAdminAccount(loginAdminDTO);
    }

    @GetMapping("/allExpenses")
    @ResponseStatus(HttpStatus.OK)
    public AdminAllExpensesWrapperDTO getAllExpenses(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        return adminService.getAllExpenses(page, size);
    }
    @GetMapping("/expense/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdminAllExpensesDTO getExpenseById(@PathVariable ("id") Long id){
        return adminService.getExpenseById(id);
    }

    @DeleteMapping("/expense/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpenseById(@PathVariable ("id") Long id){
        adminService.deleteExpenseById(id);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public AdminAllUsersWrapperDTO getAllUsers(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        return adminService.getAllUsers(page,size);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdminUserDTO getSingleUser(@PathVariable ("id") Long id){
        return adminService.getSingleUser(id);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable ("id") Long id){
        adminService.deleteUserById(id);
    }

}
