package com.pla.control.repositories;

import com.pla.control.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<User, Integer> {
    User findByUsername(@Param("username") String username);
}
