package com.pla.control.services;

import com.pla.control.models.User;
import com.pla.control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try{
		 user = userRepository.findByUsername(username);
		if (null == user) {
			throw new UsernameNotFoundException("Ususario no encontrado " + username);
		}}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}


}