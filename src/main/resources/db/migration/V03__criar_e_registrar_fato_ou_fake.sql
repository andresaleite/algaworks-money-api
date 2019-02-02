CREATE TABLE public.fato_ou_fake(
    codigo SERIAL,
    titulo character varying(50) NOT NULL,
    data_inclusao date NOT NULL,
    afirmativa character varying(1500) NOT NULL,
    explicacao character varying(5000) NOT NULL,  
    fake boolean,  
    codigo_pessoa integer REFERENCES pessoa,
    PRIMARY KEY (codigo)
);

