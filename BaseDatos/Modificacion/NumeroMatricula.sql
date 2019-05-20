--Consultamos el numero de clases
--en las que se matriculo un alumno
--Separado por periodo lectivo
SELECT id_prd_lectivo, ac.id_alumno,
persona_primer_nombre, persona_primer_apellido,
count(*)
FROM public."AlumnoCurso" ac, public."Cursos" c,
public."Alumnos" a, public."Personas" p
WHERE ac.id_curso = c.id_curso
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona
GROUP BY ac.id_alumno, id_prd_lectivo,
persona_primer_nombre, persona_primer_apellido
ORDER BY id_prd_lectivo, ac.id_alumno

/*
Consultamos los alumnos y el numero de veces que se an matriculado en esa materia
*/
SELECT id_prd_lectivo, ac.id_alumno, ac.id_curso,
persona_primer_nombre, persona_primer_apellido,
materia_nombre, (
	SELECT count(*)
	FROM public."AlumnoCurso"
	WHERE id_alumno = ac.id_alumno
	AND id_curso IN (
		SELECT id_curso
		FROM public."Cursos"
		WHERE id_materia = c.id_materia
	)
)
FROM public."AlumnoCurso" ac, public."Cursos" c,
public."Alumnos" a, public."Personas" p,
public."Materias" m
WHERE ac.id_curso = c.id_curso
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona
AND m.id_materia = c.id_materia
ORDER BY id_prd_lectivo, ac.id_alumno;


/**
Buscamos las veces que se an matriculado
*/
SELECT id_prd_lectivo, ac.id_alumno, ac.id_curso,
persona_primer_nombre, persona_primer_apellido,
materia_nombre, (
	SELECT count(*)
	FROM public."AlumnoCurso"
	WHERE id_alumno = ac.id_alumno
	AND id_curso IN (
		SELECT id_curso
		FROM public."Cursos"
		WHERE id_materia = c.id_materia
	)
)
FROM public."AlumnoCurso" ac, public."Cursos" c,
public."Alumnos" a, public."Personas" p,
public."Materias" m
WHERE ac.id_curso = c.id_curso
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona
AND m.id_materia = c.id_materia
AND persona_identificacion ILIKE '%0107390270%'
ORDER BY id_prd_lectivo, ac.id_alumno;


--Consultamos el numero de matricula que tuvo en este periodo
SELECT id_prd_lectivo, ac.id_alumno, ac.id_curso, (
	SELECT malla_almn_num_matricula
	FROM public."MallaAlumno"
	WHERE id_almn_carrera = (
		SELECT id_almn_carrera
		FROM public."AlumnosCarrera"
		WHERE id_alumno = ac.id_alumno
		AND id_carrera  = 1
	) AND id_materia = c.id_materia
)
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE ac.id_curso = c.id_curso
AND c.id_prd_lectivo = 22
ORDER BY id_prd_lectivo, ac.id_alumno;

--Solo actualizaremos de los que tienen primera matricula
SELECT id_prd_lectivo, ac.id_alumno, ac.id_curso, c.id_materia, (
	SELECT count(*)
	FROM public."AlumnoCurso"
	WHERE id_alumno = ac.id_alumno
	AND id_curso IN (
		SELECT id_curso
		FROM public."Cursos"
		WHERE id_materia = c.id_materia
	)
)
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE ac.id_curso = c.id_curso
ORDER BY id_prd_lectivo, ac.id_alumno;

--Ponemos en eliminado a los que anularon la matricula
SELECT id_almn_curso FROM public."AlumnoCursoRetirados"

UPDATE public."AlumnoCurso"
SET almn_curso_activo = false
WHERE id_almn_curso IN (
	SELECT id_almn_curso
	FROM public."AlumnoCursoRetirados"
  WHERE retiro_activo = true);



--Funcion para actualizar solo primeras matriculas

---Debemos crear la columna que falta en alumno curso
ALTER TABLE public."AlumnoCurso" ADD COLUMN almn_curso_num_matricula integer DEFAULT 0;

CREATE OR REPLACE FUNCTION ingresar_primera_matricula()
RETURNS VOID AS $ingresar_primera_matricula$
DECLARE
	total INTEGER := 0;
  primera INTEGER := 0;
  segunda INTEGER := 0;
	ingresados INTEGER := 0;
  num INTEGER := 0;
  id INTEGER:= 0;

  reg RECORD;
  almns_cursos CURSOR FOR  SELECT id_prd_lectivo, ac.id_alumno, ac.id_curso, c.id_materia,
  id_almn_curso
  FROM public."AlumnoCurso" ac, public."Cursos" c
  WHERE ac.id_curso = c.id_curso
  AND almn_curso_activo = true
  ORDER BY id_prd_lectivo, ac.id_alumno;
