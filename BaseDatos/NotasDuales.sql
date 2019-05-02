CREATE TABLE "PTI"
(
    id_pti serial NOT NULL PRIMARY KEY,
    pti_nota NUMERIC(6,2) DEFAULT 0,
    pti_estado VARCHAR(30) DEFAULT "REPROBADO"
);


CREATE TABLE "DetallePTI"
(
    id_almn_pti serial NOT NULL PRIMARY KEY,
    id_almn_curso INTEGER,
    id_pti INTEGER
);




CREATE TABLE "DetalleFasePractica"(
    id_detalle_f_prac serial NOT NULL PRIMARY KEY,
    id_almn_curso INTEGER,
    id_fase_practica INTEGER

);

CREATE TABLE "FasePractica"(
    id_fase_practica serial NOT NULL PRIMARY KEY,
    fase_prac_nota_final INTEGER DEFAULT 0,
    fase_pac_estado VARCHAR(20) DEFAULT "REPROBADO"
);


CREATE TABLE "NotasPracticas"(
    id_nota_prac serial NOT NULL PRIMARY KEY,
    nota_prac_valor NUMERIC(6,2) DEFAULT 0
    id_fase_practica INTEGER,
    id_tipo_nota INTEGER
);



ALTER TABLE "detallePTI" ADD CONSTRAINT "detallePTI__PTI_fk"
FOREIGN KEY (id_pti) REFERENCES "PTI"(id_pti) ON
DELETE CASCADE ON
UPDATE CASCADE;


ALTER TABLE "detallePTI" ADD CONSTRAINT "detallePTI__AlumnoCurso_fk"
FOREIGN KEY (id_almn_curso) REFERENCES "AlumnoCurso"(id_almn_curso) ON
DELETE CASCADE ON
UPDATE CASCADE;