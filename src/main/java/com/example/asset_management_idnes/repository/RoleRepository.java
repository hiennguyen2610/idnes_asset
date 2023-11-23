package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Role;
import com.example.asset_management_idnes.statics.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Roles name);

}