
CREATE TABLE "Notas"(
    "id_nota" SERIAL NOT NULL PRIMARY KEY,
    "nota_valor" NUMERIC(6,2) DEFAULT 0.0,
    "id_almn_curso" INTEGER,
    "id_tipo_nota" INTEGER

)WITH(OIDS = FALSE);


--FOREIGN KEYS

ALTER TABLE "AlumnoCurso2" ADD CONSTRAINT "alumno__alumno_curso__fk"
    FOREIGN KEY ("id_alumno") REFERENCES "Alumnos"("id_alumno")
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "AlumnoCurso2" ADD CONSTRAINT "curso__alumno_curso__fk"
    FOREIGN KEY ("id_curso") REFERENCES "Cursos"("id_curso")
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "Notas" ADD CONSTRAINT "alumno_curso__notas_fk"
    FOREIGN KEY ("id_almn_curso") REFERENCES "AlumnoCurso2"("id_almn_curso")
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "Notas" ADD CONSTRAINT "tipo_de_nota__notas_fk"
    FOREIGN KEY ("id_tipo_nota") REFERENCES "TipoDeNota"("id_tipo_nota")
        ON DELETE CASCADE ON UPDATE CASCADE;


--MODIFICACIONES 20/Abril/2019
ALTER TABLE "AlumnoCurso2" ADD COLUMN "almn_curso_nota_final" NUMERIC(6,2) DEFAULT '0';




--MIGRACION DE NOTAS
INSERT INTO "AlumnoCurso2"
(id_alumno, id_curso, almn_curso_asistencia, almn_curso_estado, almn_curso_num_faltas, almn_curso_nota_final)
SELECT
"public"."AlumnoCurso".id_alumno,
"public"."AlumnoCurso".id_curso,
"public"."AlumnoCurso".almn_curso_asistencia,
"public"."AlumnoCurso".almn_curso_estado,
"public"."AlumnoCurso".almn_curso_num_faltas,
"public"."AlumnoCurso".almn_curso_nota_final
FROM
"public"."AlumnoCurso"
INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera
WHERE
"Carreras".carrera_modalidad = 'PRESENCIAL';

--TRIGGER DE CREACION DE NOTAS


CREATE OR REPLACE FUNCTION migrar_notas()
RETURNS TRIGGER AS $migrar_notas$
begin

    INSERT INTO "Notas"(nota_valor,id_almn_curso,id_tipo_nota)
    VALUES(0.0, new.id_almn_curso, 5);
    INSERT INTO "Notas"(nota_valor,id_almn_curso,id_tipo_nota)
    VALUES(0.0, new.id_almn_curso, 4);
    INSERT INTO "Notas"(nota_valor,id_almn_curso,id_tipo_nota)
    VALUES(0.0, new.id_almn_curso, 6);
    INSERT INTO "Notas"(nota_valor,id_almn_curso,id_tipo_nota)
    VALUES(0.0, new.id_almn_curso, 7);
    INSERT INTO "Notas"(nota_valor,id_almn_curso,id_tipo_nota)
    VALUES(0.0, new.id_almn_curso, 8);
    INSERT INTO "Notas"(nota_valor,id_almn_curso,id_tipo_nota)
    VALUES(0.0, new.id_almn_curso, 9);
    RETURN NEW;
end;
$migrar_notas$ LANGUAGE plpgsql;

CREATE TRIGGER migracion_notas
AFTER INSERT ON "AlumnoCurso2"
FOR EACH ROW
EXECUTE PROCEDURE migrar_notas();


/*
    BORRAR NOTAS ERRONEAS
*/

--BUSCAR NOTAS
SELECT
"public"."Notas".nota_valor,
"public"."TipoDeNota".tipo_nota_nombre,
"public"."Notas".id_nota,
"public"."PeriodoLectivo".prd_lectivo_nombre,
"public"."Carreras".carrera_nombre
FROM
"public"."PeriodoLectivo"
INNER JOIN "public"."Cursos" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."AlumnoCurso" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."Notas" ON "public"."Notas".id_almn_curso = "public"."AlumnoCurso".id_almn_curso
INNER JOIN "public"."TipoDeNota" ON "public"."Notas".id_tipo_nota = "public"."TipoDeNota".id_tipo_nota
INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera
WHERE
"public"."PeriodoLectivo".id_prd_lectivo IN (1,2,3,5,6,7,10,11,,13,14,15,17,18,19,20);


--ELIMINAR NOTAS


DELETE 
FROM
	"Notas" 
