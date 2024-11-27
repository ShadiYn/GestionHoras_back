package com.esplai.usuariosyeventos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.esplai.usuariosyeventos.models.Usuario;
import com.esplai.usuariosyeventos.models.UsuarioEvento;

public interface UsuarioEventoRepository extends CrudRepository<UsuarioEvento, Integer> {
	
	  public List<UsuarioEvento> findAll();
	  
}
