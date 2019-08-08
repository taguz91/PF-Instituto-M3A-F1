
CREATE TABLE "IngresoNotas"
(
    id serial NOT NULL PRIMARY KEY,
    fecha_inicio DATE,
    fecha_cierre DATE,
    fecha_cierre_extendido DATE,
    estado BOOLEAN DEFAULT FALSE,
    id_tipo_nota INTEGER,
    id_curso INTEGER
);

ALTER TABLE "IngresoNotas" ADD CONSTRAINT "IngresoNotas__TipoNotas__fk"
    FOREIGN KEY (id_tipo_nota) REFERENCES "TipoDeNota"(id_tipo_nota)
        ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "IngresoNotas" ADD CONSTRAINT "IngresoNotas__Cursos__fk"
    FOREIGN KEY (id_curso) REFERENCES "Cursos"(id_curso)
        ON UPDATE CASCADE ON DELETE CASCADE;


CREATE 
	OR REPLACE FUNCTION "agregarIngresosNotas" ( IN id_periodo INTEGER, IN fecha_inicio DATE, IN fecha_cierre DATE ) RETURNS VARCHAR AS $BODY$ BEGIN
		INSERT INTO "IngresoNotas" ( fecha_inicio, fecha_cierre, id_tipo_nota, id_curso ) SELECT
		fecha_inicio,
		fecha_cierre,
		"public"."TipoDeNota".id_tipo_nota,
		"public"."Cursos".id_curso 
	FROM
		"public"."TipoDeNota",
		"public"."Cursos" 
	WHERE
		"TipoDeNota".id_prd_lectivo = id_periodo 
		AND "Cursos".id_prd_lectivo = id_periodo 
		AND "TipoDeNota".tipo_nota_nombre NOT IN ( 'NOTA FINAL', 'NOTA INTERCICLO', 'TOTAL GESTION', 'SUBTOTAL FASE PRACTICA', 'NOTA FINAL TOTAL' );
	RETURN 'QUERY EJECUTADO';

END $BODY$ LANGUAGE'plpgsql' VOLATILE;