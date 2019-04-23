--Para llenar las notas con datos aleatorios
CREATE OR REPLACE FUNCTION llenar_notas_aleatorias()
RETURNS VOID AS $llenar_notas_aleatorias$
DECLARE
	total INTEGER := 0;
	ingresados INTEGER := 0;

  reg RECORD;
  almns_cursos CURSOR FOR  SELECT id_almn_curso,
  FROM public."AlumnoCurso";
BEGIN
  IF new.prd_lectivo_estado = TRUE THEN
		SELECT count(*) INTO total
		FROM public."AlumnoCurso";

    OPEN almns_cursos;
    FETCH almns_cursos INTO reg;

    WHILE ( FOUND ) LOOP
			ingresados := ingresados + 1;
			UPDATE public."AlumnoCurso"
				SET  almn_curso_nota_final = floor(random()* (90-25 + 1) + 25)
				WHERE id_almn_curso= reg.id_almn_curso;
      FETCH almns_cursos INTO reg;
    END LOOP;
		RAISE NOTICE 'Total de updates: % Todos se ingreasaron: %', ingresados, total = ingresados;
  END IF;
  RETURN;
END;
$llenar_notas_aleatorias$ LANGUAGE plpgsql;
