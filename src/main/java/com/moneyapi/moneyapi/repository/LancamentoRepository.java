package com.moneyapi.moneyapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.moneyapi.model.Lancamento;
import com.moneyapi.moneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

	Optional<Lancamento> save(Optional<Lancamento> pessoaSalva);
	

}
