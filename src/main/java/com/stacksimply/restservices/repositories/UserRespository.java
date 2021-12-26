package com.stacksimply.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stacksimply.restservices.entities.User;
//repository
//git change
@Repository
public interface UserRespository extends JpaRepository<User, Long>{
  User findByUserName(String username);
}
