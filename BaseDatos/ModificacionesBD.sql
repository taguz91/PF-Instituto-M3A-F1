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

--Grupo Diego

ALTER TABLE "Cursos" DROP "curso_permiso_ingreso_nt";
