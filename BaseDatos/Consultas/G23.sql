--Consultamos los periodos lectivos para un combo
SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre
FROM public."PeriodoLectivo"
WHERE prd_lectivo_activo = true
ORDER BY prd_lectivo_fecha_inicio;

--Cargamos los alumonos de un curso por periodo lectivo
SELECT DISTINCT c.curso_nombre,
persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido,
persona_segundo_apellido, persona_identificacion
FROM public."AlumnoCurso" ac, public."Alumnos" a, public."Personas" p,
public."Cursos" c
WHERE a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
c.id_prd_lectivo = 19  AND
ac.id_curso = c.id_curso;

--Cargamos los alumnos de un curso
SELECT DISTINCT c.curso_nombre,
persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido,
persona_segundo_apellido, persona_identificacion
FROM public."AlumnoCurso" ac, public."Alumnos" a, public."Personas" p,
public."Cursos" c
WHERE a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
c.curso_nombre = 'M4A'  AND
ac.id_curso = c.id_curso;


--Filtra el numero de Alumnos matriculados segun la ID de la carrera mandada
SELECT COUNT(*) FROM (public."Carreras" c JOIN public."AlumnosCarrera" a
					  USING(id_carrera)) JOIN public."MallaAlumno" m USING(id_almn_carrera)
							WHERE c.id_carrera = 2 AND m.malla_almn_estado LIKE 'M';

--Consultamos todos los alumnos de una malla por carrera
SELECT id_malla_alumno, id_materia, ma.id_almn_carrera,
malla_almn_ciclo, malla_almn_num_matricula,
malla_almn_nota1, malla_almn_nota2,
malla_almn_nota3, malla_almn_estado
FROM public."MallaAlumno" ma, public."AlumnosCarrera" ac
WHERE ac.id_carrera = 2
AND ma.id_almn_carrera = ac.id_almn_carrera
AND malla_almn_estado = 'M';

--Consultamos los horarios con el periodo lectivo

	SELECT id_sesion, sc.id_curso, dia_sesion, hora_inicio_sesion, hora_fin_sesion,
curso_nombre, id_prd_lectivo, id_jornada, materia_nombre
	FROM public."SesionClase" sc, public."Cursos" c,
	public."Materias" m
	WHERE c.id_curso = sc.id_curso AND
	m.id_materia = c.id_materia;

	--Consultamos los alumnos retirados
	SELECT id_retirado, acr.id_almn_curso, retiro_fecha,
retiro_observacion, ac.id_curso, materia_nombre,
ac.id_alumno, persona_identificacion,
persona_primer_nombre, persona_primer_apellido
FROM public."AlumnoCursoRetirados" acr,
public."AlumnoCurso" ac, public."Cursos" c,
public."Materias" m, public."Alumnos" a,
public."Personas" p
WHERE ac.id_almn_curso = acr.id_almn_curso AND
c.id_curso = ac.id_curso AND
m.id_materia = c.id_materia AND
a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
retiro_activo = true;

--Buscamos las materias de un alumno curso
SELECT ac.id_almn_curso, almn_curso_estado,
ac.id_curso, materia_nombre, persona_identificacion,
persona_primer_nombre, persona_primer_apellido
FROM public."AlumnoCurso" ac, public."Cursos" c,
public."Materias" m, public."Alumnos" a,
public."Personas" p
WHERE c.id_curso = ac.id_curso AND
m.id_materia = c.id_materia AND
a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND(
	persona_identificacion ILIKE '%%' OR
	persona_primer_nombre || ' ' || persona_segundo_nombre
	|| ' ' || persona_primer_apellido || ' ' || persona_segundo_apellido
	ILIKE '%%');

--Buscamos los alumnos retirados
SELECT id_retirado, acr.id_almn_curso, retiro_fecha,
retiro_observacion, ac.id_curso, materia_nombre,
ac.id_alumno, persona_identificacion,
persona_primer_nombre, persona_primer_apellido
FROM public."AlumnoCursoRetirados" acr,
public."AlumnoCurso" ac, public."Cursos" c,
public."Materias" m, public."Alumnos" a,
public."Personas" p
WHERE ac.id_almn_curso = acr.id_almn_curso AND
c.id_curso = ac.id_curso AND
m.id_materia = c.id_materia AND
a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
retiro_activo = false AND(
persona_identificacion ILIKE '%%' OR
persona_primer_nombre || ' ' || persona_segundo_nombre
|| ' ' || persona_primer_apellido || ' ' ||persona_segundo_apellido
ILIKE '%%'
OR persona_primer_nombre || ' ' || persona_primer_apellido ILIKE '%%');


--Consultamos el estado de la malla
SELECT malla_almn_estado
FROM public."MallaAlumno"
WHERE id_almn_carrera = (
	SELECT id_almn_carrera
	FROM public."AlumnosCarrera"
	WHERE id_alumno = 227 AND
	id_carrera = (
		SELECT id_carrera
		FROM public."Materias"
		WHERE id_materia = (
			SELECT id_materia
			FROM public."Cursos"
			WHERE id_curso = 320
		)
	)
) AND id_materia = (
	SELECT id_materia
		FROM public."Cursos"
		WHERE id_curso = 320
);


--Consutlamso el estado de una malla solo pasandole el id de alumno curso
SELECT malla_almn_estado
FROM public."MallaAlumno"
WHERE id_almn_carrera = (
	SELECT id_almn_carrera
	FROM public."AlumnosCarrera"
	WHERE id_alumno = (
		SELECT id_alumno
		FROM public."AlumnoCurso"
		WHERE id_almn_curso = 6328) AND
	id_carrera = (
		SELECT id_carrera
		FROM public."Materias"
		WHERE id_materia = (
			SELECT id_materia
			FROM public."Cursos"
			WHERE id_curso = (
				SELECT id_curso
				FROM public."AlumnoCurso"
				WHERE id_almn_curso = 6328
			)
		)
	)
) AND id_materia = (
	SELECT id_materia
		FROM public."Cursos"
		WHERE id_curso = (
			SELECT id_curso
			FROM public."AlumnoCurso"
			WHERE id_almn_curso = 6328)
);