WHERE
"Notas".id_nota IN (
SELECT
"public"."Notas".id_nota
FROM
"public"."PeriodoLectivo"
INNER JOIN "public"."Cursos" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."AlumnoCurso" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."Notas" ON "public"."Notas".id_almn_curso = "public"."AlumnoCurso".id_almn_curso
INNER JOIN "public"."TipoDeNota" ON "public"."Notas".id_tipo_nota = "public"."TipoDeNota".id_tipo_nota
INNER JOIN "public"."Carreras" ON "public"."PeriodoLectivo".id_carrera = "public"."Carreras".id_carrera
WHERE
"public"."PeriodoLectivo".id_prd_lectivo IN (1,2,3,5,6,7,10,11,13,14,15,17,18,19,20)
);
--IDs: 1,2,3,5,6,7,10,11,13,14,15,17,18,19,20
/*
    VERIFICAR CANTIDAD DE NOTAS
    PORPERIODO LECTIVO
*/

SELECT
"count"(*)
FROM
"public"."PeriodoLectivo"
INNER JOIN "public"."Cursos" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."AlumnoCurso" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."Notas" ON "public"."Notas".id_almn_curso = "public"."AlumnoCurso".id_almn_curso
WHERE
"PeriodoLectivo".id_prd_lectivo = 


/*
VERIFICA LAS NOTAS SEGUN LOS PARAMETROS
*/
SELECT
"count"(*)
FROM
"public"."AlumnoCurso"
INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."Jornadas" ON "public"."Cursos".id_jornada = "public"."Jornadas".id_jornada
INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia
INNER JOIN "public"."Alumnos" ON "public"."AlumnoCurso".id_alumno = "public"."Alumnos".id_alumno
INNER JOIN "public"."Personas" ON "public"."Alumnos".id_persona = "public"."Personas".id_persona
INNER JOIN "public"."Notas" ON "public"."Notas".id_almn_curso = "public"."AlumnoCurso".id_almn_curso
WHERE
"public"."Cursos".id_docente = 88 AND
"public"."PeriodoLectivo".id_prd_lectivo = 26 AND
"public"."Cursos".curso_nombre = 'M2A' AND
"public"."Materias".materia_nombre = 'DEPORTE DE INICIACIÓN: TEORÍA Y METODOLOGÍA'


/*
VERIFICA LA CANTIDAD DE ALUMNOS DE UN CURSO
*/
SELECT
"count"(*)
FROM
"public"."AlumnoCurso"
INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."Jornadas" ON "public"."Cursos".id_jornada = "public"."Jornadas".id_jornada
INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia
INNER JOIN "public"."Alumnos" ON "public"."AlumnoCurso".id_alumno = "public"."Alumnos".id_alumno
INNER JOIN "public"."Personas" ON "public"."Alumnos".id_persona = "public"."Personas".id_persona
WHERE
"public"."Cursos".id_docente = 88 AND
"public"."PeriodoLectivo".id_prd_lectivo = 26 AND
"public"."Cursos".curso_nombre = 'M2A' AND
"public"."Materias".materia_nombre = 'DEPORTE DE INICIACIÓN: TEORÍA Y METODOLOGÍA'


/*
    CREA LAS NOTAS FALTANTES DE UN CURSO 
*/

INSERT INTO "Notas" ( id_almn_curso, id_tipo_nota ) SELECT
"AlumnoCurso".id_almn_curso,
"TipoDeNota".id_tipo_nota 
FROM
	"public"."AlumnoCurso"
	INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
	INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
	INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia
	FULL JOIN "TipoDeNota" ON "TipoDeNota".id_prd_lectivo = 26 
WHERE
	"public"."Cursos".id_docente = 88 
	AND "public"."PeriodoLectivo".id_prd_lectivo = 26 
	AND "public"."Cursos".curso_nombre = 'M2A' 
	AND "public"."Materias".materia_nombre = 'DEPORTE DE INICIACIÓN: TEORÍA Y METODOLOGÍA' 
	AND "TipoDeNota".tipo_nota_nombre != 'NOTA FINAL';


/*
    CREA LAS NOTAS FALTANTES DE TODO UN PERIODO SIEMPRE Y CUANDO ESTE EN 0
*/

INSERT INTO "Notas" ( id_almn_curso, id_tipo_nota ) SELECT
"AlumnoCurso".id_almn_curso,
"TipoDeNota".id_tipo_nota 
FROM
	"AlumnoCurso"
	INNER JOIN "Cursos" ON "Cursos".id_curso = "AlumnoCurso".id_curso
	INNER JOIN "PeriodoLectivo" ON "PeriodoLectivo".id_prd_lectivo = "Cursos".id_prd_lectivo
	INNER JOIN "Materias" ON "Materias".id_materia = "Cursos".id_materia
	FULL JOIN "TipoDeNota" ON "TipoDeNota".id_prd_lectivo = 26 
WHERE
	0 = ( SELECT "count" ( * ) FROM "Notas" WHERE "Notas".id_almn_curso = "AlumnoCurso".id_almn_curso ) 
	AND "PeriodoLectivo".id_prd_lectivo = 26 
	AND "TipoDeNota".tipo_nota_nombre != 'NOTA FINAL';