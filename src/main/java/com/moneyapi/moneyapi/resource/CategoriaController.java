package com.moneyapi.moneyapi.resource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.moneyapi.evento.RecursoCriadoEvent;
import com.moneyapi.moneyapi.model.Categoria;
import com.moneyapi.moneyapi.repository.Categorias;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private Categorias bd;
	@Autowired
	private ApplicationEventPublisher publicador;
	
	
	
	@GetMapping
	public List<Categoria> listar(){
		return bd.findAll();
	}
	
	@PostMapping	
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria retorno = bd.save(categoria);
		publicador.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Categoria> buscarPorCodigo(@PathVariable Long codigo) throws IOException {
		Optional<Categoria> retorno =  bd.findById(codigo);
		if(retorno.equals(Optional.empty())) {					
			ResponseEntity.notFound().build();
		}		
		
		return retorno;
	}
	
}
