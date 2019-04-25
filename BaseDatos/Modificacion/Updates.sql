UPDATE public."Personas"
SET persona_identificacion = TRIM(persona_identificacion);

--Actualizamos el nombre de la provincia
UPDATE public."Lugares"
SET lugar_nombre = REPLACE (lugar_nombre, 'PROVINCIA DEL', '')
WHERE id_lugar_referencia = 1;

UPDATE public."Lugares"
SET lugar_nombre = REPLACE (lugar_nombre, 'PROVINCIA DE', '')
WHERE id_lugar_referencia = 1;

UPDATE public."Lugares"
SET lugar_nombre = REPLACE (lugar_nombre, 'L ', '')
WHERE id_lugar_referencia = 1;

UPDATE public."Lugares"
SET lugar_nombre = TRIM(lugar_nombre);

SELECT * FROM public."Lugares"
WHERE id_lugar_referencia = 1;

ALTER TABLE public."Notas" ADD UNIQUE(id_almn_curso, id_tipo_nota);

--Ingresar notas para entrenamiento deportivo

CREATE OR REPLACE FUNCTION llenar_notas_periodo()
RETURNS VOID AS $llenar_notas_periodo$
DECLARE
	total INTEGER := 0;
	ingresados INTEGER := 0;
  prd INTEGER := 9;
  id INTEGER:= 0;

  reg RECORD;
  almns_cursos CURSOR FOR  SELECT id_almn_curso
  FROM public."AlumnoCurso" WHERE id_curso IN(
    SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = prd
  );
BEGIN
  SELECT count(*) INTO total
  FROM public."AlumnoCurso" WHERE id_curso IN(
    SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = prd
  );

    OPEN almns_cursos;
    FETCH almns_cursos INTO reg;

    WHILE ( FOUND ) LOOP
			ingresados := ingresados + 1;
      id := reg.id_almn_curso;

      INSERT INTO public."Notas"(
        id_almn_curso, id_tipo_nota)
        SELECT id, id_tipo_nota
        FROM public."TipoDeNota"
        WHERE id_prd_lectivo = prd
        AND tipo_nota_nombre <> 'NOTA FINAL';

      FETCH almns_cursos INTO reg;
    END LOOP;
		RAISE NOTICE 'Total de inserts: % Todos se ingreasaron: %', ingresados, total = ingresados;

  RETURN;
END;
$llenar_notas_periodo$ LANGUAGE plpgsql;

--Cursos que son los que se dupli id materia 18
SELECT * FROM public."Cursos"
WHERE id_prd_lectivo = 8 AND id_jornada = 1
AND curso_ciclo = 3

--Borramos ete curso porque nuncaabrio 
DELETE FROM public."Cursos"
WHERE id_curso = 287;

--Acualizamoscon el id 312