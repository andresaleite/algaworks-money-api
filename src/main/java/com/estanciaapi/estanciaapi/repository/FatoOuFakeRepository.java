package com.estanciaapi.estanciaapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estanciaapi.estanciaapi.model.FatoOuFake;
import com.estanciaapi.estanciaapi.repository.fatooufake.FatoOuFakeRepositoryQuery;

public interface FatoOuFakeRepository extends JpaRepository<FatoOuFake, Long>, FatoOuFakeRepositoryQuery{

	Optional<FatoOuFake> save(Optional<FatoOuFake> pessoaSalva);
	

}
