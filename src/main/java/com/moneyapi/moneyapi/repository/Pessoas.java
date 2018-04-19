package com.moneyapi.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moneyapi.moneyapi.model.Pessoa;

public interface Pessoas extends JpaRepository<Pessoa, Long>{
	

}
