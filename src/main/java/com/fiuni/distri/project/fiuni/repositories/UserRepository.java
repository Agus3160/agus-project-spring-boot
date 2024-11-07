package com.fiuni.distri.project.fiuni.repositories;

import com.fiuni.distri.project.fiuni.domain.Role;
import com.fiuni.distri.project.fiuni.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByRolesContaining(Role role);

}
