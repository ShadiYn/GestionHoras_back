package com.esplai.usuariosyeventos.utils;

import java.util.List;

import org.springframework.stereotype.Service;
import com.esplai.usuariosyeventos.models.Evento;
import com.esplai.usuariosyeventos.models.Usuario;


@Service
public class EventoUtils {

	public boolean AddParticipante(Evento evento, Usuario usuario) {
		List<Usuario> part = evento.getParticipantes();
		part.add(usuario);
		evento.setParticipantes(part);
		return true;
	}

	public boolean RemoveParticipante(Evento evento, Usuario usuario) {
		List<Usuario> part = evento.getParticipantes();
		part.remove(usuario);
		evento.setParticipantes(part);
		return true;
	}

	public boolean checkInsideParticipant(Evento evento, Usuario usuario) {
		List<Usuario> part = evento.getParticipantes();
		for (Usuario user : part) {
			if(user.getId() == usuario.getId()) {
				return true;
			}
		}
		return false;
	}
	

}
