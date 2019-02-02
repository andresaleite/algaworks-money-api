package com.estanciaapi.estanciaapi.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ResumoFatoOuFake {

	private Long codigo;
	private LocalDate dataInicio;
	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	private BigDecimal valor;
	private String categoria;
	private String pessoa;
	
	public ResumoFatoOuFake(Long codigo, LocalDate dataInicio,
			BigDecimal valor, String titulo, String pessoa) {
		this.codigo = codigo;
		this.dataInicio = dataInicio;
		this.valor = valor;
		this.pessoa = pessoa;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPessoa() {
		return pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

}
