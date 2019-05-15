--Antes de matricularle
SELECT materia_nombre,
persona_primer_nombre,
persona_primer_apellido,
persona_identificacion,
carrera_nombre
FROM public."Materias" m, public."Cursos" c,
public."AlumnosCarrera" ac, public."Alumnos" a,
public."Personas" p, public."Carreras" cr
WHERE m.id_materia = c.id_materia
AND c.id_materia IN (SELECT id_materia
	FROM public."MallaAlumno"
	WHERE id_almn_carrera = 211
	AND malla_almn_ciclo = 3
	AND malla_almn_num_matricula = 1
	AND malla_almn_estado = 'R')
AND c.curso_nombre = 'M3A'
AND c.id_prd_lectivo = 22
AND ac.id_almn_carrera = 211
AND cr.id_carrera = ac.id_carrera
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona;

--Consultando las terceras matriculas

SELECT materia_nombre,
persona_primer_nombre,
persona_primer_apellido,
persona_identificacion,
carrera_nombre
FROM public."Materias" m, public."Cursos" c,
public."AlumnosCarrera" ac, public."Alumnos" a,
public."Personas" p, public."Carreras" cr
WHERE m.id_materia = c.id_materia
AND c.id_materia = 2
AND c.curso_nombre = 'M3A'
AND c.id_prd_lectivo = 22
AND ac.id_almn_carrera = 211
AND cr.id_carrera = ac.id_carrera
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona;


SELECT persona_primer_nombre,
persona_primer_apellido,
persona_identificacion,
carrera_nombre
FROM public."AlumnosCarrera" ac, public."Carreras",
public."Alumnos" a, public."Personas" p
WHERE ac.id_almn_carrera = 211
AND cr.id_carrera = ac.id_carrera
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona;

--Ya luego de matricularle
SELECT persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_nombre,
materia_nombre,
curso_nombre
FROM public."Carreras" cr, public."Cursos" c,
public."Alumnos" a, public."Personas" p,
public."Materias" m
WHERE p.id_persona = a.id_persona
AND a.id_alumno = 745
AND c.id_curso IN (SELECT id_curso
	FROM public."AlumnoCurso"
	WHERE id_alumno =  745
	AND id_prd_lectivo = 21)
AND c.id_materia IN (SELECT id_materia
	FROM public."MallaAlumno"
	WHERE id_almn_carrera = (
		SELECT id_almn_carrera
		FROM public."AlumnosCarrera"
		WHERE id_carrera = cr.id_carrera
		AND id_alumno = 745
	)AND malla_almn_num_matricula = 2
	 AND malla_almn_estado = 'M')
AND m.id_materia = c.id_materia
AND cr.id_carrera = (
	SELECT id_carrera
	FROM public."PeriodoLectivo"
	WHERE id_prd_lectivo = 21
)
AND curso_nombre = ''

--
SELECT persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_nombre
FROM public."Alumnos" a, public."Personas" p,
public."Carreras" cr
WHERE a.id_alumno = 211
AND cr.id_carrera = (SELECT id_carrera
	FROM public."PeriodoLectivo"
	WHERE id_prd_lectivo = 21)
AND p.id_persona = a.id_persona;

--Seleccionamos los nombre de los cursos en los que se matriculo un alumno
SELECT DISTINCT curso_nombre
FROM public."Cursos"
WHERE id_curso IN (SELECT id_curso
	FROM public."AlumnoCurso"
	WHERE id_alumno =
	AND id_prd_lectivo = )


---Sujeto de pruebas
SELECT * FROM public."Alumnos"
WHERE alumno_codigo = '0105714539'

SELECT * FROM public."Personas"
WHERE persona_identificacion = '0105714539'
