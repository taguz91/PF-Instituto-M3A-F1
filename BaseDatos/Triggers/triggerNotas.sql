
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


CREATE OR REPLACE FUNCTION "agregarIngresosNotas" 
( IN id_periodo INTEGER, IN id_tipo_de_nota INTEGER, IN fecha_inicio DATE, IN fecha_cierre DATE )
	RETURNS VARCHAR AS 
	$BODY$ 
	BEGIN
		INSERT INTO "IngresoNotas" ( fecha_inicio, fecha_cierre, id_tipo_nota, id_curso ) 
			SELECT
				fecha_inicio,
				fecha_cierre,
				id_tipo_de_nota,
				id_curso 
			FROM
				"Cursos" 
			WHERE
				"Cursos".id_prd_lectivo = id_periodo;
		
		RETURN 'QUERY EJECUTADO';

END $BODY$ LANGUAGE'plpgsql' VOLATILE;