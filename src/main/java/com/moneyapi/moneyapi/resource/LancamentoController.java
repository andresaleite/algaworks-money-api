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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moneyapi.moneyapi.evento.RecursoAlteradoEvent;
import com.moneyapi.moneyapi.evento.RecursoCriadoEvent;
import com.moneyapi.moneyapi.model.Lancamento;
import com.moneyapi.moneyapi.repository.Lancamentos;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	private Lancamentos bd;
	
	@Autowired
	private ApplicationEventPublisher publicador;
	
	@GetMapping
	public List<Lancamento> listar(){
		return bd.findAll();
	}
	
	
	@GetMapping("/{codigo}")
	public Optional<Lancamento> buscarPorCodigo(@PathVariable Long codigo) throws IOException {
		Optional<Lancamento> retorno =  bd.findById(codigo);
		if(retorno.equals(Optional.empty())) {					
			ResponseEntity.notFound().build();
		}				
		return retorno;
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento retorno = bd.save(lancamento);
		publicador.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		bd.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> alterar(@PathVariable Long codigo,@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		lancamento.setCodigo(codigo);
		bd.saveAndFlush(lancamento);
		publicador.publishEvent(new RecursoAlteradoEvent(this, response, lancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
	}
}
