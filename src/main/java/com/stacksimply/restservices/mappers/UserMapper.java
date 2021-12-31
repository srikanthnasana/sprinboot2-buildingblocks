package com.stacksimply.restservices.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.stacksimply.restservices.dtos.UserMapStructDto;
import com.stacksimply.restservices.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	// User To UserMapStructDto
	@Mappings({
	@Mapping(source ="email",target ="emailaddress"),
	@Mapping(source ="role",target ="userRole")})
	 UserMapStructDto userMapStructDto(User user);

	// List<User> To List<UserMapStructDto>
	List<UserMapStructDto> usersToUserMsDtos(List<User> users);
}
