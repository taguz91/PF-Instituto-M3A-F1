/*
	ESTE TRIGGER HACE QUE CADA QUE SE REGISTRE UNA FICHA SE GENEREN LAS SECCIONES DE LA MISMA
*/

CREATE OR REPLACE FUNCTION registro_ficha_salud () RETURNS TRIGGER AS $registro_ficha_salud$ BEGIN
		INSERT INTO "SeccionFS" ( seccion_nombre_id, ficha_id )
	VALUES
		( 1, NEW.id ),
		( 2, NEW.id ),
		( 3, NEW.id ),
		( 4, NEW.id ),
		( 5, NEW.id ),
		( 6, NEW.id ),
		( 7, NEW.id ),
		( 8, NEW.id ),
		( 9, NEW.id ),
		( 10, NEW.id ),
		( 11, NEW.id );
	RETURN NEW; 
	END;
$registro_ficha_salud$ LANGUAGE plpgsql;

CREATE TRIGGER ingreso_ficha_salud_secciones AFTER INSERT ON PUBLIC."FichaSalud" FOR EACH ROW
EXECUTE PROCEDURE registro_ficha_salud ();

/*
============================================================================================================================================================
*/

/*
		ESTE TRIGGER CREA LAS SECCIONES CON SUS PREGUNTAS
*/

CREATE 
	OR REPLACE FUNCTION registro_preguntas_f_salud () RETURNS TRIGGER AS $registro_preguntas_f_salud$
	 BEGIN

		INSERT INTO "DetalleRespuesta" ( pregunta_id, seccion_id, respuesta ) SELECT
		"Pregunta"."id" AS "pregunta_id",
		NEW.id AS "seccion_id", 
		'' AS respuesta
		FROM
			"Pregunta" 
		WHERE
			"Pregunta".secc_nom_id = NEW.seccion_nombre_id;


	RETURN NEW;
	
END;
$registro_preguntas_f_salud$ LANGUAGE plpgsql;



CREATE TRIGGER registro_preguntas_f_salud_tri AFTER INSERT ON PUBLIC."SeccionFS" FOR EACH ROW
EXECUTE PROCEDURE registro_preguntas_f_salud ();