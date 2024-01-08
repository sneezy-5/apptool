package com.apptool.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apptool.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //  @Query("SELECT u FROM User u WHERE u.username = :username")
    // User findByUsername(String username);
       Optional<User> findByUsername(String username);
       
       Boolean existsByUsername(String username);

       Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.id <> :userId")
    int countByUsernameAndExcludeUserId(@Param("username") String username, @Param("userId") Long userId);

    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email AND u.id <> :userId")
    int countByEmailAndExcludeUserId(@Param("email") String email, @Param("userId") Long userId);

}