package com.esplai.usuariosyeventos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.esplai.usuariosyeventos.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	  // GET
	  public List<Usuario> findAll();
	  public Usuario findById(int id);
	  public Usuario findByNombre(String nombre);
	  public Usuario findByUsername(String username);
	  public Usuario findByApellido(String apellido);

	  // POST


}
