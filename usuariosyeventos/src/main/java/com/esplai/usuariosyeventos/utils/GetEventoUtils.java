package com.esplai.usuariosyeventos.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
public class GetEventoUtils {
    @Bean(name = "eventoUtils")
    EventoUtils getEventoUtils() {
        return new EventoUtils();
    }
}
