package com.thejaneshin.petswag.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thejaneshin.petswag.exception.AppException;
import com.thejaneshin.petswag.model.Role;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.ApiResponse;
import com.thejaneshin.petswag.payload.JwtAuthenticationResponse;
import com.thejaneshin.petswag.payload.LoginRequest;
import com.thejaneshin.petswag.payload.SignUpRequest;
import com.thejaneshin.petswag.security.JwtTokenProvider;
import com.thejaneshin.petswag.service.RoleService;
import com.thejaneshin.petswag.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsernameOrEmail(),
						loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if(userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
		
		if(userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        };
        
        User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getEmail(), signUpRequest.getName());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        Role userRole = roleService.findByName("ROLE_USER");
        
        if (userRole == null)
        	throw new AppException("User Role not set.");
        
        user.setRoles(Collections.singleton(userRole));
        
        User theUser = userService.save(user);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(theUser.getUsername()).toUri();
        
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
}
