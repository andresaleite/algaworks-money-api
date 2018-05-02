package com.moneyapi.moneyapi.resource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.moneyapi.moneyapi.model.Pessoa;
import com.moneyapi.moneyapi.repository.PessoaRepository;
import com.moneyapi.moneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository bd;
	
	@Autowired
	private ApplicationEventPublisher publicador;
	
	@Autowired
	private PessoaService pessoaService;
	
	
	@GetMapping
	public List<Pessoa> listar(){
		return bd.findAll();
	}
	
	@PostMapping	
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa retorno = bd.save(pessoa);
		publicador.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Pessoa> buscarPorCodigo(@PathVariable Long codigo) throws IOException {
		Optional<Pessoa> retorno =  bd.findById(codigo);
		if(retorno.equals(Optional.empty())) {					
			ResponseEntity.notFound().build();
		}				
		return retorno;
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		bd.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> alterar(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		pessoa.setCodigo(codigo);
		bd.saveAndFlush(pessoa);
		publicador.publishEvent(new RecursoAlteradoEvent(this, response, pessoa.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
	
	@PutMapping("/professor/{codigo}")
	public ResponseEntity<Pessoa> alterarByProfessor(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa p = pessoaService.atualizar(codigo, pessoa);
		publicador.publishEvent(new RecursoAlteradoEvent(this, response, p.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(p);
	}
	
	
	/**
	 * Esse método eu fiz antes do professor
	 * @param codigo
	 * @param ativo
	 */	
	@PutMapping("/{codigo}/{ativo}")
	public void alterarAtivo(@PathVariable Long codigo,@PathVariable boolean ativo) {
		Optional<Pessoa> p = bd.findById(codigo);
		if(p.equals(Optional.empty()))
		throw new EmptyResultDataAccessException(1);
		
		p.get().setCodigo(codigo);
		p.get().setAtivo(ativo);		
		bd.saveAndFlush(p.get());
	}
	
	/**
	 * Esse está igual ao do professor
	 * @param codigo
	 * @param ativo
	 */
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo,@RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo,ativo);		
	}
	
	
}
