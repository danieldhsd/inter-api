package com.danieldhsd.interapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieldhsd.interapi.model.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
