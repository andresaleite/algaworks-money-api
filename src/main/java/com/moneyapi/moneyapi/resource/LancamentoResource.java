package com.moneyapi.moneyapi.resource;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.moneyapi.moneyapi.evento.RecursoAlteradoEvent;
import com.moneyapi.moneyapi.evento.RecursoCriadoEvent;
import com.moneyapi.moneyapi.model.Lancamento;
import com.moneyapi.moneyapi.repository.LancamentoRepository;
import com.moneyapi.moneyapi.repository.filter.LancamentoFilter;
import com.moneyapi.moneyapi.service.LancamentoService;

@EnableWebMvc
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;
	
	
	
	@Autowired
	private ApplicationEventPublisher publicador;
	
	
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter filtro, Pageable paginacao ){
		return service.filtrar(filtro,paginacao);
	}
	
	@GetMapping("/{codigo}")
	public Lancamento buscarPorCodigo(@PathVariable Long codigo) throws IOException {
					
		return service.buscarPorCodigo(codigo);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento retorno = service.salvar(lancamento);
		publicador.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		service.remover(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> alterar(@PathVariable Long codigo,@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		lancamento.setCodigo(codigo);		
		service.atualizar(codigo, lancamento);
		publicador.publishEvent(new RecursoAlteradoEvent(this, response, lancamento.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
	}
}
