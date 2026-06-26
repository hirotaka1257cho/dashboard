-- Table: public.monitoring_targets

-- DROP TABLE IF EXISTS public.monitoring_targets;

CREATE TABLE IF NOT EXISTS public.monitoring_targets
(
    id bigserial NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    company character varying(255) COLLATE pg_catalog."default" NOT NULL,
    url character varying(500) COLLATE pg_catalog."default" NOT NULL,
    check_interval integer NOT NULL DEFAULT 5,
    failure_threshold integer NOT NULL DEFAULT 3,
    status character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT 'UNKNOWN'::character varying,
    created_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT monitoring_targets_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.monitoring_targets
    OWNER to postgres;

-- Table: public.monitoring_results

-- DROP TABLE IF EXISTS public.monitoring_results;

CREATE TABLE IF NOT EXISTS public.monitoring_results
(
    id bigserial NOT NULL,
    target_id bigint NOT NULL,
    checked_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    success boolean NOT NULL,
    status_code integer,
    response_time integer,
    CONSTRAINT monitoring_results_pkey PRIMARY KEY (id),
    CONSTRAINT monitoring_results_target_id_fkey FOREIGN KEY (target_id)
        REFERENCES public.monitoring_targets (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.monitoring_results
    OWNER to postgres;

-- Table: public.incidents

-- DROP TABLE IF EXISTS public.incidents;

CREATE TABLE IF NOT EXISTS public.incidents
(
    id bigserial NOT NULL,
    target_id bigint NOT NULL,
    occurred_at timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    recovered_at timestamp without time zone,
    failure_type character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT incidents_pkey PRIMARY KEY (id),
    CONSTRAINT incidents_target_id_fkey FOREIGN KEY (target_id)
        REFERENCES public.monitoring_targets (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.incidents
    OWNER to postgres;
