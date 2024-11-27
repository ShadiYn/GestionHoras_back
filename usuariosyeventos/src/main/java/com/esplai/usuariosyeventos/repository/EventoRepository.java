package com.esplai.usuariosyeventos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.esplai.usuariosyeventos.models.Evento;
import com.esplai.usuariosyeventos.models.Usuario;

public interface EventoRepository extends CrudRepository<Evento, Integer> {
	
	  public List<Evento> findAll();
	  public Evento findByNombre(String nombre);
	  public Evento findById(int id);
}
