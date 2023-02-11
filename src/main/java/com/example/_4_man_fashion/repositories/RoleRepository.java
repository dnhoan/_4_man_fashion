package com.example._4_man_fashion.repositories;

import com.example._4_man_fashion.entities.Role;
import com.example._4_man_fashion.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole roleName);

}
