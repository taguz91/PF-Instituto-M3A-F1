CREATE TRIGGER "estado_alumno_duales" BEFORE UPDATE OF "almn_curso_estado", "almn_curso_num_faltas" ON "public"."AlumnoCurso"
FOR EACH ROW
EXECUTE PROCEDURE ;



0104021191	CALLE BUSTOS ANDRÉS MARCELO	0999733570	marceloandrescalle@gmail.com	TIEMPO COMPLETO




SELECT
	(
	SELECT
		lpv.lugar_nombre 
	FROM
		PUBLIC."Lugares" lp,
		PUBLIC."Lugares" lc,
		PUBLIC."Lugares" lpr,
		PUBLIC."Lugares" lpv 
	WHERE
		lp.id_lugar = "public"."Personas".id_lugar_residencia 
		AND lc.id_lugar = lp.id_lugar 
		AND lpr.id_lugar = lc.id_lugar_referencia 
		AND lpv.id_lugar = lpr.id_lugar_referencia 
		AND lpv.lugar_nivel = 2 
	) AS "PROVINCIA",
	'INSTITUTO TECNOLOGICO DEL AZUAY' AS "NOMBRE INSTITUTO",
	"public"."Carreras".carrera_codigo AS "CODIGO CARRERA",
	"public"."Carreras".carrera_nombre AS "CARRERA",
	"public"."Personas".persona_primer_apellido || ' ' || "public"."Personas".persona_segundo_apellido || ' ' || "public"."Personas".persona_primer_nombre || ' ' || "public"."Personas".persona_segundo_nombre AS "APELLIDOS Y NOMBRES",
	"public"."Personas".persona_identificacion AS "NRO. DE IDENTIFICACIÓN",
	"Carreras".carrera_modalidad AS "MODALIDAD DE ESTUDIOS",
	"public"."Cursos".curso_nombre AS "CURSO",
	"public"."Materias".materia_nombre AS "ASIGNATURA",
	per_doc.persona_primer_apellido || ' ' || per_doc.persona_primer_nombre AS "DOCENTE" 
FROM
	"public"."AlumnoCurso"
	INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
	INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia
	INNER JOIN "public"."Alumnos" ON "public"."AlumnoCurso".id_alumno = "public"."Alumnos".id_alumno
	INNER JOIN "public"."Personas" ON "public"."Alumnos".id_persona = "public"."Personas".id_persona
	INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
	INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera
	INNER JOIN "public"."Docentes" ON "public"."Docentes".id_docente = "public"."Cursos".id_docente
	INNER JOIN "public"."Personas" per_doc ON "public"."Docentes".id_persona = per_doc.id_persona 
WHERE
	"public"."AlumnoCurso".almn_curso_asistencia LIKE'%Desertor%' 
	AND "public"."Cursos".id_prd_lectivo = 8 
ORDER BY
	"Personas".persona_primer_apellido,
	"Personas".persona_segundo_apellido