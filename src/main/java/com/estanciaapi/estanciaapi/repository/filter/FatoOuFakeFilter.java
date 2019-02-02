package com.estanciaapi.estanciaapi.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class FatoOuFakeFilter {

	private String afirmativa;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataAte;
	
	
	public LocalDate getDataDe() {
		return dataDe;
	}
	public void setDataDe(LocalDate dataDe) {
		this.dataDe = dataDe;
	}
	public LocalDate getDataAte() {
		return dataAte;
	}
	public void setDataAte(LocalDate dataAte) {
		this.dataAte = dataAte;
	}
	public String getAfirmativa() {
		return afirmativa;
	}
	public void setAfirmativa(String afirmativa) {
		this.afirmativa = afirmativa;
	}
}
