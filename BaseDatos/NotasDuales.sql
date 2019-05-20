
CREATE TABLE "NotasDuales"(
    id_notas_duales serial NOT NULL PRIMARY KEY,
    notas_pti NUMERIC (6,2),
    notas_estado_pti VARCHAR(20) DEFAULT 'REPROBADO',
    notas_t_empresarial NUMERIC (6,2),
    notas_t_academico NUMERIC (6,2),
    notas_total_practica NUMERIC (6,2),
    notas_estado_practica VARCHAR(20) DEFAULT 'REPROBADO',

    id_prd_lectivo INTEGER
);


CREATE TABLE "DetalleDuales"(
    id_detale_duales serial NOT NULL PRIMARY KEY,
    id_almn_curso INTEGER,
    id_notas_duales INTEGER
);

ALTER TABLE "NotasDuales" ADD CONSTRAINT "PeridoLectivo__notasDuales__fk"
    FOREIGN KEY (id_prd_lectivo) REFERENCES "PeriodoLectivo"
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "DetalleDuales" ADD CONSTRAINT "NotasDuales__detalleDuales__fk"
    FOREIGN KEY (id_notas_duales) REFERENCES "NotasDuales" (id_notas_duales)
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "DetalleDuales" ADD CONSTRAINT "NotasDuales__AlumnoCurso__fk"
    FOREIGN KEY (id_almn_curso) REFERENCES "AlumnoCurso"
        ON DELETE CASCADE ON UPDATE CASCADE;

0703630715=CARRANZA OCHOA ROBERTO  NATANIEL\t0995710017\trncarranza76@gmail.com\tTIEMPO COMPLETO


--Count tipo de nota por periodo
SELECT
"PeriodoLectivo".prd_lectivo_nombre,
COUNT ("TipoDeNota".id_tipo_nota) AS "TIPO DE NOTA"
FROM
"public"."TipoDeNota"
INNER JOIN "public"."PeriodoLectivo" ON "public"."TipoDeNota".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
GROUP BY "PeriodoLectivo".prd_lectivo_nombre
HAVING "PeriodoLectivo".prd_lectivo_nombre = 'TDS 11/2018 - 4/2019';

iptraf <--

SELECT DISTINCT
"public"."PeriodoLectivo".prd_lectivo_nombre,
"public"."Carreras".carrera_modalidad,
t1.id_prd_lectivo,
"public"."PeriodoLectivo".id_prd_lectivo
FROM
"public"."TipoDeNota" AS t1
INNER JOIN "public"."PeriodoLectivo" ON t1.id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera
WHERE
7 != (
	SELECT
		"count" ( * ) 
	FROM
		"public"."TipoDeNota" AS t2
		INNER JOIN "public"."PeriodoLectivo" ON t2.id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
		INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera 
	WHERE
		t2.id_prd_lectivo = t1.id_prd_lectivo 
		AND ( "Carreras".carrera_modalidad ILIKE'%PRESENCIAL%' OR "Carreras".carrera_modalidad ILIKE'%TRADICIONAL%' ) 
	) AND
8 != (
	SELECT
		"count" ( * ) 
	FROM
		"public"."TipoDeNota" AS t2
		INNER JOIN "public"."PeriodoLectivo" ON t2.id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
		INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera 
	WHERE
		t2.id_prd_lectivo = t1.id_prd_lectivo 
	AND ( "Carreras".carrera_modalidad ILIKE'%DUAL%' OR "Carreras".carrera_modalidad ILIKE'%DUAL FOCALIZADA%' ) 
	);

SELECT
	"public"."Notas".id_nota,
	"public"."Notas".nota_valor,
	"public"."Notas".id_tipo_nota,
	"public"."TipoDeNota".tipo_nota_nombre 
FROM
	"public"."Notas"
	INNER JOIN "public"."TipoDeNota" ON "public"."Notas".id_tipo_nota = "public"."TipoDeNota".id_tipo_nota 
WHERE
	"public"."Notas".id_almn_curso = 7374 
	AND tipo_nota_nombre IN ( 'EXAMEN FINAL', 'EXAMEN DE RECUPERACION' );



------ SELECT  REPROADOS > 70
	SELECT
	"public"."AlumnoCurso".id_almn_curso,
	"public"."AlumnoCurso".almn_curso_nota_final,
	"public"."AlumnoCurso".almn_curso_estado,
	"public"."AlumnoCurso".almn_curso_asistencia,
	( 100 * "AlumnoCurso".almn_curso_num_faltas ) / "Materias".materia_horas_presencial AS "porcentaje" 
FROM
	"public"."AlumnoCurso"
	INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
	INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia 
WHERE
	"public"."AlumnoCurso".almn_curso_activo IS TRUE 
	AND "Cursos".id_prd_lectivo IN ( 4, 8 ) 
	AND "AlumnoCurso".almn_curso_estado = 'REPROBADO' 
	AND "AlumnoCurso".almn_curso_nota_final >= 70 
	AND (( 100 * "AlumnoCurso".almn_curso_num_faltas ) / "Materias".materia_horas_presencial ) < 25 
ORDER BY
	"AlumnoCurso".id_almn_curso


-------- SELECT APROBADOS < 70
SELECT
"public"."AlumnoCurso".id_almn_curso,
"public"."AlumnoCurso".almn_curso_nota_final,
"public"."AlumnoCurso".almn_curso_estado,
"public"."AlumnoCurso".almn_curso_asistencia,
( 100 * "AlumnoCurso".almn_curso_num_faltas ) / "Materias".materia_horas_presencial AS porcentaje,
"public"."PeriodoLectivo".prd_lectivo_nombre,
"public"."Docentes".docente_codigo,
"public"."Cursos".curso_nombre
FROM
"public"."AlumnoCurso"
INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."Docentes" ON "public"."Cursos".id_docente = "public"."Docentes".id_docente
WHERE
"public"."AlumnoCurso".almn_curso_activo AND
"public"."Cursos".id_prd_lectivo IN (4, 8) AND
"public"."AlumnoCurso".almn_curso_estado = 'APROBADO' AND
"public"."AlumnoCurso".almn_curso_nota_final < 70 AND
((((( 100 * "AlumnoCurso".almn_curso_num_faltas ) / "Materias".materia_horas_presencial ) < 25)))
ORDER BY
"public"."AlumnoCurso".id_almn_curso ASC


