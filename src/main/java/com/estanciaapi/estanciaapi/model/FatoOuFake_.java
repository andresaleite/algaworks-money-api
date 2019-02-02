package com.estanciaapi.estanciaapi.model;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FatoOuFake.class)
public abstract class FatoOuFake_ {

	public static volatile SingularAttribute<FatoOuFake, Long> codigo;
	public static volatile SingularAttribute<FatoOuFake, String> afirmativa;
	public static volatile SingularAttribute<FatoOuFake, String> explicacao;
	public static volatile SingularAttribute<FatoOuFake, String> titulo;
	public static volatile SingularAttribute<FatoOuFake, LocalDate> dataInclusao;
	public static volatile SingularAttribute<FatoOuFake, Pessoa> pessoa;
	public static volatile SingularAttribute<FatoOuFake, Boolean> fake;
	
	

}

