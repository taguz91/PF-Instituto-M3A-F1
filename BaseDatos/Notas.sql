CREATE TABLE "AlumnoCurso2"(

    "id_almn_curso" SERIAL NOT NULL PRIMARY KEY,
    "id_alumno" INTEGER NOT NULL,
    "id_curso" INTEGER NOT NULL,
    "almn_curso_asistencia" VARCHAR(30) DEFAULT 'Asiste',
    "almn_curso_estado" VARCHAR(30) DEFAULT 'Reprobado',
    "almn_curso_num_faltas" INTEGER DEFAULT '0',
	"almn_curso_activo" BOOLEAN DEFAULT 'true'

)WITH(OIDS = FALSE);

CREATE TABLE "Notas"(
    "id_nota" SERIAL NOT NULL PRIMARY KEY,
    "nota_valor" NUMERIC(6,2),
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


--MIGRACION DE NOTAS
INSERT INTO "AlumnoCurso2"(id_alumno, id_curso, almn_curso_asistencia, almn_curso_estado, almn_curso_num_faltas) 
SELECT id_alumno, id_curso, almn_curso_asistencia, almn_curso_estado, almn_curso_num_faltas
FROM "AlumnoCurso";

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
    INSERT INTO "Notas"(nota_valor,id_almn_curso,id_tipo_nota) 
    VALUES(0.0, new.id_almn_curso, 10);

    RETURN NEW;
end;
$migrar_notas$ LANGUAGE plpgsql;

CREATE TRIGGER migracion_notas
AFTER INSERT ON "AlumnoCurso2"
FOR EACH ROW
EXECUTE PROCEDURE migrar_notas();