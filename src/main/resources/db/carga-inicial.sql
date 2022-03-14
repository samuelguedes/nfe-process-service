-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION postgres;

-- DROP SEQUENCE public.tb_duplicata_id_seq;

CREATE SEQUENCE public.tb_duplicata_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 2
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.tb_duplicata_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.tb_duplicata_id_seq TO postgres;

-- DROP SEQUENCE public.tb_nota_fiscal_id_seq;

CREATE SEQUENCE public.tb_nota_fiscal_id_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 3
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.tb_nota_fiscal_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.tb_nota_fiscal_id_seq TO postgres;

-- DROP SEQUENCE public.tb_st_processamento_id_seq;

CREATE SEQUENCE public.tb_st_processamento_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 3
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.tb_st_processamento_id_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.tb_st_processamento_id_seq TO postgres;
-- public.tb_st_processamento definition

-- Drop table

-- DROP TABLE public.tb_st_processamento;

CREATE TABLE public.tb_st_processamento (
	id serial4 NOT NULL,
	ds_processamento varchar(25) NOT NULL,
	CONSTRAINT tb_st_processamento_pk PRIMARY KEY (id),
	CONSTRAINT tb_st_processamento_un UNIQUE (ds_processamento)
);

-- Permissions

ALTER TABLE public.tb_st_processamento OWNER TO postgres;
GRANT ALL ON TABLE public.tb_st_processamento TO postgres;


-- public.tb_nota_fiscal definition

-- Drop table

-- DROP TABLE public.tb_nota_fiscal;

CREATE TABLE public.tb_nota_fiscal (
	id int4 NOT NULL,
	dh_registro timestamp NOT NULL,
	nm_destinatario varchar(100) NOT NULL,
	nm_emitente varchar(100) NOT NULL,
	nu_nota_fiscal varchar(100) NOT NULL,
	vl_nota_fiscal numeric NOT NULL,
	nm_arquivo varchar(255) NOT NULL,
	id_st_processamento int4 NOT NULL DEFAULT 1, -- 1 - "Em processamento", 2 - "Processada" e 3 - "Processada com erro"
	CONSTRAINT tb_nota_fiscal_pkey PRIMARY KEY (id),
	CONSTRAINT tb_nota_fiscal_un UNIQUE (nu_nota_fiscal),
	CONSTRAINT tb_nota_fiscal_fk FOREIGN KEY (id_st_processamento) REFERENCES public.tb_st_processamento(id)
);

-- Column comments

COMMENT ON COLUMN public.tb_nota_fiscal.id_st_processamento IS '1 - "Em processamento", 2 - "Processada" e 3 - "Processada com erro"';

-- Permissions

ALTER TABLE public.tb_nota_fiscal OWNER TO postgres;
GRANT ALL ON TABLE public.tb_nota_fiscal TO postgres;


-- public.tb_duplicata definition

-- Drop table

-- DROP TABLE public.tb_duplicata;

CREATE TABLE public.tb_duplicata (
	id serial4 NOT NULL,
	nr_parcela int4 NOT NULL,
	vr_parcela numeric NOT NULL,
	id_nota_fiscal int4 NOT NULL,
	dt_vencimento timestamp NOT NULL,
	CONSTRAINT tb_duplicata_pk PRIMARY KEY (id),
	CONSTRAINT tb_duplicata_fk FOREIGN KEY (id_nota_fiscal) REFERENCES public.tb_nota_fiscal(id)
);

-- Permissions

ALTER TABLE public.tb_duplicata OWNER TO postgres;
GRANT ALL ON TABLE public.tb_duplicata TO postgres;




-- Permissions

GRANT ALL ON SCHEMA public TO postgres;




-- Iserts

INSERT INTO public.tb_st_processamento (id, ds_processamento) VALUES(0, 'Em processamento');
INSERT INTO public.tb_st_processamento (id, ds_processamento) VALUES(1, 'Processada');
INSERT INTO public.tb_st_processamento (id, ds_processamento) VALUES(2, 'Processada com erro');

INSERT INTO public.tb_nota_fiscal (id, dh_registro, nm_destinatario, nm_emitente, nu_nota_fiscal, vl_nota_fiscal, nm_arquivo, id_st_processamento) VALUES(1, '2022-03-13 17:41:08.526', 'JOÃO ALBERTO', 'JS COMERCIAL E DISTRIBUIDORA LTDA.', '35180706225746000422550010000106141999893855', 15254, 'procNFe.xml', 0);

INSERT INTO public.tb_duplicata (id, nr_parcela, vr_parcela, id_nota_fiscal, dt_vencimento) VALUES(1, 4, 3813.5, 1, '2022-03-13 18:11:24.477');
