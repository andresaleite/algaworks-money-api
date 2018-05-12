package com.moneyapi.moneyapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moneyapi.moneyapi.model.Lancamento;
import com.moneyapi.moneyapi.repository.filter.LancamentoFilter;
import com.moneyapi.moneyapi.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter filtro, Pageable paginacao);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
