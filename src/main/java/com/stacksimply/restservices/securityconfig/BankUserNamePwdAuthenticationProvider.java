package com.stacksimply.restservices.securityconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.stacksimply.restservices.entities.Authority;
import com.stacksimply.restservices.entities.Customer;
import com.stacksimply.restservices.repositories.CustomerRepository;
@Component
public class BankUserNamePwdAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String passWord = authentication.getCredentials().toString();
		List<Customer> customers = customerRepository.findByEmail(userName);
		if (customers.size() > 0) {
			if (passwordEncoder.matches(passWord, customers.get(0).getPassword())) {
				return new UsernamePasswordAuthenticationToken(userName, passWord,getGrantedAuthorities(customers.get(0).getAuthorities()));

			} else {
				throw new BadCredentialsException("Invalid Password!");
			}
		} else {
			throw new BadCredentialsException("No user registered with this details!");
		}

	}

	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
		List<GrantedAuthority> grantedAuthorities=authorities.stream().map(auth->new SimpleGrantedAuthority(auth.getName())).collect(Collectors.toList());
		return grantedAuthorities;
	}
	
	@Override
	public boolean supports(Class<?> authenticationType) {

		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}

}
