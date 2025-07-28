package com.example.expensetracker.web.api;

import com.example.expensetracker.data.entity.Expense;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.service.ExpenseService;
import com.example.expensetracker.web.dto.expenseDTO.DTOWrapperForAmount;
import com.example.expensetracker.web.dto.expenseDTO.ExpenseDTO;
import com.example.expensetracker.web.dto.expenseDTO.UpdateExpenseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseAPIController {

    private final ExpenseService expenseService;
    public ExpenseAPIController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/addExpense")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addExpense(@RequestBody ExpenseDTO expenseDTO){
    expenseService.addExpense(expenseDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body("Expense Created Successfully");
    }

    @GetMapping("/getExpenses")
    public DTOWrapperForAmount getExpenses(@RequestParam(required = false) List<String> categories,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(required = false) LocalDate startDate,
                                           @RequestParam(required = false) LocalDate endDate)
    {
       return expenseService.getAllDTOWithAmount(categories, page, size, startDate,endDate);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseById(@PathVariable ("id") long id){
        Optional<Expense> expense = expenseService.getExpenseById(id);
        if(expense.isEmpty()){
            throw new NotFoundException("This expense with this ID was not found");
        }
        return expense.get();

    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> updateExpense(@RequestBody UpdateExpenseDTO updateExpenseDTO, @PathVariable("id") long id){
         expenseService.updateExpense(updateExpenseDTO, id);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body("Expense Updated Successfully");
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable("id") long id){
        expenseService.deleteExpenseById(id);
    }

}
