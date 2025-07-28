package com.example.expensetracker.data.repository;

import com.example.expensetracker.data.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByCategoryIn(List<String> category);
    Page<Expense> findAllByUser_UserId(Long userId, Pageable pageable);
    Page<Expense> findAllByUser_UserIdAndCategoryIn(Long userId, List<String> categories, Pageable pageable);

    Page<Expense> findAllByUser_UserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Expense> findAllByUser_UserIdAndDateAfter(Long userId, LocalDate startDate, Pageable pageable);
    Page<Expense> findAllByUser_UserIdAndDateBefore(Long userId, LocalDate endDate, Pageable pageable);

    Page<Expense> findAllByUser_UserIdAndDateBetweenAndCategoryIn(Long userId, LocalDate startDate, LocalDate endDate, List<String> categories, Pageable pageable);
    Page<Expense> findAllByUser_UserIdAndDateAfterAndCategoryIn(Long userId, LocalDate startDate,  List<String> categories, Pageable pageable);
    Page<Expense> findAllByUser_UserIdAndDateBeforeAndCategoryIn(Long userId, LocalDate endDate,  List<String> categories, Pageable pageable);

    
}
