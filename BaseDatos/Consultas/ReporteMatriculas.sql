SELECT
persona_identificacion,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_correo,
persona_celular,
persona_telefono,
carrera_nombre,
carrera_codigo,
prd_lectivo_nombre,
	STRING_AGG(
		c.curso_nombre || '  # ' || ac.almn_curso_num_matricula || ':  ' || materia_nombre, E'\n'
	) Materias
FROM public."Carreras" cr, public."Cursos" c,
public."Alumnos" a, public."Personas" p,
public."Materias" m, public."AlumnoCurso" ac,
public."PeriodoLectivo" pl
WHERE p.id_persona = a.id_persona
AND a.id_alumno = ac.id_alumno
AND ac.id_curso = c.id_curso
AND m.id_materia = c.id_materia
AND pl.id_prd_lectivo = c.id_prd_lectivo
AND cr.id_carrera = pl.id_carrera
AND ac.almn_curso_activo = true


GROUP BY persona_identificacion,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_correo,
persona_celular,
persona_telefono,
carrera_nombre,
carrera_codigo,
prd_lectivo_nombre
ORDER BY persona_primer_apellido, persona_segundo_apellido
