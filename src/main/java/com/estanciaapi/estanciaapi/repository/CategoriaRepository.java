package com.estanciaapi.estanciaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estanciaapi.estanciaapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	

}
