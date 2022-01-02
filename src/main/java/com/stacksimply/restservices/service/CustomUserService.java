package com.stacksimply.restservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stacksimply.restservices.entities.Customer;
import com.stacksimply.restservices.exceptions.UserNameNotFoundExp;
import com.stacksimply.restservices.repositories.CustomerRepository;
import com.stacksimply.restservices.securityconfig.CustomerSecurity;

/**
 * Implementation CustomUserService security service to load user details from database.
 * If you want perform user creation such type of services to implement UserDetailsManagement
 * @author LENOVO
 *
 */
@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Customer> customer = customerRepository.findByEmail(username);
		if (customer.size() == 0) {
			throw new UserNameNotFoundExp("User Details Not Found for the user :" + username);
		}
		return new CustomerSecurity(customer.get(0));
	}

}
