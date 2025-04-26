CREATE TABLE IF NOT EXISTS public."user"
(
    id uuid NOT NULL,
    username text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.unit
(
    id uuid NOT NULL,
    number_of_rooms integer NOT NULL,
    floor integer,
    description text COLLATE pg_catalog."default",
    accomodation_type text COLLATE pg_catalog."default" NOT NULL,
    cost numeric NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT unit_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE IF NOT EXISTS public.booking
(
    id uuid NOT NULL,
    check_in_date date NOT NULL,
    check_out_date date NOT NULL,
    unit_id uuid NOT NULL,
    user_id uuid NOT NULL,
    status text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT booking_pkey PRIMARY KEY (id),
    CONSTRAINT fk_unit FOREIGN KEY (unit_id)
        REFERENCES public.unit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE IF NOT EXISTS public.payment
(
    id uuid NOT NULL,
    status text COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    booking_id uuid NOT NULL,
    amount numeric NOT NULL,
    CONSTRAINT payment_pkey PRIMARY KEY (id),
    CONSTRAINT fk_booking FOREIGN KEY (booking_id)
        REFERENCES public.booking (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE IF NOT EXISTS public.event
(
    id uuid NOT NULL,
    event_type text COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    booking_id uuid NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT event_pkey PRIMARY KEY (id),
    CONSTRAINT fk_booking FOREIGN KEY (booking_id)
        REFERENCES public.booking (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);