/*
    CONSULTA PERIODOS LECTIVOS DEL DOCENTE
*/

SELECT
DISTINCT
"public"."PeriodoLectivo".prd_lectivo_nombre
FROM
"public"."Cursos"
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
WHERE 
"Cursos".id_docente = 49

/*
    NOMBRE DE LA CARRERA SEGUN EL NOMBRE DEL PERIODO LECTIVO Y QUE ESE PERIODO LECTIVO ESTE ACTIVO
*/
SELECT
"public"."Carreras".carrera_nombre
FROM
"public"."PeriodoLectivo"
INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera
WHERE
"PeriodoLectivo".prd_lectivo_nombre = 'SDS   MAYO/2019   OCTUBRE/2019'
AND
"PeriodoLectivo".prd_lectivo_estado = false

/*
    INFORMACION DEL CURSO
*/

//NOMBRE DE LOS CURSOS DEL PROFESOR
SELECT
DISTINCT
"public"."Cursos".curso_nombre
FROM
"public"."Cursos"
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
WHERE
"public"."Cursos".id_docente = 55


//NOMBRE, PARALELO Y CICLO DEL CURSO
SELECT 
DISTINCT
"public"."Cursos".curso_nombre,
"public"."Cursos".curso_paralelo,
"public"."Cursos".curso_ciclo
FROM
"public"."Cursos"
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
WHERE
"public"."Cursos".id_docente = 55

//RETORNA LAS MATERIAS 
SELECT
"public"."Materias".materia_nombre
FROM
"public"."Cursos"
INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
WHERE
"public"."Cursos".id_docente = 55 AND
"public"."Cursos".curso_nombre = 'M3B'
AND "PeriodoLectivo".prd_lectivo_nombre = 'SDS 11/2018 - 4/2019'


/*
    RETORNA LOS ALUMNOS DE UN CURSO
*/

SELECT
"public"."AlumnoCurso".id_almn_curso,
"public"."AlumnoCurso".id_alumno,
"public"."AlumnoCurso".id_curso,
"public"."AlumnoCurso".almn_curso_nt_1_parcial,
"public"."AlumnoCurso".almn_curso_nt_examen_interciclo,
"public"."AlumnoCurso".almn_curso_nt_2_parcial,
"public"."AlumnoCurso".almn_curso_nt_examen_final,
"public"."AlumnoCurso".almn_curso_nt_examen_supletorio,
"public"."AlumnoCurso".almn_curso_asistencia,
"public"."AlumnoCurso".almn_curso_nota_final,
"public"."AlumnoCurso".almn_curso_estado,
"public"."AlumnoCurso".almn_curso_num_faltas
FROM
"public"."AlumnoCurso"
INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso





UPDATE "AlumnoCurso" 
SET 
almn_curso_nt_1_parcial = 30, 
almn_curso_nt_examen_interciclo = 15, 
almn_curso_nt_2_parcial = 30, 
almn_curso_nt_examen_final = 25, 
almn_curso_nt_examen_supletorio = 0, 
almn_curso_asistencia = 'ASISTE', 
almn_curso_nota_final = 100, 
almn_curso_estado = 'APROBADO',
almn_curso_num_faltas = 3
WHERE 
id_almn_curso = 54;










SELECT
"AlumnoCurso".id_alumno,
"Personas".persona_identificacion,
"Personas".persona_primer_apellido || '  ' ||"Personas".persona_segundo_apellido AS "APELLIDOS",
"Personas".persona_primer_nombre || '  '||"Personas".persona_segundo_nombre AS "NOMBRES",
"AlumnoCurso".almn_curso_nt_1_parcial,
"AlumnoCurso".almn_curso_nt_examen_interciclo,
"AlumnoCurso".almn_curso_nt_2_parcial,
"AlumnoCurso".almn_curso_nt_examen_final,
"AlumnoCurso".almn_curso_nt_examen_supletorio,
"AlumnoCurso".almn_curso_nota_final,
"AlumnoCurso".almn_curso_estado,
"AlumnoCurso".almn_curso_num_faltas
FROM
"public"."AlumnoCurso"
JOIN "Cursos" ON "AlumnoCurso".id_curso = "Cursos".id_curso
JOIN "Jornadas" ON "Cursos".id_jornada = "Jornadas".id_jornada
JOIN "Materias" ON "Cursos".id_materia = "Materias".id_materia
JOIN "PeriodoLectivo" ON "Cursos".id_prd_lectivo = "PeriodoLectivo".id_prd_lectivo
JOIN "Alumnos" ON "AlumnoCurso".id_alumno = "Alumnos".id_alumno
JOIN "Personas" ON "Alumnos".id_persona = "Personas".id_persona
WHERE
"PeriodoLectivo".prd_lectivo_estado = FALSE AND
"Cursos".id_docente = 55 AND
"PeriodoLectivo".prd_lectivo_nombre = 'SDS   MAYO/2019   OCTUBRE/2019' AND
"Cursos".curso_ciclo = 4 AND
"Cursos".curso_paralelo = 'A' AND
"Jornadas".nombre_jornada = 'MATUTINA'
ORDER BY
"Personas".persona_primer_apellido ASC
;