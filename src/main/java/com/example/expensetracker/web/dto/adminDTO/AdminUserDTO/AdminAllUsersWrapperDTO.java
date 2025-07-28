package com.example.expensetracker.web.dto.adminDTO.AdminUserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminAllUsersWrapperDTO {
    private List<AdminUserSummaryDTO> userSummaryDTO;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;

    public List<AdminUserSummaryDTO> getUserSummaryDTO() {
        return userSummaryDTO;
    }

    public void setUserSummaryDTO(List<AdminUserSummaryDTO> userSummaryDTO) {
        this.userSummaryDTO = userSummaryDTO;
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


    public AdminAllUsersWrapperDTO(List<AdminUserSummaryDTO> userSummaryDTO, int currentPage, int totalPages, long totalItems, int pageSize) {
        this.userSummaryDTO = userSummaryDTO;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
    }
}
