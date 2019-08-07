package com.thejaneshin.petswag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thejaneshin.petswag.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);
}
