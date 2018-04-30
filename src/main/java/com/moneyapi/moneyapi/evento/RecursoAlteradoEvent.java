package com.moneyapi.moneyapi.evento;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoAlteradoEvent extends ApplicationEvent{

	private static final long serialVersionUID = -5114136076180230999L;
	private HttpServletResponse response;
	private Long codigo;

	public RecursoAlteradoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}
	
	
}
