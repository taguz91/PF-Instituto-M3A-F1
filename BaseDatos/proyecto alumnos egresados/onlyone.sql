CREATE OR REPLACE FUNCTION promedio_malla_ciclo_not_similar_to (
  id_almn_carr_param INTEGER, ciclo_param INTEGER, filtro_param CHARACTER VARYING(50)
) RETURNS NUMERIC(4, 2) AS $BODY$
DECLARE
  reg RECORD;
  num_materias INTEGER := 0;
  sum_materias NUMERIC(6,2) := 0;

  -- Seleccionando todas las materias del ciclo
  notas CURSOR FOR SELECT
  ma.malla_almn_nota1,
  ma.malla_almn_nota2,
  ma.malla_almn_nota3
  FROM public."MallaAlumno" ma
  JOIN public."Materias" m ON m.id_materia = ma.id_materia
  WHERE id_almn_carrera = id_almn_carr_param
  AND m.materia_ciclo = ciclo_param
  AND m.materia_nombre NOT SIMILAR TO filtro_param;
BEGIN

  OPEN notas;
  FETCH notas INTO reg;
  WHILE ( FOUND ) LOOP
    IF reg.malla_almn_nota3 <> 0 THEN
      sum_materias := sum_materias + reg.malla_almn_nota3;
    ELSE
      IF reg.malla_almn_nota2 <> 0 THEN
        sum_materias := sum_materias + reg.malla_almn_nota2;
      ELSE
        sum_materias := sum_materias + reg.malla_almn_nota1;
      END IF;
    END IF;
    num_materias := num_materias + 1;
    FETCH notas INTO reg;
  END LOOP;
  CLOSE notas;

  RETURN ROUND( sum_materias/num_materias, 2 );
END $BODY$ LANGUAGE plpgsql;


-- Similares

CREATE OR REPLACE FUNCTION promedio_malla_ciclo_similar_to (
  id_almn_carr_param INTEGER, ciclo_param INTEGER, filtro_param CHARACTER VARYING(50)
) RETURNS NUMERIC(4, 2) AS $promedio_malla_ciclo_similar_to$
DECLARE
  reg RECORD;
  num_materias INTEGER := 0;
  sum_materias NUMERIC(6,2) := 0;

  -- Seleccionando todas las materias del ciclo
  notas CURSOR FOR SELECT
  ma.malla_almn_nota1,
  ma.malla_almn_nota2,
  ma.malla_almn_nota3
  FROM public."MallaAlumno" ma
  JOIN public."Materias" m ON m.id_materia = ma.id_materia
  WHERE id_almn_carrera = id_almn_carr_param
  AND m.materia_ciclo = ciclo_param
  AND m.materia_nombre SIMILAR TO filtro_param;
BEGIN

  OPEN notas;
  FETCH notas INTO reg;
  WHILE ( FOUND ) LOOP
    IF reg.malla_almn_nota3 <> 0 THEN
      sum_materias := sum_materias + reg.malla_almn_nota3;
    ELSE
      IF reg.malla_almn_nota2 <> 0 THEN
        sum_materias := sum_materias + reg.malla_almn_nota2;
      ELSE
        sum_materias := sum_materias + reg.malla_almn_nota1;
      END IF;
    END IF;
    num_materias := num_materias + 1;
    FETCH notas INTO reg;
  END LOOP;
  CLOSE notas;

  RETURN ROUND( sum_materias/num_materias, 2);
END $promedio_malla_ciclo_similar_to$ LANGUAGE plpgsql;


--- Consulta loca

SELECT
ma.id_almn_carrera,
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo,
COUNT(ma.malla_almn_ciclo)

FROM public."MallaAlumno" ma
JOIN public."AlumnosCarrera" ac
ON ac.id_almn_carrera = ma.id_almn_carrera
JOIN public."Alumnos" a
ON a.id_alumno = ac.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE ma.id_almn_carrera IN (
  SELECT id_almn_carrera
  FROM public."AlumnosCarrera"
  WHERE id_carrera = 3
) AND ma.malla_almn_estado = 'C'
GROUP BY
ma.id_almn_carrera,
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo
ORDER BY
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
ma.malla_almn_ciclo;

-- 746 id_almn_carrera

-- Promedio solo por alumno 

SELECT
ma.id_almn_carrera,
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo,
COUNT(ma.malla_almn_ciclo) AS num_materias,
promedio_malla_ciclo_not_similar_to(
  ma.id_almn_carrera, malla_almn_ciclo, '%(PTI|FASE PRÁ|INGLÉS)%'
) AS fase_teorica,
promedio_malla_ciclo_similar_to(
  ma.id_almn_carrera, malla_almn_ciclo, '%(FASE PR)%'
) AS fase_practica,
promedio_malla_ciclo_similar_to(
  ma.id_almn_carrera, malla_almn_ciclo, '%(PTI)%'
) AS pti

FROM public."MallaAlumno" ma
JOIN public."AlumnosCarrera" ac
ON ac.id_almn_carrera = ma.id_almn_carrera
JOIN public."Alumnos" a
ON a.id_alumno = ac.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE ma.id_almn_carrera IN (
  SELECT id_almn_carrera
  FROM public."AlumnosCarrera"
  WHERE id_carrera = 3
) AND ma.malla_almn_estado = 'C'
AND ma.id_almn_carrera = 746
GROUP BY
ma.id_almn_carrera,
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo
ORDER BY
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
ma.malla_almn_ciclo;
