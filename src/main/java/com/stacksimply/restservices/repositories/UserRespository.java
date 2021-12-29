package com.stacksimply.restservices.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stacksimply.restservices.entities.User;
//repository
//git change

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
	User findByUserName(String username);

	@Query("select distinct u from User u left join fetch u.orders")
	List<User> findAllUsers();

	Optional<User> findByEmail(String email);
}
