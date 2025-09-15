package com.jsp.SpringBoot_React.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jsp.SpringBoot_React.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	 // Custom query to eagerly fetch cartItems and associated products
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.cartItems ci LEFT JOIN FETCH ci.product WHERE u.username = :username")
	Optional<User> findByUsernameWithCartItems(@Param("username") String username);


    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    long countByIsAdmin(int isAdmin);
    Optional<User> findByIsAdmin(int isAdmin);
}

