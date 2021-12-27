package com.stacksimply.restservices.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimply.restservices.entities.Order;
import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.repositories.OrderRepository;
import com.stacksimply.restservices.repositories.UserRespository;

@RestController
@RequestMapping("/users")
@Validated
public class OrderController {

	@Autowired
	private UserRespository userRespository;
	@Autowired
	private OrderRepository orderRepository;

	@GetMapping("/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable("userid") @Min(1) Long userId) throws UserNotFound {

		Optional<User> userOptional = userRespository.findById(userId);
		if (!userOptional.isPresent())
			throw new UserNotFound("User Not Found");
		return userOptional.get().getOrders();

	}

	@PostMapping("/{userid}/orders")
	public Order createOrder(@PathVariable("userid") @Min(1) Long userId, @RequestBody Order order)
			throws UserNotFound {

		Optional<User> userOptional = userRespository.findById(userId);
		if (!userOptional.isPresent())
			throw new UserNotFound("User Not Found");
		order.setUser(userOptional.get());
		return orderRepository.save(order);

	}

	@GetMapping("/{userid}/orders/{orderid}")
	public Optional<Order> getOrderByOrderId(@PathVariable("userid") @Min(1) Long userId,
			@PathVariable("orderid") @Min(1) Long orderId) throws UserNotFound {
		Optional<User> userOptional = userRespository.findById(userId);
		if (!userOptional.isPresent())
			throw new UserNotFound("User Not Found");
		return Optional.ofNullable(orderRepository.findById(orderId).orElseThrow(()->new UserNotFound("Order Not Exits!..")));
	}

}
