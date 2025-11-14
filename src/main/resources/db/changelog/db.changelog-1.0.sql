create table if not exists public.categories (
    id bigserial NOT NULL PRIMARY KEY,
    categories_name varchar(256) unique
);

create table if not exists public.descriptions (
    id bigserial NOT NULL PRIMARY KEY,
    descriptions_name varchar(256) unique
);

CREATE TABLE IF NOT EXISTS public.users (
    id bigserial NOT NULL PRIMARY KEY,
    name_users varchar(256) NOT NULL,
    last_name_users varchar(256) NOT NULL,
    email varchar(256) NOT NULL unique,
    birthdate date NOT NULL,
    password varchar(256) NOT NULL,
    role varchar(256) NOT NULL,
    gender varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.currency_operations (
    id serial NOT NULL PRIMARY KEY,
    name varchar(12) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.expenses (
    id bigserial NOT NULL PRIMARY KEY,
    date_operations timestamp NOT NULL,
    sum_operations numeric(18,5),
    currency_operations_id int references public.currency_operations (id),
    categories_id bigint references public.categories (id),
    descriptions_id bigint references public.descriptions (id),
    users_id bigint NOT NULL references public.users (id)
);

CREATE TABLE IF NOT EXISTS public.files_info (
    id bigserial NOT NULL PRIMARY KEY,
    file_name varchar(256) NOT NULL,
    file_size bigint NOT NULL,
    file_key varchar(256) NOT NULL,
    upload_date date NOT NULL,
    users_id bigint NOT NULL references public.users (id)
);

--CREATE TABLE IF NOT EXISTS public.expensesColumns (
--    date_operations varchar(256) NOT NULL,
--    sum_operations varchar(256) NOT NULL,
--    currency_operations varchar(256) NOT NULL,
--    categories varchar(256) NOT NULL,
--    descriptions varchar(256) NOT NULL
--);
