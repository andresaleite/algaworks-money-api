package com.estanciaapi.estanciaapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.estanciaapi.estanciaapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Optional<Pessoa> save(Optional<Pessoa> pessoaSalva);
	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
	

}
