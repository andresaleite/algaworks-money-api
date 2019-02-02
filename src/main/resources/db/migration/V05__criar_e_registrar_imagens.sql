CREATE TABLE imagem (
	codigo  SERIAL PRIMARY KEY,
	nome character varying(50) NOT NULL,
	data timestamp NOT NULL,
	imagem bytea NOT NULL
);

CREATE TABLE imagem_fato_ou_fake (
	codigo_fato_ou_fake BIGINT NOT NULL,
	codigo_imagem BIGINT NOT NULL,
	PRIMARY KEY (codigo_fato_ou_fake, codigo_imagem),
	FOREIGN KEY (codigo_imagem) REFERENCES imagem(codigo),
	FOREIGN KEY (codigo_fato_ou_fake) REFERENCES fato_ou_fake(codigo)
	
);