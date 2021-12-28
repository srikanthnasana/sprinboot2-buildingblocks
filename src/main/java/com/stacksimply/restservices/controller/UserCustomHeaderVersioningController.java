package com.stacksimply.restservices.controller;

import java.util.Optional;
import javax.validation.constraints.Min;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.stacksimply.restservices.dtos.UserDtoV1;
import com.stacksimply.restservices.dtos.UserDtoV2;
import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.service.UserService;

@RestController
@RequestMapping("/versioning/header/users")
@Validated
public class UserCustomHeaderVersioningController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	
	// Custom Header based Versioning -v1
	@GetMapping(value="/{id}",headers = {"API-VERSION=1.0","AUTH=2.0"})
	public UserDtoV1 getUserByIdV1(@PathVariable("id") @Min(1) Long userId) {
		try {
			Optional<User> userOptional = userService.findByUserId(userId);
			User user = userOptional.get();
			UserDtoV1 userDtoV1 = modelMapper.map(user, UserDtoV1.class);
			return userDtoV1;
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	// Custom Header based Versioning -v2
	@GetMapping(value="/{id}",headers = "API-VERSION=2.0")
	public UserDtoV2 getUserByIdV2(@PathVariable("id") @Min(1) Long userId) {
		try {
			Optional<User> userOptional = userService.findByUserId(userId);
			User user = userOptional.get();
			UserDtoV2 userDtoV2 = modelMapper.map(user, UserDtoV2.class);
			return userDtoV2;
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
