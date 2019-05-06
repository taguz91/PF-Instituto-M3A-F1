
CREATE TABLE "NotasDuales"(
    id_notas_duales serial NOT NULL PRIMARY KEY,
    nota_pti numeric(6,2),
    estado_pti VARCHAR(20) DEFAULT 'REPROBADO',
    notas_t_empresarial numeric (6,2),
    notas_t_academico numeric (6,2),
    duales_practicas_estado numeric (6,2),

    id_prd_lectivo INTEGER

);

CREATE TABLE "DetalleDuales"(
    id_detale_duales serial NOT NULL PRIMARY KEY,
    id_almn_curso INTEGER,
    id_notas_duales INTEGER

);

ALTER TABLE "NotasDuales" ADD CONSTRAINT ""

0703630715=CARRANZA OCHOA ROBERTO  NATANIEL\t0995710017\trncarranza76@gmail.com\tTIEMPO COMPLETO