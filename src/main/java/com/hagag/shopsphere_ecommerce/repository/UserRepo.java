package com.hagag.shopsphere_ecommerce.repository;

import com.hagag.shopsphere_ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String usernameOrEmail);

    Optional<User> findByEmail(String usernameOrEmail);

    Optional<User> findByUsernameOrEmail(String username, String email);

}
