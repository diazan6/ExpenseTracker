package com.example.expensetracker.web.dto.adminDTO.AdminExpenseDTO;


import com.example.expensetracker.web.dto.adminDTO.AdminUserDTO.AdminUserSummaryDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AdminAllExpensesDTO {
    private Long expenseId;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private AdminUserSummaryDTO user;

    public AdminAllExpensesDTO(Long expenseId, String description, BigDecimal amount, String category, LocalDate date, AdminUserSummaryDTO user) {
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.user = user;

    }

    public AdminAllExpensesDTO(Long expenseId, String description, BigDecimal amount, String category, LocalDate date) {
        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AdminUserSummaryDTO getUser() {
        return user;
    }

    public void setUser(AdminUserSummaryDTO user) {
        this.user = user;
    }


}
