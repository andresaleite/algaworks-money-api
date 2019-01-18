CREATE TABLE public.lancamento(
    codigo SERIAL,
    descricao character varying(50) NOT NULL,
    data_vencimento date NOT NULL,
    data_pagamento date,
    valor decimal(10,02) NOT NULL,
    observacao character varying(100) ,
    tipo character varying(20) NOT NULL,    
    codigo_categoria integer  REFERENCES categoria,
    codigo_pessoa integer REFERENCES pessoa,
    PRIMARY KEY (codigo)
);


INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,codigo_categoria,codigo_pessoa) 
values('Salário Mensal','2019-07-15','2018-04-25',150.32,'Distribuição de Lucros','RECEITA',1,1);

INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,codigo_categoria,codigo_pessoa) 
values('Bahamas','2018-08-15','2018-04-15',6500.00,null,'RECEITA',1,1);

INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,codigo_categoria,codigo_pessoa) 
values('Top Club','2018-01-15','2018-03-15',120,null,'RECEITA',3,3);

INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,codigo_categoria,codigo_pessoa) 
values('DMAE','2019-04-08','2018-04-01',180,null,'DESPESA',4,1);