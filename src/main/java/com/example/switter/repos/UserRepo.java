package com.example.switter.repos;

import com.example.switter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Search users
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username); // метод возвращает пользователя

    User findByActivationCode(String code);
}
