package com.thejaneshin.petswag.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    UserService userService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		
		if (user == null)
			throw new UsernameNotFoundException("User not found with username : " + usernameOrEmail);
		
		return UserPrincipal.create(user);
	}
	
	@Transactional
    public UserDetails loadUserById(int id) {
        User user = userService.findById(id);
        
        if (user == null)
			throw new UsernameNotFoundException("User not found with id : " + id);

        return UserPrincipal.create(user);
    }

}
