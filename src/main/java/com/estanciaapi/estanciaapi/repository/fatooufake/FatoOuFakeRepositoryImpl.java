package com.estanciaapi.estanciaapi.repository.fatooufake;

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

import com.estanciaapi.estanciaapi.model.FatoOuFake;
import com.estanciaapi.estanciaapi.model.FatoOuFake_;
import com.estanciaapi.estanciaapi.model.Pessoa_;
import com.estanciaapi.estanciaapi.repository.filter.FatoOuFakeFilter;
import com.estanciaapi.estanciaapi.repository.projection.ResumoFatoOuFake;

public class FatoOuFakeRepositoryImpl implements FatoOuFakeRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Page<FatoOuFake> filtrar(FatoOuFakeFilter filtro, Pageable paginacao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<FatoOuFake> criteria = builder.createQuery(FatoOuFake.class);
		Root<FatoOuFake> root = criteria.from(FatoOuFake.class);
		Predicate[] predicado = criarRestricoes(filtro,builder,root);
		criteria.where(predicado);
		
		TypedQuery<FatoOuFake> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,paginacao);
		
		return new PageImpl<>(query.getResultList(), paginacao, total(filtro));
	}

	@Override
	public Page<ResumoFatoOuFake> resumir(FatoOuFakeFilter fatoOuFakeFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoFatoOuFake> criteria = builder.createQuery(ResumoFatoOuFake.class);
		Root<FatoOuFake> root = criteria.from(FatoOuFake.class);
		
		criteria.select(builder.construct(ResumoFatoOuFake.class
				, root.get(FatoOuFake_.codigo)
				, root.get(FatoOuFake_.dataInclusao)
				, root.get(FatoOuFake_.titulo), root.get(FatoOuFake_.fake)
				, root.get(FatoOuFake_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(fatoOuFakeFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoFatoOuFake> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(fatoOuFakeFilter));
	}

	private Predicate[] criarRestricoes(FatoOuFakeFilter filtro, CriteriaBuilder builder, Root<FatoOuFake> root) {

		List<Predicate> restricoes = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filtro.getAfirmativa())) {
			restricoes.add(builder.like(
					builder.lower(root.get(FatoOuFake_.afirmativa)),
					"%"+filtro.getAfirmativa().toLowerCase()+"%"));
		}
		
		if(filtro.getDataDe() != null) {
			restricoes.add(builder.greaterThanOrEqualTo(root.get(FatoOuFake_.dataInclusao), filtro.getDataDe()));
		}
		
		return restricoes.toArray(new Predicate[restricoes.size()]);
	}
	private Long total(FatoOuFakeFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<FatoOuFake> root = criteria.from(FatoOuFake.class);
		
		Predicate[] predicados = criarRestricoes(filtro, builder, root);
		criteria.where(predicados);
		
		criteria.select(builder.count(root));
		
		
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable paginacao) {
		int paginaAtual = paginacao.getPageNumber();
		int totalPorPagina = paginacao.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalPorPagina);		
		
	}




	
}
