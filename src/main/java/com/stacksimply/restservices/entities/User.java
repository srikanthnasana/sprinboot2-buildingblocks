package com.stacksimply.restservices.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimply.restservices.projections.SimpleUserProjectionDTO;
import com.stacksimply.restservices.validators.UniqueEmail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@NamedNativeQuery(name = "User.findUserDtlsById", 
query = "select u.first_name as firstName,u.last_name as lastName,u.user_id as id from user_info u where user_id=:id",
resultSetMapping = "mapping.SimpleUserProjectionDTO")

@SqlResultSetMapping(name = "mapping.SimpleUserProjectionDTO", 
classes = @ConstructorResult(
		targetClass = SimpleUserProjectionDTO.class, 
		columns = {
		@ColumnResult(name = "firstName",type =String.class), 
		@ColumnResult(name = "lastName",type =String.class), 
		@ColumnResult(name = "id",type = Long.class) }))

@ApiModel(description = "This Model is to create a user")
@Entity
@Table(name = "user_info")
//@JsonFilter(value = "userFilter")--Used for MappingJacksonValue filtering section
//@JsonIgnoreProperties({"firstName","lastName"}) --Static filtering @JsonIgnore
public class User {
	@ApiModelProperty(notes = "Auto generated unique Id", required = true, position = 1, example = "101")
	@Id
	@SequenceGenerator(name = "incseq_gen", allocationSize = 1, sequenceName = "user_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incseq_gen")
	@Column(name = "user_Id", updatable = false)
	@JsonView(Views.External.class)
	private Long id;

	@ApiModelProperty(notes = "username should be in format flname", required = true, position = 2, example = "Srikanth")
	@Size(min = 2, max = 50, message = "{NotEmpty.User.userName}")
	@Pattern(regexp = "[^0-9]+", message = "{Pattern.User.userName}")
	@NotEmpty(message = "{NotEmpty.User.userName}")
	@Column(name = "USER_NAME", length = 50, nullable = false, unique = true)
	@JsonView(Views.External.class)
	private String userName;

	@ApiModelProperty(notes = "firstname should be in format flname", required = false, position = 3, example = "Srikanth")
	@Size(min = 2, max = 50, message = "{size.User.firstName}")
	@Column(name = "FIRST_NAME", length = 50, nullable = false)
	@JsonView(Views.External.class)
	private String firstName;

	@ApiModelProperty(notes = "lastname should be in format flname", required = false, position = 4, example = "Nasana")
	@Column(name = "LAST_NAME", length = 50, nullable = false)
	@JsonView(Views.External.class)
	private String lastName;

	@NotEmpty(message = "{NotEmpty.User.email}")
	@Email(message = "{Email.User.email}")
	@UniqueEmail
	@ApiModelProperty(notes = "email should be in format flname", required = true, position = 5, example = "srikanth.nasana@gmail.com")
	@Column(name = "EMAIL_ADDRESS", length = 50, nullable = false)
	@JsonView(Views.External.class)
	private String email;

	@NotNull(message = "{NotNull.User.role}")
	@ApiModelProperty(notes = "role should be in format flname", required = true, position = 6, example = "Admin")
	@Column(name = "ROLE", length = 50, nullable = false)
	@JsonView(Views.Internal.class)
	private String role;

	@ApiModelProperty(notes = "ssn should be in format flname", required = true, position = 7, example = "ssn101")
	@Column(name = "SSN", length = 50, nullable = false, unique = true)
	// @JsonIgnore--Static filtering @JsonIgnore
	@JsonView(Views.Internal.class)
	private String ssn;

	@OneToMany(mappedBy = "user")
	@JsonView(Views.Internal.class)
	private List<Order> orders;
	@NotBlank(message = "{NotBlank.User.Address}")
	@ApiModelProperty(notes = "address should be in format flname", required = true, position = 8, example = "New York")
	@Column(name = "ADDRESS")
	private String Address;

	public User() {

	}

	public User(Long id, @NotEmpty String userName, @Size String firstName, String lastName, String email, String role,
			String ssn, List<Order> orders, String address) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.ssn = ssn;
		this.orders = orders;
		Address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", role=" + role + ", ssn=" + ssn + ", orders=" + orders + ", Address=" + Address
				+ "]";
	}

}
