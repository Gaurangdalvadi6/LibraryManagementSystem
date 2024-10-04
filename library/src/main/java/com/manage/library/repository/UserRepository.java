package com.manage.library.repository;

import com.manage.library.entity.Role;
import com.manage.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    Optional<User> findByUserRole(Role role);

    Optional<User> findByRole(Role role);

    Optional<User> findFirstByEmail(String username);
}
