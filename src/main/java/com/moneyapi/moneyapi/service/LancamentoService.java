package com.moneyapi.moneyapi.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.moneyapi.moneyapi.model.Lancamento;
import com.moneyapi.moneyapi.model.Pessoa;
import com.moneyapi.moneyapi.repository.LancamentoRepository;
import com.moneyapi.moneyapi.repository.filter.LancamentoFilter;
import com.moneyapi.moneyapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository bd;
	
	@Autowired
	private PessoaService pessoaService;
	
	
	
	public Page<Lancamento> filtrar(LancamentoFilter filtro, Pageable paginacao){
		return bd.filtrar(filtro,paginacao);
	}
	
	public Lancamento buscarPorCodigo(Long codigo) throws IOException {
		Lancamento retorno =  bd.findOne(codigo);
		if(retorno == null) {					
			ResponseEntity.notFound().build();
		}				
		return retorno;
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaService.buscarPessoaPorCodigo(lancamento.getPessoa().getCodigo());
		if(pessoa == null || !pessoa.isAtivo()) {
			throw new PessoaInexistenteOuInativaException();			
		}
		return bd.save(lancamento);
	}
	
	public void remover(Long codigo) {
		bd.delete(codigo);
	}
	
	public Lancamento alterar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoPorCodigo(codigo);
		Pessoa pessoa = pessoaService.buscarPessoaPorCodigo(lancamento.getPessoa().getCodigo());
		if(!pessoa.isAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		
		return bd.saveAndFlush(lancamentoSalvo);
	}

	private Lancamento buscarLancamentoPorCodigo(Long codigo) {
		Lancamento lancamentoSalvo = bd.findOne(codigo);
		if(lancamentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo;
	}
	
	

}
