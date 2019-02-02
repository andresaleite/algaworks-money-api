package com.estanciaapi.estanciaapi.repository.fatooufake;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.estanciaapi.estanciaapi.model.FatoOuFake;
import com.estanciaapi.estanciaapi.repository.filter.FatoOuFakeFilter;
import com.estanciaapi.estanciaapi.repository.projection.ResumoFatoOuFake;

public interface FatoOuFakeRepositoryQuery {

	public Page<FatoOuFake> filtrar(FatoOuFakeFilter filtro, Pageable paginacao);
	public Page<ResumoFatoOuFake> resumir(FatoOuFakeFilter fatoOuFakeFilter, Pageable pageable);
}
