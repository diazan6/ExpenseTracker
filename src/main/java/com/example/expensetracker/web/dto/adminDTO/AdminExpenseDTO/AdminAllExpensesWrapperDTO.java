package com.example.expensetracker.web.dto.adminDTO.AdminExpenseDTO;

import java.util.List;

public class AdminAllExpensesWrapperDTO {
    private List<AdminAllExpensesDTO> adminAllExpensesDTO;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;

    public AdminAllExpensesWrapperDTO(List<AdminAllExpensesDTO> adminAllExpensesDTO, int currentPage, int totalPages, long totalItems, int pageSize) {
        this.adminAllExpensesDTO = adminAllExpensesDTO;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
    }

    public List<AdminAllExpensesDTO> getAdminAllExpensesDTO() {
        return adminAllExpensesDTO;
    }

    public void setAdminAllExpensesDTO(List<AdminAllExpensesDTO> adminAllExpensesDTO) {
        this.adminAllExpensesDTO = adminAllExpensesDTO;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
