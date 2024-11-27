package com.esplai.usuariosyeventos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.esplai.usuariosyeventos.models.Usuario;
import com.esplai.usuariosyeventos.repository.UsuarioRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userRepository.findByUsername(username);
		if (null == user) {
			throw new UsernameNotFoundException("Ususario no encontrado "+username);
		} 	
		return user;
	}
}
