-- Table: public.Departments

-- DROP TABLE IF EXISTS public."Departments";

CREATE TABLE IF NOT EXISTS public."Departments"
(
    department_id integer NOT NULL,
    department_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT "Departments_pkey" PRIMARY KEY (department_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Departments"
    OWNER to postgres;

-- Table: public.Employees

-- DROP TABLE IF EXISTS public."Employees";

CREATE TABLE IF NOT EXISTS public."Employees"
(
    employee_id integer NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    job_title character varying(255) COLLATE pg_catalog."default",
    salary double precision,
    department_id integer,
    CONSTRAINT "Employees_pkey" PRIMARY KEY (employee_id),
    CONSTRAINT fk_emp_dep FOREIGN KEY (department_id)
        REFERENCES public."Departments" (department_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Employees"
    OWNER to postgres;

-- Table: public.Projects

-- DROP TABLE IF EXISTS public."Projects";

CREATE TABLE IF NOT EXISTS public."Projects"
(
    project_id integer NOT NULL,
    project_name character varying(255) COLLATE pg_catalog."default",
    start_date date,
    end_date date,
    CONSTRAINT "Projects_pkey" PRIMARY KEY (project_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Projects"
    OWNER to postgres;

-- Table: public.Employee_Projects

-- DROP TABLE IF EXISTS public."Employee_Projects";

CREATE TABLE IF NOT EXISTS public."Employee_Projects"
(
    employee_id integer,
    project_id integer,
    CONSTRAINT fk_emp FOREIGN KEY (employee_id)
        REFERENCES public."Employees" (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_prj FOREIGN KEY (project_id)
        REFERENCES public."Projects" (project_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Employee_Projects"
    OWNER to postgres;