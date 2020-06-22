package com.zsc.web.repository;

import com.zsc.web.domain.Storage;
import com.zsc.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Abouerp
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
