--Consultamos la malla de una carrera
SELECT * FROM public."MallaAlumno"
WHERE id_almn_carrera IN (
	SELECT id_almn_carrera
	FROM public."AlumnosCarrera"
	 WHERE id_carrera = 3)
AND malla_almn_ciclo = 2
ORDER BY id_almn_carrera;

--Con la suma de materias que tienen por ciclo
SELECT id_almn_carrera, count(id_almn_carrera)
FROM public."MallaAlumno"
WHERE id_almn_carrera IN (
	SELECT id_almn_carrera
	FROM public."AlumnosCarrera"
	 WHERE id_carrera = 3)
AND malla_almn_ciclo = 2
GROUP BY id_almn_carrera
HAVING count(id_almn_carrera) < 10;

--Consultamos todos los estudiantes de esta carrera que no tienen esta materia
SELECT DISTINCT id_almn_carrera FROM public."MallaAlumno"
WHERE id_almn_carrera IN (
	SELECT id_almn_carrera
	FROM public."AlumnosCarrera"
	 WHERE id_carrera = 3)
AND malla_almn_ciclo = 1
AND id_materia <> 72;

INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT DISTINCT id_almn_carrera, 72, 1
  FROM public."MallaAlumno"
  WHERE id_almn_carrera IN (
  	SELECT id_almn_carrera
  	FROM public."AlumnosCarrera"
  	 WHERE id_carrera = 3)
  AND malla_almn_ciclo = 1
  AND id_materia <> 72;

SELECT id_almn_carrera
FROM public."AlumnosCarrera"
WHERE id_carrera = 3
AND id_almn_carrera NOT IN(
	SELECT id_almn_carrera FROM public."MallaAlumno"
		WHERE id_almn_carrera IN (
		SELECT id_almn_carrera
		FROM public."AlumnosCarrera"
		WHERE id_carrera = 3)
		AND malla_almn_ciclo = 1
		AND id_materia = 72)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 72, 1
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 1
			AND id_materia = 72)

INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
        SELECT id_almn_carrera, 71, 1
      	FROM public."AlumnosCarrera"
      	WHERE id_carrera = 3
      	AND id_almn_carrera NOT IN(
      		SELECT id_almn_carrera FROM public."MallaAlumno"
      			WHERE id_almn_carrera IN (
      			SELECT id_almn_carrera
      			FROM public."AlumnosCarrera"
      			WHERE id_carrera = 3)
      			AND malla_almn_ciclo = 1
      			AND id_materia = 71)

INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 74, 1
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 1
			AND id_materia = 74)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 85, 2
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 2
			AND id_materia = 85)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 81, 2
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 2
			AND id_materia = 81)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 80, 2
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 2
			AND id_materia = 80)

--Actualizar la malla
UPDATE public."MallaAlumno"
SET malla_almn_ciclo = 2
WHERE id_malla_alumno IN (
	SELECT id_malla_alumno FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
		AND id_materia = 86
		AND malla_almn_ciclo <> 2)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 88, 3
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 3
			AND id_materia = 88)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 89, 3
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 3
			AND id_materia = 89)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 90, 3
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 3
			AND id_materia = 90)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 104, 4
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 4
			AND id_materia = 104)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 101, 4
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 4
			AND id_materia = 101)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 100, 4
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 4
			AND id_materia = 100)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 109, 5
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 5
			AND id_materia = 109)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 110, 5
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 5
			AND id_materia = 110)

--
INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT id_almn_carrera, 111, 5
	FROM public."AlumnosCarrera"
	WHERE id_carrera = 3
	AND id_almn_carrera NOT IN(
		SELECT id_almn_carrera FROM public."MallaAlumno"
			WHERE id_almn_carrera IN (
			SELECT id_almn_carrera
			FROM public."AlumnosCarrera"
			WHERE id_carrera = 3)
			AND malla_almn_ciclo = 5
			AND id_materia = 111)
