package com.esplai.usuariosyeventos.models;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
@Entity
@Component
public class UsuarioEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne
	private Usuario usuarioid;
	@OneToOne
	private Evento eventoid;
	@ManyToOne
	private Objeto objetos;
	
	private boolean asiste;

	//private Boolean completo;
	public UsuarioEvento() {}
	
	public UsuarioEvento(Usuario usuarioid, Evento eventoid, Objeto objetos, boolean asiste) {
		super();
		this.usuarioid = usuarioid;
		this.eventoid = eventoid;
		this.objetos = objetos;
		this.asiste = asiste;
	}
	
	
	
	
	
}
