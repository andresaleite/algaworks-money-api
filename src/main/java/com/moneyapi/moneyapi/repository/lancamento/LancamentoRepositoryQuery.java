package com.moneyapi.moneyapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moneyapi.moneyapi.model.Lancamento;
import com.moneyapi.moneyapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter filtro, Pageable paginacao);
}
