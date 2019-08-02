package com.thejaneshin.petswag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thejaneshin.petswag.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Optional<Role> findByName(String name);
}
