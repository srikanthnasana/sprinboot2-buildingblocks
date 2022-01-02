package com.stacksimply.restservices.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.repositories.UserRespository;
import com.stacksimply.restservices.specifications.UserSpecifications;

@RestController
@RequestMapping("/userspec")
public class UserSpecificationController {

	@Autowired
	UserRespository userRespository;

	@GetMapping("/getuser/{firstname}/{lastname}")
	public List<User> getUserDtls(@PathVariable("firstname") String firstName,
			@PathVariable("lastname") String lastName) {
		Specification<User> specs = Specification.where(UserSpecifications.likeFirstName(firstName))
				.and(UserSpecifications.equalLastName(lastName));
		List<User> users = userRespository.findAll(specs);
		return users;

	}

	@GetMapping("/getorder/{order_desc}")
	public List<User> getOrderDesc(@PathVariable("order_desc") Long orderId) {
		Specification<User> specs = Specification.where(UserSpecifications.notForm(orderId));
		List<User> users = userRespository.findAll(specs);
		return users;

	}
}
