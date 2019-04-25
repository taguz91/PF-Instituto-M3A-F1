--AL ingresar un alumno en un curso se crea sus notas

--Trigger al retirar a un alumno de una materia
--VERSION DESARROLLO
CREATE OR REPLACE FUNCTION iniciar_notas_matricula()
RETURNS TRIGGER AS $iniciar_notas_matricula$
  DECLARE
  total INTEGER := 0;
  ingresados INTEGER := 0;

  reg RECORD;
  tipos_nota CURSOR FOR  SELECT id_tipo_nota
  FROM public."TipoDeNota"
  WHERE tipo_nota_nombre <> 'NOTA FINAL'
  AND tipo_nota_estado = TRUE
  AND id_prd_lectivo = (
    SELECT id_prd_lectivo
    FROM public."Cursos"
    WHERE id_curso = new.id_curso
  );
BEGIN

  SELECT count(*) INTO total
  FROM public."TipoDeNota"
  WHERE tipo_nota_nombre <> 'NOTA FINAL'
  AND tipo_nota_estado = TRUE
  AND id_prd_lectivo = (
    SELECT id_prd_lectivo
    FROM public."Cursos"
    WHERE id_curso = new.id_curso
  );

  OPEN tipos_nota;
  FETCH tipos_nota INTO reg;

  WHILE ( FOUND ) LOOP
    ingresados := ingresados + 1;
    INSERT INTO public."Notas" (
      nota_valor, id_almn_curso, id_tipo_nota)
      VALUES(0.0, new.id_almn_curso, reg.id_tipo_nota);
    RAISE NOTICE '% Ingresamos: %', ingresados, reg.id_tipo_nota;
    FETCH tipos_nota INTO reg;
  END LOOP;
  RAISE NOTICE 'Total de insertados: % Todos se ingreasaron: %', ingresados, total = ingresados;
  CLOSE tipos_nota;
  RETURN NEW;
END;
$iniciar_notas_matricula$ LANGUAGE plpgsql;

CREATE TRIGGER iniciar_notas
AFTER INSERT
ON public."AlumnoCurso" FOR EACH ROW
EXECUTE PROCEDURE iniciar_notas_matricula();

--VERSION PRODUCCION

CREATE OR REPLACE FUNCTION iniciar_notas_matricula()
RETURNS TRIGGER AS $iniciar_notas_matricula$
  DECLARE
  reg RECORD;
  tipos_nota CURSOR FOR  SELECT id_tipo_nota
  FROM public."TipoDeNota"
  WHERE tipo_nota_nombre <> 'NOTA FINAL'
  AND tipo_nota_estado = TRUE
  AND id_prd_lectivo = (
    SELECT id_prd_lectivo
    FROM public."Cursos"
    WHERE id_curso = new.id_curso
  );
BEGIN
  OPEN tipos_nota;
  FETCH tipos_nota INTO reg;

  WHILE ( FOUND ) LOOP
    INSERT INTO public."Notas" (
      nota_valor, id_almn_curso, id_tipo_nota)
      VALUES(0.0, new.id_almn_curso, reg.id_tipo_nota);
    FETCH tipos_nota INTO reg;
  END LOOP;
  CLOSE tipos_nota;
  RETURN NEW;
END;
$iniciar_notas_matricula$ LANGUAGE plpgsql;

CREATE TRIGGER iniciar_notas
AFTER INSERT
ON public."AlumnoCurso" FOR EACH ROW
EXECUTE PROCEDURE iniciar_notas_matricula();
