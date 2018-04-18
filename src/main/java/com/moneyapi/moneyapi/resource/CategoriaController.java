package com.moneyapi.moneyapi.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.moneyapi.moneyapi.model.Categoria;
import com.moneyapi.moneyapi.repository.Categorias;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private Categorias bd;
	
	@GetMapping
	public List<Categoria> listar(){
		return bd.findAll();
	}
	
	@PostMapping	
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria retorno = bd.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(retorno.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(retorno);
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
