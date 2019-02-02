package com.estanciaapi.estanciaapi.service;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.estanciaapi.estanciaapi.model.FatoOuFake;
import com.estanciaapi.estanciaapi.model.Pessoa;
import com.estanciaapi.estanciaapi.repository.FatoOuFakeRepository;
import com.estanciaapi.estanciaapi.repository.filter.FatoOuFakeFilter;
import com.estanciaapi.estanciaapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class FatoOuFakeService {

	@Autowired
	private FatoOuFakeRepository bd;
	
	@Autowired
	private PessoaService pessoaService;
	
	
	
	public Page<FatoOuFake> filtrar(FatoOuFakeFilter filtro, Pageable paginacao){
		return bd.filtrar(filtro,paginacao);
	}
	
	public FatoOuFake buscarPorCodigo(Long codigo) throws IOException {
		FatoOuFake retorno =  bd.findOne(codigo);
		if(retorno == null) {					
			ResponseEntity.notFound().build();
		}				
		return retorno;
	}
	
	public FatoOuFake salvar(FatoOuFake fatoOuFake) {
		Pessoa pessoa = pessoaService.buscarPessoaPorCodigo(fatoOuFake.getPessoa().getCodigo());
		if(pessoa == null || !pessoa.isAtivo()) {
			throw new PessoaInexistenteOuInativaException();			
		}
		return bd.save(fatoOuFake);
	}
	
	public void remover(Long codigo) {
		bd.delete(codigo);
	}
	
	public FatoOuFake alterar(Long codigo, FatoOuFake fatoOuFake) {
		FatoOuFake fatoOuFakeSalvo = buscarFatoOuFakePorCodigo(codigo);
		Pessoa pessoa = pessoaService.buscarPessoaPorCodigo(fatoOuFake.getPessoa().getCodigo());
		if(!pessoa.isAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		BeanUtils.copyProperties(fatoOuFake, fatoOuFakeSalvo, "codigo");
		
		return bd.saveAndFlush(fatoOuFakeSalvo);
	}

	private FatoOuFake buscarFatoOuFakePorCodigo(Long codigo) {
		FatoOuFake fatoOuFakeSalvo = bd.findOne(codigo);
		if(fatoOuFakeSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return fatoOuFakeSalvo;
	}
	
	

}
