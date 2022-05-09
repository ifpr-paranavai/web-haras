package com.api.apiwebharas.repository;

import com.api.apiwebharas.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	Optional<Usuario> findByEmail(String email);

}