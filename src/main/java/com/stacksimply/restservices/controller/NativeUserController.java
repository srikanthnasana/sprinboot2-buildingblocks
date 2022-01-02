package com.stacksimply.restservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimply.restservices.projections.SimpleUserProjectionDTO;
import com.stacksimply.restservices.projections.UserNativeProjectionDTO;
import com.stacksimply.restservices.repositories.UserRespository;

@RestController
@RequestMapping(path = "/native/users")
public class NativeUserController {

	@Autowired
	private UserRespository userRespository;
	
	@GetMapping("/surname/{lastname}")
	public UserNativeProjectionDTO getUserByLastName(@PathVariable("lastname") String lastName) {
		return userRespository.findUserByLastName(lastName, UserNativeProjectionDTO.class);
		
	} 
	@GetMapping("/userid/{id}")
	public SimpleUserProjectionDTO getUserById(@PathVariable("id") Long id) {
		return userRespository.findUserDtlsById(id);
	}
}
