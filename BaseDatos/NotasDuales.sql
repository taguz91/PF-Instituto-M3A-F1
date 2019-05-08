
CREATE TABLE "NotasDuales"(
    id_notas_duales serial NOT NULL PRIMARY KEY,
    notas_pti NUMERIC (6,2),
    notas_estado_pti VARCHAR(20) DEFAULT 'REPROBADO',
    notas_t_empresarial NUMERIC (6,2),
    notas_t_academico NUMERIC (6,2),
    notas_total_practica NUMERIC (6,2),
    notas_estado_practica NUMERIC (6,2),

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

iptraf <--