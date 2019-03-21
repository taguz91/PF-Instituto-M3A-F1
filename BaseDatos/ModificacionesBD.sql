ALTER TABLE public."UnidadSilabo" ADD COLUMN id_silabo integer NOT NULL;

ALTER TABLE "UnidadSilabo" ADD CONSTRAINT "unidad_silabo_pk1"
FOREIGN KEY ("id_silabo") REFERENCES "Silabo"("id_silabo")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE public."EvaluacionSilabo" DROP CONSTRAINT "EvaluacionSilabo_id_silabo_fkey";

ALTER TABLE public."EvaluacionSilabo" DROP COLUMN id_silabo;

ALTER TABLE public."PeriodoLectivo" ADD COLUMN prd_lectivo_estado BOOLEAN NOT NULL DEFAULT 'false';
