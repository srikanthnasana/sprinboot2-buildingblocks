package com.stacksimply.restservices.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.entities.Views;
import com.stacksimply.restservices.exceptions.UserExistsException;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.service.UserService;

@RestController
@RequestMapping(value = "/jsonview/users")
public class JsonViewController {
	@Autowired
	UserService userService;
	
	@JsonView(Views.External.class)
	@GetMapping("/external/{id}")
	public Optional<User> getUserByIdExternal(@PathVariable("id") @Min(1) Long userId) {
		try {
			return userService.findByUserId(userId);
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@JsonView(Views.Internal.class)
	@GetMapping("/internal/{id}")
	public Optional<User> getUserByIdInternal(@PathVariable("id") @Min(1) Long userId) {
		try {
			return userService.findByUserId(userId);
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	
}
