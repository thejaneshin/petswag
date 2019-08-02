package com.thejaneshin.petswag.service;

import java.util.Optional;

import com.thejaneshin.petswag.model.Role;

public interface RoleService {
	public Optional<Role> findByName(String name);
}
