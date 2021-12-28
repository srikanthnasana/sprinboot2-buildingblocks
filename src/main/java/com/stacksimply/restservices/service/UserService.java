package com.stacksimply.restservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.exceptions.UserExistsException;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.repositories.UserRespository;

@Service
public class UserService {

	@Autowired
	private UserRespository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void save(User user) throws UserExistsException {
		
		userRepository.save(user);
	}

	public Optional<User> findByUserId(Long userId) throws UserNotFound {
		Optional<User> user= userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserNotFound("User Not Found in User Repository");
		}
		return user;
	}

	public User updateUserById(Long id, User user) throws UserNotFound {
		Optional<User> optionalUser= userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new UserNotFound("User Not Found in User Repository,Provide correct UserId");
		}
		user.setId(id);
		return userRepository.save(user);
	}

	public void deleteUserById(Long id) throws UserNotFound {
		Optional<User> optionalUser= userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new UserNotFound("User Not Found in User Repository,Provide correct UserId");
		}
			userRepository.deleteById(id);

	}

	public User findByUserName(String userName) {
		
		return userRepository.findByUserName(userName);
		
	}
}
