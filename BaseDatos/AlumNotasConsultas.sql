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
    CONSULTA DE CARRERAS DE LOS PERIODOS LECTIVOS
*/

SELECT
"public"."Carreras".carrera_nombre
FROM
"public"."Carreras"
INNER JOIN "public"."PeriodoLectivo" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera
WHERE
"PeriodoLectivo".prd_lectivo_nombre = 'SDS 11/2018 - 4/2019'

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

