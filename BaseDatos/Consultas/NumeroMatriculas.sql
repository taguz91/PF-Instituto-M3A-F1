
SELECT c.id_prd_lectivo, ac.id_alumno, ac.id_curso,
persona_identificacion AS Cedula,
persona_primer_nombre || ' ' ||persona_segundo_nombre  AS Nombres, 
persona_primer_apellido|| ' ' ||persona_segundo_apellido AS Apellidos,
carrera_codigo AS "Codigo Carrera",
carrera_nombre AS "Carrera" ,
materia_ciclo AS "Nivel Academico", (
	SELECT count(*)
	FROM public."AlumnoCurso"
	WHERE id_alumno = ac.id_alumno
	AND id_curso IN (
		SELECT id_curso
		FROM public."Cursos"
		WHERE id_materia = c.id_materia
	)
) AS "Numero Matricula", 
materia_nombre AS "Materia"
FROM public."AlumnoCurso" ac, public."Cursos" c,
public."Alumnos" a, public."Personas" p,
public."Materias" m, public."Carreras" cr, 
public."PeriodoLectivo" pl
WHERE ac.id_curso = c.id_curso
AND a.id_alumno = (
	SELECT id_alumno
	FROM public."AlumnoCurso"
	WHERE id_alumno = ac.id_alumno
	AND id_curso IN (
		SELECT id_curso
		FROM public."Cursos"
		WHERE id_materia = c.id_materia
	)
	GROUP BY id_alumno 
	HAVING count(*) > 1
)
AND p.id_persona = a.id_persona
AND m.id_materia = c.id_materia
AND c.id_prd_lectivo IN (21, 22, 23, 24, 25, 26, 27, 28, 29)
AND pl.id_prd_lectivo = c.id_prd_lectivo
AND cr.id_carrera = pl.id_carrera
ORDER BY id_prd_lectivo, ac.id_alumno;
