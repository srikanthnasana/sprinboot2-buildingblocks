package com.stacksimply.restservices.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public List<User> getAllUser() {
		return userService.getAllUsers();
	}

	@PostMapping("/create-user")
	public User createUser(@RequestBody User user, UriComponentsBuilder builder) {
		HttpHeaders header=new HttpHeaders();
		header.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
		return userService.save(user);
	}

	@GetMapping("/users/{id}")
	public Optional<User> getUserId(@PathVariable("id") Long userId) {
		try {
			return userService.findByUserId(userId);
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user																																																																																																																																		) {

		
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		try {
			userService.deleteUserById(id);
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/users/byUserName/{userName}")
	public User findByUserName(@PathVariable("userName") String userName) {
		return userService.findByUserName(userName);
	}

}
