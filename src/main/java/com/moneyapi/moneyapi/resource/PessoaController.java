package com.moneyapi.moneyapi.resource;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.moneyapi.moneyapi.model.Pessoa;
import com.moneyapi.moneyapi.repository.Pessoas;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private Pessoas bd;
	
	@GetMapping
	public List<Pessoa> listar(){
		return bd.findAll();
	}
	
	@PostMapping	
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa retorno = bd.save(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(retorno.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(retorno);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Pessoa> buscarPorCodigo(@PathVariable Long codigo) throws IOException {
		Optional<Pessoa> retorno =  bd.findById(codigo);
		if(retorno.equals(Optional.empty())) {					
			ResponseEntity.notFound().build();
		}				
		return retorno;
	}
	
}
