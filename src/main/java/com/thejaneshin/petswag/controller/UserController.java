package com.thejaneshin.petswag.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thejaneshin.petswag.model.User;

@RestController
public class UserController {
	
	@GetMapping("/")
	public String root() {
		return "Spring Boot REST API for PetSwag.";
	}
	
	@PostMapping("/signup")
	public User signup(@RequestBody User theUser) {
		return theUser;
	}
	
}
