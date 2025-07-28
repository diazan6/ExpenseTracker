package com.example.expensetracker.service;

import com.example.expensetracker.data.entity.Expense;
import com.example.expensetracker.data.entity.User;
import com.example.expensetracker.data.repository.ExpensesRepository;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.web.dto.expenseDTO.DTOWrapperForAmount;
import com.example.expensetracker.web.dto.expenseDTO.ExpenseDTO;
import com.example.expensetracker.web.dto.expenseDTO.UpdateExpenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpensesRepository expensesRepository;

    public ExpenseService(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }

    public void addExpense(ExpenseDTO expenseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Expense expense = new Expense();
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDate(expenseDTO.getDate());
        expense.setUser(user);

        expensesRepository.save(expense);
    }



    public Optional<Expense> getExpenseById(long id) {
        return expensesRepository.findById(id);
    }

    public void updateExpense(UpdateExpenseDTO updateExpenseDTO, long id) {
        Optional<Expense> expenseCheck = getExpenseById(id);
        if (expenseCheck.isEmpty()) {
            throw new NotFoundException("No expense found with given id");
        }
        //At this point it means we found an expense with that ID. Now check if ID's match
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(!Objects.equals(expenseCheck.get().getUser().getUserId(), user.getUserId())){
            throw new AccessDeniedException("You don't own this");
        }

        Expense expense = expenseCheck.get();
        if (updateExpenseDTO.getDescription() != null) {
            expense.setDescription(updateExpenseDTO.getDescription());
        }
        if (updateExpenseDTO.getAmount() != null) {
            expense.setAmount(updateExpenseDTO.getAmount());
        }
        if (updateExpenseDTO.getCategory() != null) {
            expense.setCategory(updateExpenseDTO.getCategory());
        }
        if (updateExpenseDTO.getDate() != null) {
            expense.setDate(updateExpenseDTO.getDate());
        }
        expensesRepository.save(expense);
    }

    public void deleteExpenseById(long id) {
        Optional<Expense> checkExpense = getExpenseById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (checkExpense.isEmpty()) {
            throw new NotFoundException("Expense with given ID is not found");
        }

        if(!user.getUserId().equals(checkExpense.get().getUser().getUserId())){
            throw new AccessDeniedException("You don't own this");
        }

        expensesRepository.deleteById(id);
    }


    public DTOWrapperForAmount getAllDTOWithAmount(List<String> categories, int page, int size, LocalDate startDate,
                                                   LocalDate endDate){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        PageRequest pageable = PageRequest.of(page,size);
        User user = (User) authentication.getPrincipal();
        BigDecimal totalAmount;
        List<ExpenseDTO> expenseDTO;
        Page<Expense> expensePage;


        if( categories == null || categories.isEmpty() ){
            if(startDate!= null && endDate!= null){
                expensePage = expensesRepository.findAllByUser_UserIdAndDateBetween(user.getUserId(), startDate,endDate, pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            }
            else if(startDate != null){
                expensePage = expensesRepository.findAllByUser_UserIdAndDateAfter(user.getUserId(), startDate, pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            }
            else if(endDate != null){
                expensePage = expensesRepository.findAllByUser_UserIdAndDateBefore(user.getUserId(), endDate, pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            }
            else{
                expensePage = expensesRepository.findAllByUser_UserId(user.getUserId(), pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            }

        }else {
            if (startDate != null && endDate != null) {
                expensePage = expensesRepository.findAllByUser_UserIdAndDateBetweenAndCategoryIn(user.getUserId(),startDate,endDate, categories, pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            } else if (startDate != null) {
                expensePage = expensesRepository.findAllByUser_UserIdAndDateAfterAndCategoryIn(user.getUserId(), startDate, categories, pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            } else if (endDate != null) {
                expensePage = expensesRepository.findAllByUser_UserIdAndDateBeforeAndCategoryIn(user.getUserId(), endDate, categories, pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            } else {
                expensePage = expensesRepository.findAllByUser_UserIdAndCategoryIn(user.getUserId(), categories, pageable);
                expenseDTO = expensePage.stream()
                        .map(expenses -> new ExpenseDTO(
                                expenses.getDescription(),
                                expenses.getAmount(),
                                expenses.getCategory(),
                                expenses.getDate()
                        ))
                        .toList();
            }
        }
        totalAmount = expenseDTO.stream()
                .map(ExpenseDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new DTOWrapperForAmount(expenseDTO,totalAmount,expensePage.getNumber(),expensePage.getTotalPages(),
                expensePage.getTotalElements(),expensePage.getSize());

    }
}
