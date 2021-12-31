package com.stacksimply.restservices.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimply.restservices.controller.UserController;
import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.exceptions.UserExistsException;
import com.stacksimply.restservices.exceptions.UserNotFound;
import com.stacksimply.restservices.projections.SimpleUserProjectionDTO;
import com.stacksimply.restservices.projections.SimpleUsers;
import com.stacksimply.restservices.projections.UserProjectionDTO;
import com.stacksimply.restservices.repositories.UserRespository;

@Service
public class UserService {

	@Autowired
	private UserRespository userRepository;

	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	public List<User> getAllUsers() {
		return userRepository.findAllUsers();
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

	public SimpleUsers findsimpleyById(@Positive Long userId) {
		logger.info("findByUser Start Calling from Userservice findsimpleyById {}"+userId);
		return userRepository.findSimpleyById(userId);
	}

	public <T> T findsimpleyByIdAndUsername(long id, String userName,Class<T> type) {
		return userRepository.findByIdAndUserName(id, userName,type);
		
	}

	public User findDistinctFirstNameAndLastName(String firstName, String lastName) {
		return userRepository.findDistinctByLastNameAndFirstName(lastName, firstName);
	}

	public <T> List<T> getSSNDtls(Class<T> type) {
		return userRepository.getVVNDtls(type);
	}
}
