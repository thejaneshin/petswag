package com.thejaneshin.petswag.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Role;
import com.thejaneshin.petswag.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	public RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository theRoleRepository) {
		roleRepository = theRoleRepository;
	}

	@Override
	public Optional<Role> findByName(String name) {
		return roleRepository.findByName(name);
	}
	
	
}
