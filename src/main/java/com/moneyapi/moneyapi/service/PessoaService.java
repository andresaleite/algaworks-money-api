package com.moneyapi.moneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.moneyapi.moneyapi.model.Pessoa;
import com.moneyapi.moneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository bd;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPorCodigo(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		return bd.save(pessoaSalva);
	}


	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPorCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		bd.save(pessoaSalva);
		
	}

	public Pessoa buscarPessoaPorCodigo(Long codigo) {
		Pessoa pessoaSalva = bd.findOne(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
	

}
