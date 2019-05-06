--COnsutlamos los reprobados de desarrollo de software
SELECT
	persona_identificacion AS "CEDULA",
	persona_primer_nombre || '  ' || persona_segundo_nombre AS "NOMBRES",
	persona_primer_apellido || '  ' || persona_segundo_apellido AS "APELLIDOS",
	persona_correo AS "Correo", 
	persona_telefono AS "Telefono",
	persona_celular AS "Celular",
	materia_nombre AS "MATERIA",
	lc.lugar_nombre AS "Ciudad",
	malla_almn_num_matricula AS "NUMERO DE MATRICULA",
	curso_nombre AS "CURSO",
	almn_curso_nota_final AS "NOTA FINAL" 
FROM
	PUBLIC."AlumnoCurso" ac,
	PUBLIC."Cursos" C,
	PUBLIC."Materias" M,
	PUBLIC."MallaAlumno" ma,
	PUBLIC."AlumnosCarrera" ar,
	PUBLIC."Personas" P,
	PUBLIC."Alumnos" A, 
	public."Lugares" l, 
	public."Lugares" lc
WHERE
	C.id_curso = ac.id_curso 
	AND C.id_prd_lectivo = 4 
	AND M.id_materia = C.id_materia 
	AND ma.id_materia = C.id_materia 
	AND ar.id_alumno = ac.id_alumno 
	AND ma.id_almn_carrera = ar.id_almn_carrera 
	AND ac.almn_curso_nota_final < 70 
	AND A.id_alumno = ac.id_alumno 
	AND P.id_persona = A.id_persona
	AND P.id_lugar_residencia = l.id_lugar
	AND lc.id_lugar = l.id_lugar_referencia 
	AND ac.almn_curso_estado NOT ILIKE '%RETIRADO%'
ORDER BY P.persona_identificacion


--Alumnos reporbados de analisis 
SELECT
	persona_identificacion AS "CEDULA",
	persona_primer_nombre || '  ' || persona_segundo_nombre AS "NOMBRES",
	persona_primer_apellido || '  ' || persona_segundo_apellido AS "APELLIDOS",
	materia_nombre AS "MATERIA",
	malla_almn_num_matricula AS "NUMERO DE MATRICULA",
	curso_nombre AS "CURSO",
	almn_curso_nota_final AS "NOTA FINAL" 
FROM
	PUBLIC."AlumnoCurso" ac,
	PUBLIC."Cursos" C,
	PUBLIC."Materias" M,
	PUBLIC."MallaAlumno" ma,
	PUBLIC."AlumnosCarrera" ar,
	PUBLIC."Personas" P,
	PUBLIC."Alumnos" A 
WHERE
	C.id_curso = ac.id_curso 
	AND C.id_prd_lectivo = 8 
	AND M.id_materia = C.id_materia 
	AND ma.id_materia = C.id_materia 
	AND ar.id_alumno = ac.id_alumno 
	AND ma.id_almn_carrera = ar.id_almn_carrera 
	AND ac.almn_curso_nota_final < 70 
	AND A.id_alumno = ac.id_alumno 
	AND P.id_persona = A.id_persona
ORDER BY P.persona_identificacion