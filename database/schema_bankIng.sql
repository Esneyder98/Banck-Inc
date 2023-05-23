-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;

CREATE SEQUENCE cards_car_id_seq;
 
CREATE TABLE IF NOT EXISTS public.cards
(
    card_id bigint NOT NULL DEFAULT nextval('cards_car_id_seq'::regclass),
    card_number bigint NOT NULL,
    holder_name character varying COLLATE pg_catalog."default" NOT NULL,
    state boolean NOT NULL,
    expiration_date timestamp without time zone,
    balance double precision NOT NULL DEFAULT 0,
    product_id integer NOT NULL,
    CONSTRAINT cards_pkey PRIMARY KEY (card_id)
);

CREATE SEQUENCE products_product_id_seq;
CREATE TABLE IF NOT EXISTS public.products
(
    product_id integer NOT NULL DEFAULT nextval('products_product_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (product_id)
);

CREATE SEQUENCE transactions_transaction_id_seq;
CREATE TABLE IF NOT EXISTS public.transactions
(
    transaction_id integer NOT NULL DEFAULT nextval('transactions_transaction_id_seq'::regclass),
    card_id bigint,
    balance numeric,
    transaction_type character varying COLLATE pg_catalog."default",
    date timestamp without time zone,
    CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id)
);

ALTER TABLE IF EXISTS public.cards
    ADD CONSTRAINT "Targeta_id_producto_fkey" FOREIGN KEY (product_id)
    REFERENCES public.products (product_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.transactions
    ADD CONSTRAINT "FK_Targeta" FOREIGN KEY (card_id)
    REFERENCES public.cards (card_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;