BEGIN
  SELECT count(*) INTO total
    FROM public."AlumnoCurso"
    WHERE almn_curso_activo = true;

    OPEN almns_cursos;

    FETCH almns_cursos INTO reg;

    WHILE ( FOUND ) LOOP

      SELECT count(*) INTO num
    	FROM public."AlumnoCurso"
    	WHERE id_alumno = reg.id_alumno
    	AND id_curso IN (
    		SELECT id_curso
    		FROM public."Cursos"
    		WHERE id_materia = reg.id_materia
    	)AND almn_curso_activo = true;

      id := reg.id_almn_curso;

      IF num = 1 THEN
        primera := primera + 1;
        UPDATE public."AlumnoCurso"
        SET almn_curso_num_matricula = num
        WHERE id_almn_curso = id;
        ingresados := ingresados + 1;
      ELSE
        segunda := segunda + 1;
      END IF;

      FETCH almns_cursos INTO reg;
    END LOOP;
    RAISE NOTICE 'Total por ingresar: % ', total;
		RAISE NOTICE 'Total ingresados: % ', ingresados;
    RAISE NOTICE 'Total de primeras: % ', primera;
    RAISE NOTICE 'Total de segundas: % ', segunda;

  RETURN;
END;
$ingresar_primera_matricula$ LANGUAGE plpgsql;


---Ahora consultamos los alumno curso con segunda matricula

--Consultamos los alumno curso con segunda matricula
SELECT id_alumno, c.id_materia, count(*)
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE almn_curso_num_matricula = 0
AND ac.id_curso = c.id_curso
AND almn_curso_activo = true
GROUP BY id_alumno, c.id_materia
ORDER BY id_alumno, c.id_materia

--
UPDATE public."AlumnoCurso"
SET almn_curso_num_matricula = 2
WHERE id_almn_curso = id;

CREATE OR REPLACE FUNCTION ingresar_segunda_matricula()
RETURNS VOID AS $ingresar_segunda_matricula$
DECLARE
	total INTEGER := 0;
  segunda INTEGER := 0;
	ingresados INTEGER := 0;
  id INTEGER:= 0;

  reg RECORD;
  almns_cursos CURSOR FOR  SELECT id_alumno, c.id_materia
  FROM public."AlumnoCurso" ac, public."Cursos" c
  WHERE almn_curso_num_matricula = 0
  AND ac.id_curso = c.id_curso
  AND almn_curso_activo = true
  GROUP BY id_alumno, c.id_materia
  ORDER BY id_alumno, c.id_materia;
BEGIN
  SELECT count(*) INTO total
    FROM public."AlumnoCurso" ac, public."Cursos" c
    WHERE almn_curso_num_matricula = 0
    AND ac.id_curso = c.id_curso
    AND almn_curso_activo = true
    GROUP BY id_alumno, c.id_materia;

    OPEN almns_cursos;

    FETCH almns_cursos INTO reg;

    WHILE ( FOUND ) LOOP

      SELECT id_almn_curso INTO id
      FROM public."AlumnoCurso"
      WHERE id_alumno = reg.id_alumno
      AND id_curso IN (
      	SELECT id_curso
      	FROM public."Cursos"
      	WHERE id_materia = reg.id_materia)
      AND almn_curso_activo = true
      ORDER BY id_almn_curso DESC
      LIMIT 1;

      segunda := segunda + 1;
      UPDATE public."AlumnoCurso"
      SET almn_curso_num_matricula = 2
      WHERE id_almn_curso = id;
      ingresados := ingresados + 1;

      FETCH almns_cursos INTO reg;
    END LOOP;
		RAISE NOTICE 'Total por ingresar: % ', total;
    RAISE NOTICE 'Total ingresados: % ', ingresados;
    RAISE NOTICE 'Total de segundas: % ', segunda;

  RETURN;
END;
$ingresar_segunda_matricula$ LANGUAGE plpgsql;

--Actualizamos los que quedan con numero de matricula uno
UPDATE public."AlumnoCurso"
SET almn_curso_num_matricula = 2
WHERE id_almn_curso IN (
  SELECT id_almn_curso FROM public."AlumnoCurso"
  WHERE almn_curso_num_matricula = 0
  AND almn_curso_activo = true
);

--Ahora actualizamos acorde a la malla
--Los ultimos periodo lectivos son:
21, 22, 23, 24, 25, 26, 27, 28, 29


--Consultamos que coincida con la malla

--Alumnos que tienen tercera matricula
SELECT id_malla_alumno, ma.id_materia, persona_identificacion,
persona_primer_nombre, persona_primer_apellido,
materia_nombre, carrera_nombre
FROM public."MallaAlumno" ma, public."AlumnosCarrera" ac,
public."Alumnos" a, public."Personas" p, public."Carreras" c,
public."Materias" m
WHERE malla_almn_num_matricula = 3
AND ac.id_almn_carrera = ma.id_almn_carrera
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona
AND c.id_carrera = ac.id_carrera
AND m.id_materia = ma.id_materia

--

SELECT id_alumno, ac.id_curso, c.id_materia,
almn_curso_num_matricula, (
	SELECT malla_almn_num_matricula
	FROM public."MallaAlumno"
	WHERE id_almn_carrera = (
		SELECT id_almn_carrera
		FROM public."AlumnosCarrera"
		WHERE id_alumno = ac.id_alumno
		AND id_carrera = (
			SELECT id_carrera
			FROM public."PeriodoLectivo"
			WHERE id_prd_lectivo = c.id_prd_lectivo
		)
	)AND id_materia = c.id_materia)
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE id_prd_lectivo IN (21, 22, 23, 24, 25, 26, 27, 28, 29)
AND c.id_curso = ac.id_curso
ORDER BY almn_curso_num_matricula DESC
