package com.stacksimply.restservices.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.exceptions.UserExistsException;
import com.stacksimply.restservices.exceptions.UserNameNotFoundException;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api(tags = "User Management RESTful Services",value = "UserContoller",description = "Contoller For User Management Service")
@RestController
@Validated
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	@Value("${context.name:dev}")
	String contextName;
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);

	@ApiOperation(value = "Retrive List Of Users")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<User> getAllUser() {
		logger.info("Context Running :"+contextName);
		return userService.getAllUsers();
	}

	@ApiOperation(value = "Create New User")
	@PostMapping("/createuser")
	public ResponseEntity<Void> createUser(@ApiParam(value = "user information for a new user to be created.") @Valid @RequestBody User user, UriComponentsBuilder builder) {
		try {
			logger.info("User :{}"+user);
			userService.save(user);
			HttpHeaders header = new HttpHeaders();
			header.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(header, HttpStatus.CREATED);
		} catch (UserExistsException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());                                                                                                                                                                                                                                                                                                                                                                                                                                     
		}
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") @Min(1) Long userId) {
		try {
			return userService.findByUserId(userId).get();
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {

		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		try {
			userService.deleteUserById(id);
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/byusername/{userName}")
	public User findByUserName(@PathVariable("userName") String userName) throws UserNameNotFoundException {
		User user = userService.findByUserName(userName);
		if (user == null)
			throw new UserNameNotFoundException("Username " + userName + " Not Found in User Repository");
		return user;
	}

}
