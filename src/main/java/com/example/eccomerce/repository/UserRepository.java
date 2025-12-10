package com.example.eccomerce.repository;

import com.example.eccomerce.model.User;
import com.example.eccomerce.model.Product;
import com.example.eccomerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

