ALTER TABLE public."UnidadSilabo" ADD COLUMN id_silabo integer NOT NULL;

ALTER TABLE "UnidadSilabo" ADD CONSTRAINT "unidad_silabo_pk1"
FOREIGN KEY ("id_silabo") REFERENCES "Silabo"("id_silabo")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE public."EvaluacionSilabo" DROP CONSTRAINT "EvaluacionSilabo_id_silabo_fkey";

ALTER TABLE public."EvaluacionSilabo" DROP COLUMN id_silabo;

ALTER TABLE public."PeriodoLectivo" ADD COLUMN prd_lectivo_estado BOOLEAN NOT NULL DEFAULT 'false';

--Grupo Andres
ALTER TABLE public."UnidadSilabo" ADD COLUMN titulo_unidad TEXT NOT NULL;

ALTER TABLE public."UnidadSilabo" DROP COLUMN estrategias_unidad;

ALTER TABLE public."EvaluacionSilabo" ALTER COLUMN "valoracion" TYPE numeric(4, 1);

ALTER TABLE public."EvaluacionSilabo" ALTER COLUMN "instrumento" TYPE TEXT;

ALTER TABLE "EvaluacionSilabo" DROP "actividad";

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

    

--Grupo Diego

ALTER TABLE "Cursos" DROP "curso_permiso_ingreso_nt";
