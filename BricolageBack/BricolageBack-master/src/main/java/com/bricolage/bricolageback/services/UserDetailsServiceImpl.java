package com.bricolage.bricolageback.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AccountService accountService;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	com.bricolage.bricolageback.entities.User u = accountService.findUserByUsername(username);
	if(u==null) throw new UsernameNotFoundException(username);
	Collection<GrantedAuthority> authorities=new ArrayList<>();
	u.getRoles().forEach(r->{
		authorities.add(new SimpleGrantedAuthority(r.getRole().getRole()));
	});
return new User(u.getUsername(), u.getPassword(), authorities);
}
}