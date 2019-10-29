--Tabla docentes
ALTER TABLE public."Docentes" ADD COLUMN docente_titulo character varying(200);
ALTER TABLE public."Docentes" ADD COLUMN docente_abreviatura character varying(20);

ALTER TABLE public."UnidadSilabo" ADD COLUMN id_silabo integer NOT NULL;

ALTER TABLE "UnidadSilabo" ADD CONSTRAINT "unidad_silabo_pk1"
FOREIGN KEY ("id_silabo") REFERENCES "Silabo"("id_silabo")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE public."EvaluacionSilabo" DROP CONSTRAINT "EvaluacionSilabo_id_silabo_fkey";

ALTER TABLE public."EvaluacionSilabo" DROP COLUMN id_silabo;

ALTER TABLE public."PeriodoLectivo" ADD COLUMN prd_lectivo_estado BOOLEAN NOT NULL DEFAULT 'true';

--Grupo Andres
ALTER TABLE public."UnidadSilabo" ADD COLUMN titulo_unidad TEXT NOT NULL;

ALTER TABLE public."UnidadSilabo" DROP COLUMN estrategias_unidad;

ALTER TABLE public."EvaluacionSilabo" ALTER COLUMN "valoracion" TYPE numeric(4, 1);

ALTER TABLE public."EvaluacionSilabo" ALTER COLUMN "instrumento" TYPE TEXT;

ALTER TABLE public."EvaluacionSilabo" DROP "actividad";

ALTER TABLE public."EvaluacionSilabo" ADD COLUMN indicador TEXT NOT NULL;

ALTER TABLE public."Silabo" ADD COLUMN id_prd_lectivo integer NOT NULL;

ALTER TABLE "Silabo" ADD CONSTRAINT "fk_silabo_prd_lectivo"
    FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
        ON DELETE CASCADE ON UPDATE CASCADE;

/*
    NUEVAS
*/

CREATE SEQUENCE public."EstrategiasSilabo_id_estrategia_seq";

ALTER SEQUENCE public."EstrategiasSilabo_id_estrategia_seq"
    OWNER TO postgres;

