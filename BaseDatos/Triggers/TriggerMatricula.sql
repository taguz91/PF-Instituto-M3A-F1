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


--Iniciamos las notas al matricular
CREATE OR REPLACE FUNCTION iniciar_notas_matricula()
RETURNS TRIGGER AS $iniciar_notas_matricula$
  DECLARE
  dual INTEGER := 0;
  presencial INTEGER := 0;
  total INTEGER := 0;
  ingresados INTEGER := 0;
  modalidad CHARACTER VARYING(30) := 'SM';
  materia CHARACTER VARYING(100) :=  'M';

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

  SELECT carrera_modalidad INTO modalidad
  FROM public."Carreras" c, public."PeriodoLectivo" pl
  WHERE c.id_carrera = pl.id_carrera
  WHERE pl.id_prd_lectivo = (
    SELECT id_prd_lectivo
    FROM public."Cursos"
    WHERE id_curso = new.id_curso
  );


  OPEN tipos_nota;
  FETCH tipos_nota INTO reg;

  WHILE ( FOUND ) LOOP
    ingresados := ingresados + 1;

    SELECT materia_nombre INTO materia
    FROM public."Materia"
    WHERE id_materia = (
      SELECT id_materia
      FROM public."Cursos"
      WHERE id_curso = new.id_curso
    );

    IF modalidad = 'DUAL' THEN
      RAISE NOTICE '% Modalidad: %', modalidad;
    ELSE
      RAISE NOTICE '% Modalidad: %', modalidad;
    END IF;

    RAISE NOTICE '% Ingresamos: %', ingresados, reg.id_tipo_nota;
    FETCH tipos_nota INTO reg;
  END LOOP;
  RAISE NOTICE 'Total de insertados: % Todos se ingreasaron: %', ingresados, total = ingresados;
  CLOSE tipos_nota;
  RETURN NEW;
END;
$iniciar_notas_matricula$ LANGUAGE plpgsql;

--Solo funcion

--Iniciamos las notas al matricular
CREATE OR REPLACE FUNCTION probar_notas(periodo INTEGER)
RETURNS VOID AS $probar_notas$
  DECLARE
  total INTEGER := 0;
  ingresados INTEGER := 0;
  afectados CHARACTER VARYING(100) := 0;

  reg_c RECORD;
  alumnos_curso CURSOR FOR  SELECT id_almn_curso,
  id_curso, id_alumno
  FROM public."AlumnoCurso"
  WHERE id_curso IN (
    SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = periodo
  );

BEGIN

  SELECT count(*) INTO total
  FROM public."AlumnoCurso"
  WHERE id_curso IN (
    SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = periodo
  );

  OPEN alumnos_curso;
  FETCH alumnos_curso INTO reg_c;

  WHILE ( FOUND ) LOOP
    ingresados := ingresados + 1;

    SELECT detalle_notas(reg_c.id_curso, periodo) INTO afectados;
    RAISE NOTICE 'Afecto a %', afectados;

    FETCH alumnos_curso INTO reg_c;
  END LOOP;
  RAISE NOTICE 'Total de insertados: % Todos se ingreasaron: %', ingresados, total = ingresados;
  CLOSE alumnos_curso;
  RETURN;
END;
$probar_notas$ LANGUAGE plpgsql;

--Detalle de las notas

CREATE OR REPLACE FUNCTION detalle_notas(curso INTEGER, periodo INTEGER)
RETURNS VOID AS $detalle_notas$
  DECLARE
  dual INTEGER := 0;
  presencial INTEGER := 0;
  total INTEGER := 0;
  ingresados INTEGER := 0;
  modalidad CHARACTER VARYING(30) := 'SM';
  materia CHARACTER VARYING(100) :=  'M';

  reg_n RECORD;
  tipos_nota CURSOR FOR SELECT id_tipo_nota, tipo_nota_nombre
  FROM public."TipoDeNota"
  WHERE tipo_nota_nombre NOT SIMILAR TO
  '%(NOTA FINAL|PTI|SUBTOTAL FASE PRACTICA|NOTA FINAL TOTAL)%'
  AND tipo_nota_estado = TRUE
  AND id_prd_lectivo = periodo;

BEGIN

  SELECT count(*) INTO total
  FROM public."TipoDeNota"
  WHERE tipo_nota_nombre NOT SIMILAR TO
  '%(NOTA FINAL|PTI|SUBTOTAL FASE PRACTICA|NOTA FINAL TOTAL)%'
  AND tipo_nota_estado = TRUE
  AND id_prd_lectivo = periodo;

  SELECT carrera_modalidad INTO modalidad
  FROM public."Carreras" c, public."PeriodoLectivo" pl
  WHERE c.id_carrera = pl.id_carrera
  AND pl.id_prd_lectivo = (
    SELECT id_prd_lectivo
    FROM public."Cursos"
    WHERE id_curso = curso
  );

  SELECT TRANSLATE(materia_nombre,'ÁÉÍÓÚáéíóú','AEIOUaeiou')
  INTO materia
  FROM public."Materias"
  WHERE id_materia = (
    SELECT id_materia
    FROM public."Cursos"
    WHERE id_curso = curso
  );

  RAISE NOTICE 'D E T A L L E  N O T A S';
  RAISE NOTICE 'Materia %', materia;
  OPEN tipos_nota;
  FETCH tipos_nota INTO reg_n;

  WHILE ( FOUND ) LOOP

    IF modalidad = 'DUAL' THEN
      RAISE NOTICE 'Modalidad: %', modalidad;
      dual := dual + 1;

      IF materia ILIKE '%PTI%' THEN
        RAISE NOTICE 'Es PTI, %, No tiene notas', reg_n.id_tipo_nota;
        ingresados := ingresados + 1;
      ELSIF materia ILIKE '%FASE PRACTICA%' THEN
        RAISE NOTICE 'Es FASE PRACTICA';
        RAISE NOTICE 'Tiene dos notas ';
        IF reg_n.tipo_nota_nombre SIMILAR TO '%(N. TUTOR EMPRESARIAL|N. TUTOR ACADEMICO)%' THEN
          RAISE NOTICE 'Es fase practica, %', reg_n.id_tipo_nota;
          ingresados := ingresados + 1;
        END IF;

      ELSE
        RAISE NOTICE 'Tiene TODAS LAS NOTAS';
        IF reg_n.tipo_nota_nombre NOT IN('NOTA FINAL',
        'PTI', 'SUBTOTAL FASE PRACTICA', 'NOTA FINAL TOTAL')
        AND COALESCE(materia, '') NOT SIMILAR TO '%(PTI|FASE PRACTICA)%' THEN
            RAISE NOTICE 'Tiene todas las notas, %', reg_n.id_tipo_nota;
            ingresados := ingresados + 1;
        END IF;
      END IF;

    ELSE
      presencial := presencial + 1;
      RAISE NOTICE 'Modalidad: %', modalidad;
      RAISE NOTICE 'Tipo de nota:, %', reg_n.id_tipo_nota;
    END IF;
    RAISE NOTICE '-----------------';

    FETCH tipos_nota INTO reg_n;
  END LOOP;

  RAISE NOTICE 'Total de insertados: % Todos se ingreasaron: %', ingresados, total = ingresados;
  RAISE NOTICE 'Total de duales: %', dual;
  RAISE NOTICE 'Total de presenciales: % ', presencial;
  CLOSE tipos_nota;
  RAISE NOTICE 'F I N  D E T A L L E  N O T A S';
  RETURN;
END;
$detalle_notas$ LANGUAGE plpgsql;
