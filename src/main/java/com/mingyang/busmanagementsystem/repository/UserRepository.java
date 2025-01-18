package com.mingyang.busmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import com.mingyang.busmanagementsystem.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByIdWithPessimisticLock(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE " +
           "(:username IS NULL OR u.username LIKE CONCAT('%', :username, '%')) AND " +
           "(:role IS NULL OR u.role = :role) AND " +
           "(:email IS NULL OR u.email LIKE CONCAT('%', :email, '%'))")
    List<User> findByFilters(
            @Param("username") String username,
            @Param("role") String role,
            @Param("email") String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
