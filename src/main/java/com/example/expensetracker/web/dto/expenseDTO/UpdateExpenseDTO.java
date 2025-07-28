package com.example.expensetracker.web.dto.expenseDTO;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateExpenseDTO {
    @Nullable
    private String description;
    @Nullable
    private BigDecimal amount;
    @Nullable
    private String category;
    @Nullable
    private LocalDate date;


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
}
