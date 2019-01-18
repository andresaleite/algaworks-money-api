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


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('Andresa',true,	'SQSW 304 I', 				210,'Edificio Estrela','Sudoeste','70.673-409','Brasília','DF');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('Fernando',true,	'Rua norte',80,				'sem comp','Aguas Claras','70.673-404','Taguatinga','DF');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('Danielle',true,	'SMLN 8 lote tal',			3,'','Lago Norte','70.673-405','São Paulo','SP');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('Sharlene',true,	'SHCES 700 bloco c',		8,'','Cruzeiro Velho','70.673-406','Goiânia','GO');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('Thiago',false,	'Quadra tal conjunto tal',	0,'','Guara','70.673-407','Brasília','DF');

