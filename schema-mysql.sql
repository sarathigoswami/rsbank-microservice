-- SQLINES FOR EVALUATION USE ONLY (14 DAYS)
CREATE TABLE IF NOT EXISTS public.accounts (
    customer_id bigint NOT NULL,
    account_type character varying(2),
    account_number bigint NOT NULL,
    branch_address character varying(100),
    created_by character varying(10),
    created_date datetime(6),
    updated_by character varying(10),
    updated_date datetime(6),
    /* Name ignored: CONSTRAINT accounts_pkey */ PRIMARY KEY (account_number)
);


CREATE TABLE IF NOT EXISTS public.card (
    card_id bigserial NOT NULL,
    card_number character varying(255),
    card_type character varying(255),
    mobile_number character varying(255),
    total_limit integer NOT NULL,
    available_amount integer NOT NULL,
    amount_used integer NOT NULL,
    created_by character varying(10),
    created_date datetime(6),
    updated_by character varying(10),
    updated_date datetime(6),
    /* Name ignored: CONSTRAINT card_pkey */ PRIMARY KEY (card_id)
);

CREATE TABLE IF NOT EXISTS public.customer (
    customer_id bigserial NOT NULL,
    name character varying(100) NOT NULL,
    mobile_number character varying(10) NOT NULL,
    email character varying(100) NOT NULL,
    created_by character varying(10),
    created_date datetime(6),
    updated_by character varying(10),
    updated_date datetime(6),
    /* Name ignored: CONSTRAINT customer_pkey */ PRIMARY KEY (customer_id)
);

CREATE TABLE IF NOT EXISTS public.loan (
    loan_id bigserial NOT NULL,
    loan_number character varying(255),
    loan_type character varying(255),
    mobile_number character varying(255),
    total_loan integer NOT NULL,
    outstanding_amount integer NOT NULL,
    amount_paid integer NOT NULL,
    created_by character varying(10),
    created_date datetime(6),
    updated_by character varying(10),
    updated_date datetime(6),
    /* Name ignored: CONSTRAINT loan_pkey */ PRIMARY KEY (loan_id)
);