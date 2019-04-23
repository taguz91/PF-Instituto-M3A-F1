
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
