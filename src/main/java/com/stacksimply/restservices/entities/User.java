package com.stacksimply.restservices.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "user_info")
//@JsonFilter(value = "userFilter")--Used for MappingJacksonValue filtering section
//@JsonIgnoreProperties({"firstName","lastName"}) --Static filtering @JsonIgnore
public class User {

	@Id
	@SequenceGenerator(name = "incseq_gen", allocationSize = 1, sequenceName = "user_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incseq_gen")
	@Column(name = "user_Id", updatable = false)
	@JsonView(Views.External.class)
	private Long id;

	@NotEmpty(message="Username is Mandatory Field. Please provide username")
	@Column(name = "USER_NAME", length = 50, nullable = false, unique = true)
	@JsonView(Views.External.class)
	private String userName;
	
	@Size(min = 2,message = "FirstName should have atleast 2 characters")
	@Column(name = "FIRST_NAME", length = 50, nullable = false)
	@JsonView(Views.External.class)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 50, nullable = false)
	@JsonView(Views.External.class)
	private String lastName;
	
	@Column(name = "EMAIL_ADDRESS", length = 50, nullable = false)
	@JsonView(Views.External.class)
	private String email;
	
	@Column(name = "ROLE", length = 50, nullable = false)
	@JsonView(Views.Internal.class)
	private String role;
	
	@Column(name = "SSN", length = 50, nullable = false, unique = true)
	//@JsonIgnore--Static filtering @JsonIgnore
	@JsonView(Views.Internal.class)
	private String ssn;
	
	@OneToMany(mappedBy = "user")
	@JsonView(Views.Internal.class)
	private List<Order> orders;
	
	public User() {
		
	}

	public User(Long id, String userName, String firstName, String lastName, String email, String role, String ssn) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.ssn = ssn;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", role=" + role + ", ssn=" + ssn + "]";
	}

	
}
