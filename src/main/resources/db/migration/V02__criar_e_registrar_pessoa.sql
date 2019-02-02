CREATE TABLE public.pessoa
(
    codigo SERIAL,
    nome character varying(50) NOT NULL,
    ativo boolean NOT NULL,
    logradouro character varying(50),
    numero int ,
    complemento character varying(20) ,
    bairro character varying(50) ,
    cep character varying(10) ,
    cidade character varying(20) ,
    estado character varying(20) ,    
    PRIMARY KEY (codigo)
);
