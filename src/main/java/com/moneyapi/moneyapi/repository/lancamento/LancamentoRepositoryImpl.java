package com.moneyapi.moneyapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.moneyapi.moneyapi.model.Lancamento;
import com.moneyapi.moneyapi.model.Lancamento_;
import com.moneyapi.moneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filtro, Pageable paginacao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		Predicate[] predicado = CriarRestricoes(filtro,builder,root);
		criteria.where(predicado);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,paginacao);
		
		return new PageImpl<>(query.getResultList(), paginacao, total(filtro));
	}

	

	private Predicate[] CriarRestricoes(LancamentoFilter filtro, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> restricoes = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filtro.getDescricao())) {
			restricoes.add(builder.like(
					builder.lower(root.get(Lancamento_.descricao)),
					"%"+filtro.getDescricao().toLowerCase()+"%"));
		}
		
		if(filtro.getDataDe() != null) {
			restricoes.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filtro.getDataDe()));
		}
		
		if(filtro.getDataAte() != null) {
			restricoes.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filtro.getDataAte()));
		}
		
		
		return restricoes.toArray(new Predicate[restricoes.size()]);
	}
	private Long total(LancamentoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicados = CriarRestricoes(filtro, builder, root);
		criteria.where(predicados);
		
		criteria.select(builder.count(root));
		
		
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable paginacao) {
		int paginaAtual = paginacao.getPageNumber();
		int totalPorPagina = paginacao.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalPorPagina);		
		
	}
}
