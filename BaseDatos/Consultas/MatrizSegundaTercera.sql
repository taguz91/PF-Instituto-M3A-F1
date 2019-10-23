-- Con curso y materias en segunda y tercera matricula

SELECT
persona_identificacion AS "CI",
persona_primer_nombre || ' ' ||
persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' ||
persona_segundo_apellido AS "NOMBRES Y APELLIDOS",
carrera_codigo AS "COD CARRERA", (
  SELECT STRING_AGG(
		cu.curso_nombre || ' | ' || ma.materia_nombre, E'\n'
	)
  FROM public."AlumnoCurso" alcu
  JOIN public."Cursos" cu ON cu.id_curso = alcu.id_curso
  JOIN public."Materias" ma ON ma.id_materia = cu.id_materia
  WHERE alcu.id_alumno = ac.id_alumno
  AND alcu.almn_curso_num_matricula = 1
) AS "ASIGNATURA 2DA MATRICULA", (
  SELECT STRING_AGG(
		cu.curso_nombre || ' | ' || ma.materia_nombre, E'\n'
	)
  FROM public."AlumnoCurso" alcu
  JOIN public."Cursos" cu ON cu.id_curso = alcu.id_curso
  JOIN public."Materias" ma ON ma.id_materia = cu.id_materia
  WHERE alcu.id_alumno = ac.id_alumno
  AND alcu.almn_curso_num_matricula = 2
) AS "ASIGNATURA 3DA MATRICULA"


FROM public."AlumnoCurso" ac
JOIN public."Cursos" c ON  ac.id_curso = c.id_curso
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = c.id_prd_lectivo
JOIN public."Carreras" cr ON cr.id_carrera = pl.id_carrera
JOIN public."Alumnos" a ON a.id_alumno = ac.id_alumno
JOIN public."Personas" P ON p.id_persona = a.id_persona
WHERE ac.almn_curso_activo = true
AND ac.almn_curso_estado ILIKE 'REPROBADO'
AND c.id_prd_lectivo IN (21, 22, 23, 24, 25, 26, 27, 28, 29)
GROUP BY
ac.id_alumno,
"CI",
"NOMBRES Y APELLIDOS",
carrera_codigo
ORDER BY "COD CARRERA"

-- Consulta final solo con curso

SELECT
persona_identificacion AS "CI",
persona_primer_nombre || ' ' ||
persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' ||
persona_segundo_apellido AS "NOMBRES Y APELLIDOS",
carrera_codigo AS "COD CARRERA", (
  SELECT STRING_AGG(ma.materia_nombre, ';')
  FROM public."AlumnoCurso" alcu
  JOIN public."Cursos" cu ON cu.id_curso = alcu.id_curso
  JOIN public."Materias" ma ON ma.id_materia = cu.id_materia
  WHERE alcu.id_alumno = ac.id_alumno
  AND alcu.almn_curso_num_matricula = 1
) AS "ASIGNATURA 2DA MATRICULA", (
  SELECT STRING_AGG(ma.materia_nombre, ';')
  FROM public."AlumnoCurso" alcu
  JOIN public."Cursos" cu ON cu.id_curso = alcu.id_curso
  JOIN public."Materias" ma ON ma.id_materia = cu.id_materia
  WHERE alcu.id_alumno = ac.id_alumno
  AND alcu.almn_curso_num_matricula = 2
) AS "ASIGNATURA 3DA MATRICULA"


FROM public."AlumnoCurso" ac
JOIN public."Cursos" c ON  ac.id_curso = c.id_curso
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = c.id_prd_lectivo
JOIN public."Carreras" cr ON cr.id_carrera = pl.id_carrera
JOIN public."Alumnos" a ON a.id_alumno = ac.id_alumno
JOIN public."Personas" P ON p.id_persona = a.id_persona
WHERE ac.almn_curso_activo = true
AND ac.almn_curso_estado ILIKE 'REPROBADO'
AND c.id_prd_lectivo IN (21, 22, 23, 24, 25, 26, 27, 28, 29)
GROUP BY
ac.id_alumno,
"CI",
"NOMBRES Y APELLIDOS",
carrera_codigo
ORDER BY "COD CARRERA"


-- Con consultas en segunda matricula

SELECT
persona_identificacion AS "CI",
persona_primer_nombre || ' ' ||
persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' ||
persona_segundo_apellido AS "NOMBRES Y APELLIDOS",
carrera_codigo AS "COD CARRERA",
materia_nombre AS "ASIGNATURA 2DA MATRICULA"

FROM public."AlumnoCurso" ac
JOIN public."Cursos" c ON  ac.id_curso = c.id_curso
JOIN public."Materias" m ON m.id_materia = c.id_materia
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = c.id_prd_lectivo
JOIN public."Carreras" cr ON cr.id_carrera = pl.id_carrera
JOIN public."Alumnos" a ON a.id_alumno = ac.id_alumno
JOIN public."Personas" P ON p.id_persona = a.id_persona
WHERE ac.almn_curso_activo = true
AND ac.almn_curso_estado ILIKE 'REPROBADO'
AND ac.almn_curso_num_matricula = 1
AND c.id_prd_lectivo IN (21, 22, 23, 24, 25, 26, 27, 28, 29)
GROUP BY
materia_nombre,
ac.id_alumno,
"CI",
"NOMBRES Y APELLIDOS",
carrera_codigo
ORDER BY "NOMBRES Y APELLIDOS"

-- Tercera matricula

SELECT
persona_identificacion AS "CI",
persona_primer_nombre || ' ' ||
persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' ||
persona_segundo_apellido AS "NOMBRES Y APELLIDOS",
carrera_codigo AS "COD CARRERA",
materia_nombre AS "ASIGNATURA 3DA MATRICULA"

FROM public."AlumnoCurso" ac
JOIN public."Cursos" c ON  ac.id_curso = c.id_curso
JOIN public."Materias" m ON m.id_materia = c.id_materia
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = c.id_prd_lectivo
JOIN public."Carreras" cr ON cr.id_carrera = pl.id_carrera
JOIN public."Alumnos" a ON a.id_alumno = ac.id_alumno
JOIN public."Personas" P ON p.id_persona = a.id_persona
WHERE ac.almn_curso_activo = true
AND ac.almn_curso_estado ILIKE 'REPROBADO'
AND ac.almn_curso_num_matricula = 2
AND c.id_prd_lectivo IN (21, 22, 23, 24, 25, 26, 27, 28, 29)
GROUP BY
materia_nombre,
ac.id_alumno,
"CI",
"NOMBRES Y APELLIDOS",
carrera_codigo
ORDER BY "NOMBRES Y APELLIDOS"
