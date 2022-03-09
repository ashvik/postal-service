CREATE TABLE public.postal_details (
                                       id bigserial NOT NULL,
                                       city_name text NOT NULL,
                                       area_name text NOT NULL,
                                       postal_code text NOT NULL,
                                       CONSTRAINT postal_details_city_name_area_name_postal_code_key UNIQUE (city_name, area_name, postal_code),
                                       CONSTRAINT postal_details_pkey PRIMARY KEY (id)
);
CREATE INDEX postal_details_city_name_idx ON public.postal_details USING btree (city_name, area_name, postal_code);