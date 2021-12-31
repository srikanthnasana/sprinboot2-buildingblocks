package com.stacksimply.restservices.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stacksimply.restservices.entities.User;
import com.stacksimply.restservices.projections.SimpleUsers;
//repository
//git change

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
	User findByUserName(String username);

	@Query("select distinct u from User u left join fetch u.orders")
	List<User> findAllUsers();

	Optional<User> findByEmail(String email);
	
	SimpleUsers findSimpleyById(@Positive Long userId);
	
	@Transactional
	//@Query("select new com.stacksimply.restservices.projections.SimpleUserProjectionDTO(a.firstName,a.lastName,a.id) from User a ")
	<T> T findByIdAndUserName(Long id,String userName,Class<T> type);//DTO Projections
	
	User findDistinctByLastNameAndFirstName(String lastName,String firstName);
	
	@Query("select u from User u left join fetch u.orders o")
	<T> List<T> getVVNDtls(Class<T> type);
	
	@Query(value="select u.first_name as firstName,u.last_name as lastName,u.user_id as id from user_info u where last_name=:lastName",nativeQuery = true)
	<T> T findUserByLastName(String lastName,Class<T> type);
		
	
	
}
