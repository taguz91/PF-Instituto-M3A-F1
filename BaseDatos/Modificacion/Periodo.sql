ALTER TABLE public."PeriodoLectivo" ADD COLUMN
prd_lectivo_fecha_fin_clases DATE;


ALTER TABLE public."PeriodoLectivo" ADD COLUMN
prd_lectivo_coordinador INTEGER NOT NULL DEFAULT 33;


ALTER TABLE "PeriodoLectivo" ADD CONSTRAINT "prd_lectivo_coordinador_fk"
FOREIGN KEY ("prd_lectivo_coordinador") REFERENCES "Docentes"("id_docente")
ON UPDATE CASCADE ON DELETE CASCADE;


-- Trigger para actualizar el coordinador sen carrera si se cambia en un periodo superior

CREATE OR REPLACE FUNCTION actualizar_coordinador_carrera()
RETURNS TRIGGER AS $actualizar_coordinador_carrera$
DECLARE
  prd_superior INTEGER := 0;
BEGIN

  SELECT id_prd_lectivo INTO prd_superior
  FROM public."PeriodoLectivo"
  WHERE id_carrera = new.id_carrera
  AND prd_lectivo_fecha_inicio > new.prd_lectivo_fecha_inicio;
  -- Esto significa que no existe ningun periodo superior
  IF prd_superior IS NULL THEN
    UPDATE public."Carreras"
    SET id_docente_coordinador = new.prd_lectivo_coordinador
    WHERE id_carrera = new.id_carrera;
  END IF;

  RETURN NEW;
END;
$actualizar_coordinador_carrera$  LANGUAGE plpgsql;

-- Generar los tipos de nota automaticamente del periodo


CREATE OR REPLACE FUNCTION crear_tipos_notas()
RETURNS TRIGGER AS $crear_tipos_notas$
DECLARE
  id_periodo_anterior INTEGER := 0;

BEGIN

  SELECT id_prd_lectivo INTO id_periodo_anterior
  FROM public."PeriodoLectivo"
  WHERE id_carrera = new.id_carrera
  AND id_prd_lectivo <> new.id_prd_lectivo
  ORDER BY prd_lectivo_fecha_fin DESC
  LIMIT 1;
  -- Esto significa que no existe ningun periodo superior
  IF id_periodo_anterior <> 0 THEN
    INSERT INTO public."TipoDeNota"(
      tipo_nota_nombre,
      tipo_nota_valor_minimo,
      tipo_nota_valor_maximo,
      tipo_nota_fecha_creacion,
      tipo_nota_estado,
      id_prd_lectivo
    ) SELECT tipo_nota_nombre,
    tipo_nota_valor_minimo,
    tipo_nota_valor_maximo,
    tipo_nota_fecha_creacion,
    tipo_nota_estado,
    new.id_prd_lectivo
    FROM public."TipoDeNota"
    WHERE id_prd_lectivo = id_periodo_anterior;
  END IF;
  RETURN NEW;
END;
$crear_tipos_notas$  LANGUAGE plpgsql;


-- Iniciando triggers


CREATE TRIGGER act_coordinador
AFTER INSERT OR UPDATE
ON public."PeriodoLectivo" FOR EACH ROW
EXECUTE PROCEDURE actualizar_coordinador_carrera();


CREATE TRIGGER tipo_notas_periodo
AFTER INSERT
ON public."PeriodoLectivo" FOR EACH ROW
EXECUTE PROCEDURE crear_tipos_notas();
