package com.stacksimply.restservices.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.stacksimply.restservices.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByEmail(String email);
}
