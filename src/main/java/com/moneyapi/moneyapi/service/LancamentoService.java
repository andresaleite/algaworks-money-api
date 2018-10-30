package com.moneyapi.moneyapi.service;

import java.io.IOException;

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
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();			
		}
		return bd.save(lancamento);
	}
	
	public void remover(Long codigo) {
		bd.delete(codigo);
	}
	
	public void alterar(Long codigo,Lancamento lancamento) {
		lancamento.setCodigo(codigo);
		bd.saveAndFlush(lancamento);
		
	}
	
	public Lancamento atualizar(Long codigo, Lancamento pessoa) {
		Lancamento pessoaSalva = buscarLancamentoPorCodigo(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		return bd.save(pessoaSalva);
	}


	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		/*Optional<Lancamento> pessoaSalva = buscarLancamentoPorCodigo(codigo);
		pessoaSalva.get().setAtivo(ativo);
		bd.save(pessoaSalva);*/
		
	}

	private Lancamento buscarLancamentoPorCodigo(Long codigo) {
		Lancamento pessoaSalva = bd.findOne(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
	

}
