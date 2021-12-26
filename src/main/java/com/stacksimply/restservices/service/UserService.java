package com.stacksimply.restservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.repositories.UserRespository;

@Service
public class UserService {

	@Autowired
	private UserRespository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findByUserId(Long userId) {
		return userRepository.findById(userId);
	}

	public User updateUserById(Long id, User user) {
		user.setId(id);
		return userRepository.save(user);
	}

	public void deleteUserById(Long id) {
		if (userRepository.findById(id).isPresent())
			userRepository.deleteById(id);

	}

	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
		
	}
}
