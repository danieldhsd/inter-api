package com.danieldhsd.interapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.danieldhsd.interapi.model.DigitoUnico;

@Repository
public interface DigitosUnicosRepository extends JpaRepository<DigitoUnico, Long> {

	@Query("SELECT d FROM DigitoUnico d WHERE d.usuario.id = :idUser")
	public List<DigitoUnico> findAllDigitosUnicosByUsuario(@Param("idUser") Long idUser);
}
