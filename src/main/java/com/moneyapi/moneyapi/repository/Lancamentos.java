package com.moneyapi.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.moneyapi.model.Lancamento;

public interface Lancamentos extends JpaRepository<Lancamento, Long>{
	

}
