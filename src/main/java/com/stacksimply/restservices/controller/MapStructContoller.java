package com.stacksimply.restservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimply.restservices.dtos.UserMapStructDto;
import com.stacksimply.restservices.mappers.UserMapper;
import com.stacksimply.restservices.repositories.UserRespository;

@RestController
@RequestMapping("/mapstruct/users")
public class MapStructContoller {

	@Autowired
	private UserRespository userRespository;
	
	@Autowired
	private UserMapper userMapper;
	

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserMapStructDto> getAllUserDtos(){	
		return userMapper.usersToUserMsDtos(userRespository.findAll());
		
	}
	
	@GetMapping("/{id}")
	@PostAuthorize("hasRole('USER')")
	public UserMapStructDto getUserDtos(@PathVariable Long id){
		return userMapper.userMapStructDto(userRespository.findById(id).get());
		
	}
}
