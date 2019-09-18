SELECT  distinct
	persona_identificacion AS "CEDULA",
	persona_primer_nombre || ' ' || persona_segundo_nombre AS "NOMBRES",
	persona_primer_apellido || ' ' || persona_segundo_apellido AS "APELLIDOS",
	to_char( matricula_fecha, 'DD Mon YYYY' ) AS "FECHA DE MATRICULACION" ,C.curso_nombre
FROM
	PUBLIC."Matricula" M,
	PUBLIC."Alumnos" A,
	PUBLIC."Personas" P,
	PUBLIC."AlumnoCurso" Al,
	PUBLIC."Cursos" C
WHERE
	A.id_alumno = M.id_alumno
	AND P.id_persona = A.id_persona
	AND M.id_alumno = Al.id_alumno
	AND Al.id_curso = C.id_curso
	AND M.id_prd_lectivo = C.id_prd_lectivo
	AND M.id_prd_lectivo = $P{periodo}
ORDER BY C.curso_nombre;
