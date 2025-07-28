package com.example.expensetracker.data.repository;

import com.example.expensetracker.data.entity.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
 Optional<User> findByUsername(String username);

Optional<User> findByUsernameOrEmail(String username, String email);

}
