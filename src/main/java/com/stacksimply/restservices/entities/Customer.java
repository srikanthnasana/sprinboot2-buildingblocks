package com.stacksimply.restservices.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer {

	@Id
	@SequenceGenerator(name = "incseqorder_gen", allocationSize = 1, sequenceName = "order_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incseqorder_gen")
	private Long id;
	private String email;
	private String password;
	private String role;
	@JsonIgnore
	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
	private Set<Authority> authorities;
	
	public Customer() {
		
	}

	public Customer(Long id, String email, String password, String role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	

}
