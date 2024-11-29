package com.pla.control.configs;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Value("${app.local-domain-front}")
	private String localDomainFront;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AuthenticationSuccessHandler succesHandler() {
		return (request, response, authentication) -> response.sendRedirect("/");

	}


	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws Exception {
		AuthenticationManagerBuilder authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManager.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		return authenticationManager.build();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Desactiva CSRF para simplificar pruebas
				.cors(withDefaults()) // Habilita CORS
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/register", "/login").permitAll() // Sin autenticación
						.anyRequest().authenticated() // Las demás rutas requieren autenticación
				)
				.httpBasic(withDefaults()); // Usa autenticación básica para otras rutas
		return http.build();
	}


	// Configuración del CORS (Cross-origin resource sharing)
	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:5173") // Permitir todos los orígenes (solo para pruebas)
						.allowedMethods("POST", "PUT", "GET", "DELETE", "OPTIONS");
			}
		};
	}

}
