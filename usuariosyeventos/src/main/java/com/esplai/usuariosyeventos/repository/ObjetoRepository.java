package com.esplai.usuariosyeventos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.esplai.usuariosyeventos.models.Objeto;
import com.esplai.usuariosyeventos.models.Usuario;

public interface ObjetoRepository extends CrudRepository<Objeto, Integer> {
	
	  public List<Objeto> findAll();
}
