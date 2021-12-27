package com.stacksimply.restservices.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.service.UserService;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class FilterMappingJacksonController {
	
	@Autowired
	UserService userService;
	
	//Get userById -fields with Hashset
	@GetMapping("/{id}")
	public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long userId) {
		try {
			Optional<User> userOptional= userService.findByUserId(userId);
			User user=userOptional.get();
			//Setting fields to static Hashset
			Set<String> fileds=new HashSet<String>();
			fileds.add("id");
			fileds.add("userName");
			fileds.add("ssn");
			fileds.add("orders");
			
			FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter", 
					SimpleBeanPropertyFilter.filterOutAllExcept(fileds));
			MappingJacksonValue mapper=new MappingJacksonValue(user);
			mapper.setFilters(filterProvider);
			return mapper;
		} catch (UserNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	//Get userById -fields with @RequestParam
		@GetMapping("/params/{id}")
		public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1) Long userId,@RequestParam Set<String> fileds) {
			try {
				Optional<User> userOptional= userService.findByUserId(userId);
				User user=userOptional.get();
				
				FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter", 
						SimpleBeanPropertyFilter.filterOutAllExcept(fileds));
				MappingJacksonValue mapper=new MappingJacksonValue(user);
				mapper.setFilters(filterProvider);
				return mapper;
			} catch (UserNotFound e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}
		}

}
