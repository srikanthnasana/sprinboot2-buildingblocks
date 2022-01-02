package com.stacksimply.restservices.securityconfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.stacksimply.restservices.entities.Customer;

//Custom implementation of UserDetails [By default implementation is User]
//In for to spring this is custom user details implementation
public class CustomerSecurity implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Customer customer;
	
	public CustomerSecurity(Customer customer) {
		this.customer=customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Convert role into authorities and given back to spring
		List<GrantedAuthority> authorities=new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(customer.getRole()));
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		// we are tell to spring we are using email insted of userName.
		return customer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
