package com.perseus.userservice.repositories;

import com.perseus.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
}
