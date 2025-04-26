package com.app.finflow.repository;

import com.app.finflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("Select u from User u")
    List<User> findAllUser();

    Optional<User> findByEmail(String email);

    @Query("Select u from User u where u.email = :emailId")
    User getUserByEmail(@Param("emailId") String email);

    Optional<User> findByVerificationToken(String token);
}
