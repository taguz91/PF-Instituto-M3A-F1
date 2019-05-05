SELECT
"public"."Personas".persona_identificacion,
"public"."Personas".persona_primer_apellido,
"public"."Personas".persona_segundo_apellido,
"public"."Personas".persona_primer_nombre,
"public"."Personas".persona_segundo_nombre,
"public"."Cursos".id_curso,
"public"."Cursos".id_prd_lectivo,
"public"."Cursos".id_docente,
"public"."Cursos".curso_nombre,
"public"."Cursos".curso_activo,
"public"."PeriodoLectivo".prd_lectivo_nombre
FROM
"public"."Cursos"
INNER JOIN "public"."Docentes" ON "public"."Cursos".id_docente = "public"."Docentes".id_docente
INNER JOIN "public"."Personas" ON "public"."Docentes".id_persona = "public"."Personas".id_persona
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
WHERE
"public"."Cursos".id_docente = 59











SELECT
	persona_identificacion,
	persona_primer_apellido,
	persona_segundo_apellido,
	"AlumnoCurso".almn_curso_estado,
	"Cursos".curso_nombre, 
	"Docentes".id_docente
FROM
	PUBLIC."AlumnoCurso"
	INNER JOIN "Cursos" ON "AlumnoCurso".id_curso = "Cursos".id_curso
	INNER JOIN "Docentes" ON "Docentes".id_docente = "Cursos".id_docente
	INNER JOIN "Personas" ON "Personas".id_persona = "Docentes".id_persona 
WHERE
	"AlumnoCurso".id_curso IN ( SELECT id_curso FROM PUBLIC."Cursos" WHERE id_prd_lectivo = 8 ) 
	AND almn_curso_nota_final = 0  AND
	"Docentes".id_docente = 59
ORDER BY
	"Personas".persona_primer_apellido;

/*
    DELETES DE CURSOS DE QUE ESTABAN POR DEMAS
    LOS PROFES NUNCA DIERON CLASES EN ESTE CURSO
    SE BORRARON TODOS LOS DEMAS MENOS LOS DE LA PROFE DORIS
    BORRE UNOS CURSOS QUE ESTAN POR DEMAS NUNCA ESTABAN CON ALUMNOS
*/

DELETE FROM "Cursos" WHERE id_curso = 123;
DELETE FROM "Cursos" WHERE id_curso = 139;
DELETE FROM "Cursos" WHERE id_curso = 126;
DELETE FROM "Cursos" WHERE id_curso = 129;
DELETE FROM "Cursos" WHERE id_curso = 119;