CREATE TABLE public."EstrategiasAprendizaje"
(
    id_estrategia integer NOT NULL DEFAULT nextval('"EstrategiasSilabo_id_estrategia_seq"'::regclass),
    descripcion_estrategia text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "EstrategiasSilabo_pkey" PRIMARY KEY (id_estrategia)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."EstrategiasAprendizaje"
    OWNER to postgres;


CREATE SEQUENCE public."EstrategiasUnidad_id_estrategia_unidad_seq";

ALTER SEQUENCE public."EstrategiasUnidad_id_estrategia_unidad_seq"
    OWNER TO postgres;

CREATE TABLE public."EstrategiasUnidad"
(
    id_estrategia_unidad integer NOT NULL DEFAULT nextval('"EstrategiasUnidad_id_estrategia_unidad_seq"'::regclass),
    id_unidad integer NOT NULL,
    id_estrategia integer NOT NULL,
    CONSTRAINT "EstrategiasUnidad_pkey" PRIMARY KEY (id_estrategia_unidad),
    CONSTRAINT "EstrategiasUnidad_id_estrategia_fkey" FOREIGN KEY (id_estrategia)
        REFERENCES public."EstrategiasAprendizaje" (id_estrategia) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "EstrategiasUnidad_id_unidad_fkey" FOREIGN KEY (id_unidad)
        REFERENCES public."UnidadSilabo" (id_unidad) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."EstrategiasUnidad"
    OWNER to postgres;

--Eliminando triggers
DROP TRIGGER auditoria_silabo_elim ON public."Silabo";
DROP FUNCTION silabo_elim;

TRUNCATE TABLE public."Silabo" CASCADE;

TRUNCATE TABLE public."EstrategiasAprendizaje" CASCADE;
--Grupo Diego

ALTER TABLE "Cursos" DROP "curso_permiso_ingreso_nt";

--Agregamos un nuevo campo
ALTER TABLE public."AlumnoCurso" ADD COLUMN "almn_curso_fecha_registro" DATE default now();

--Modificaciones -- 09/04/2019

ALTER TABLE public."Silabo" DROP COLUMN estado_silabo;

ALTER TABLE public."Silabo" ADD COLUMN "estado_silabo" integer;


--Modificaciones BD 16/4/2019

CREATE TABLE "RolesPeriodo"(
	"id_rol_prd" serial NOT NULL,
	"id_prd_lectivo" integer NOT NULL,
	"rol_prd" character varying(200) NOT NULL,
	"rol_activo" boolean DEFAULT 'true',
	CONSTRAINT rol_prd_pk PRIMARY KEY ("id_rol_prd")
) WITH (OIDS = FALSE);

CREATE TABLE "RolesDocente"(
	"id_rol_docente" serial NOT NULL,
	"id_docente" integer NOT NULL,
	"id_rol_prd" integer NOT NULL,
	"rol_docente_activo" boolean default 'true',
	CONSTRAINT id_rol_docente PRIMARY KEY("id_rol_docente")
) WITH (OIDS = FALSE);

--FK de roles por periodo lectivo
ALTER TABLE "RolesPeriodo" ADD CONSTRAINT "rol_prd_fk1"
FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
ON UPDATE CASCADE ON DELETE CASCADE;

--FK de la tabla Roles DOcente
ALTER TABLE "RolesDocente" ADD CONSTRAINT "rol_docente_fk1"
FOREIGN KEY ("id_rol_prd") REFERENCES "RolesPeriodo"("id_rol_prd")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "RolesDocente" ADD CONSTRAINT "rol_docente_fk2"
FOREIGN KEY ("id_docente") REFERENCES "Docentes"("id_docente")
ON UPDATE CASCADE ON DELETE CASCADE;

--Columnas aumentadas para la eliminacion logica de curso y alumno curso
ALTER TABLE public."Cursos" ADD COLUMN "curso_activo" boolean default 'true';
ALTER TABLE public."AlumnoCurso" ADD COLUMN "almn_curso_activo" boolean default 'true';
--Para reaccinar un docente a un curso
ALTER TABLE public."Docentes" ADD COLUMN "docente_en_funcion" boolean default 'true';
--Agregamos un llave compuesta
ALTER TABLE public."DocentesMateria" ADD UNIQUE(id_docente, id_materia);


--NUevas tablas GAndres

--ELiminar tablas de KEVIN
DROP TABLE public."Plan_de_clases" CASCADE;
DROP TABLE public."Trabajo_autonomo" CASCADE;
DROP TABLE public."Recursos_plan_clases" CASCADE;
DROP TABLE public."Estrategias_metodologias" CASCADE;
DROP TABLE public."Recursos" CASCADE;

DROP SEQUENCE public."Plan_de_clases_id_plan_clases_seq";
DROP SEQUENCE public."Recursos_plan_clases_id_recursos_plan_clases_seq";
DROP SEQUENCE public."Estrategias_metodologias_id_estrategias_metodologias_seq";
DROP SEQUENCE public."Recursos_id_recurso_seq";

--Actualizamos las tablas 16/4/2019


CREATE SEQUENCE public."PlandeClases_id_plan_clases_seq";

ALTER SEQUENCE public."PlandeClases_id_plan_clases_seq"
    OWNER TO postgres;



CREATE TABLE public."PlandeClases"
(
    id_plan_clases integer NOT NULL DEFAULT nextval('"PlandeClases_id_plan_clases_seq"'::regclass),

    id_curso integer NOT NULL,
    id_unidad integer NOT NULL,
    observaciones text COLLATE pg_catalog."default",
    documento_plan_clases bytea,
    fecha_revision date,
    fecha_generacion date,
    fecha_cierre date,
    CONSTRAINT "PlandeClases_pkey" PRIMARY KEY (id_plan_clases),
    CONSTRAINT id_curso FOREIGN KEY (id_curso)
        REFERENCES public."Cursos" (id_curso) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT id_unidad FOREIGN KEY (id_unidad)
        REFERENCES public."UnidadSilabo" (id_unidad) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."PlandeClases"
    OWNER to postgres;



CREATE TABLE public."TrabajoAutonomo"
(
    id_evaluacion integer NOT NULL,
    id_plan_clases integer NOT NULL,
    CONSTRAINT "Trabajo_autonomo_pkey" PRIMARY KEY (id_evaluacion),
    CONSTRAINT id_evaluacion FOREIGN KEY (id_evaluacion)
        REFERENCES public."EvaluacionSilabo" (id_evaluacion) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_plan_clases FOREIGN KEY (id_plan_clases)
        REFERENCES public."PlandeClases" (id_plan_clases) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."TrabajoAutonomo"
    OWNER to postgres;



CREATE SEQUENCE public."Recursos_id_recurso_seq";

ALTER SEQUENCE public."Recursos_id_recurso_seq"
    OWNER TO postgres;

CREATE TABLE public."Recursos"
(
    id_recurso integer NOT NULL DEFAULT nextval('"Recursos_id_recurso_seq"'::regclass),
    nombre_recursos text COLLATE pg_catalog."default",
    tipo_recurso character(1) COLLATE pg_catalog."default",
    CONSTRAINT "Recursos_pkey" PRIMARY KEY (id_recurso)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Recursos"
    OWNER to postgres;



CREATE SEQUENCE public."RecursosPlanClases_id_recursos_plan_clases_seq";

ALTER SEQUENCE public."RecursosPlanClases_id_recursos_plan_clases_seq"
    OWNER TO postgres;

CREATE TABLE public."RecursosPlanClases"
(
    id_recursos_plan_clases integer NOT NULL DEFAULT nextval('"RecursosPlanClases_id_recursos_plan_clases_seq"'::regclass),
    id_plan_clases integer NOT NULL,
    id_recurso integer NOT NULL,
    CONSTRAINT "RecursosPlanClases_pkey" PRIMARY KEY (id_recursos_plan_clases),
    CONSTRAINT id_plan_clases FOREIGN KEY (id_plan_clases)
        REFERENCES public."PlandeClases" (id_plan_clases) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_recurso FOREIGN KEY (id_recurso)
        REFERENCES public."Recursos" (id_recurso) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."RecursosPlanClases"
    OWNER to postgres;



CREATE SEQUENCE public."EstrategiasMetodologias_id_estrategias_metodologias_seq";

ALTER SEQUENCE public."EstrategiasMetodologias_id_estrategias_metodologias_seq"
    OWNER TO postgres;


CREATE TABLE public."EstrategiasMetodologias"
(
    id_estrategias_metodologias integer NOT NULL DEFAULT nextval('"EstrategiasMetodologias_id_estrategias_metodologias_seq"'::regclass),
    tipo_estrategias_metodologias text COLLATE pg_catalog."default",
    id_plan_de_clases integer NOT NULL,
    id_estrategias_unidad integer NOT NULL,
    CONSTRAINT "EstrategiasMetodologias_pkey" PRIMARY KEY (id_estrategias_metodologias),
    CONSTRAINT id_estrategias_unidad FOREIGN KEY (id_estrategias_metodologias)
        REFERENCES public."EstrategiasUnidad" (id_estrategia_unidad) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_plan_de_clases FOREIGN KEY (id_plan_de_clases)
        REFERENCES public."PlandeClases" (id_plan_clases) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."EstrategiasMetodologias"
    OWNER to postgres;


--Dos campos nuevos
ALTER TABLE public."Silabo" ADD COLUMN documento_silabo bytea;
ALTER TABLE public."Silabo" ADD COLUMN documento_analitico bytea;


--ALTERS G16 16/Abril/2019

ALTER TABLE "TipoDeNota" ADD COLUMN id_carrera integer;

ALTER TABLE "TipoDeNota" ADD CONSTRAINT "carrera_TipoDeNota_fk"
    FOREIGN KEY ("id_carrera") REFERENCES "Carreras" ("id_carrera")
        ON DELETE CASCADE ON UPDATE CASCADE;

--Reestructuracion de para matricula
CREATE TABLE "Matricula"(
	"id_matricula" serial NOT NULL,
	"id_alumno" integer NOT NULL,
	"id_prd_lectivo" integer NOT NULL,
	"matricula_fecha" TIMESTAMP DEFAULT now(),
	"matricula_activa" boolean NOT NULL DEFAULT 'true',
	CONSTRAINT id_matricula_pk PRIMARY KEY("id_matricula")
) WITH (OIDS = FALSE);

ALTER TABLE "Matricula" ADD CONSTRAINT "matricula_fk1"
FOREIGN KEY ("id_alumno") REFERENCES "Alumnos"("id_alumno")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Matricula" ADD CONSTRAINT "matricula_fk2"
FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
ON UPDATE CASCADE ON DELETE CASCADE;


--Creamos una tabla para guardar los retirados
--DROP TABLE "AlumnoCursoRetirados"

CREATE TABLE "AlumnoCursoRetirados"(
	"id_retirado" serial NOT NULL,
	"id_almn_curso" integer NOT NULL,
	"retiro_fecha" TIMESTAMP DEFAULT now(),
	"retiro_observacion" character varying(250) DEFAULT 'SA',
  "retiro_activo" boolean DEFAULT 'true',
	CONSTRAINT id_retirado_pk PRIMARY KEY("id_retirado")
) WITH (OIDS = FALSE);


ALTER TABLE "AlumnoCursoRetirados" ADD CONSTRAINT "retirado_fk1"
FOREIGN KEY ("id_almn_curso") REFERENCES "AlumnoCurso"("id_almn_curso")
ON UPDATE CASCADE ON DELETE CASCADE;

/*
ALTER TABLE "Retirados" ADD CONSTRAINT "retirado_.fk2"
FOREIGN KEY ("id_malla_alumno") REFERENCES "MallaAlumno"("id_malla_alumno")
*/
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE public."HistorialUsuarios" ADD COLUMN historial_ip
character varying (200) NOT NULL DEFAULT '000.00.000.000';

ALTER TABLE "Materias" ADD COLUMN materia_nucleo boolean DEFAULT 'false';
--Updates 20/4/2019
ALTER TABLE "PeriodoLectivo" ADD COLUMN "prd_lectivo_num_cierre" integer NOT NULL DEFAULT '0';
ALTER TABLE "" ADD COLUMN "prd_lectivo_num_cierre" integer NOT NULL DEFAULT '0';


--UPDATES  20/Abril/2019

ALTER TABLE "TipoDeNota" DROP CONSTRAINT "carrera_TipoDeNota_fk";
ALTER TABLE "TipoDeNota" DROP COLUMN id_carrera;
ALTER TABLE "TipoDeNota" ADD COLUMN id_prd_lectivo INTEGER;
ALTER TABLE "TipoDeNota" ADD CONSTRAINT "periodo_lectivo_tipo_de_nota__fk"
    FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
        ON DELETE CASCADE ON UPDATE CASCADE;


--UPDATES 24/Abril/2019
ALTER TABLE "public"."Carreras"
  ADD COLUMN "carrera_emanas" smallint DEFAULT 0;

--Para que salga la hora
ALTER TABLE public."AlumnoCurso" ALTER COLUMN "almn_curso_fecha_registro" TYPE TIMESTAMP;


--
ALTER TABLE public."Matricula" ADD COLUMN matricula_tipo character varying(20) NOT NULL DEFAULT 'ORDINARIA';



/*
    MODIFICANDO LA MALLA
*/
UPDATE "MallaAlumno" 
SET id_materia = 198 , malla_almn_ciclo = 1
WHERE
	id_malla_alumno IN ( SELECT id_malla_alumno FROM "MallaAlumno" WHERE "MallaAlumno".id_materia = 168 AND id_almn_carrera IN ( SELECT id_almn_carrera FROM "public"."AlumnosCarrera" WHERE id_carrera = 6 ) );