CREATE TABLE "IngresoNotas"(
    id_ingreso_notas serial NOT NULL PRIMARY KEY,
    nota_primer_inteciclo BOOLEAN DEFAULT FALSE,
    nota_examen_intecilo BOOLEAN DEFAULT FALSE,
    nota_segundo_inteciclo BOOLEAN DEFAULT FALSE,
    nota_examen_final BOOLEAN DEFAULT FALSE,
    nota_examen_de_recuperacion BOOLEAN DEFAULT FALSE,

    id_curso INTEGER NOT NULL
);

ALTER TABLE "IngresoNotas" ADD CONSTRAINT "fk_cursos_ingreso_notas"
    FOREIGN KEY ("id_curso") REFERENCES "Cursos"("id_curso")
        ON DELETE CASCADE ON UPDATE CASCADE; 
