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
	OR REPLACE FUNCTION registro_preguntas_f_salud () RETURNS TRIGGER AS $registro_preguntas_f_salud$ BEGIN
	CASE
			NEW.seccion_nombre_id 
		
		WHEN 1 THEN
			INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 1, NEW.ID ),
			( 2, NEW.ID ),
			( 3, NEW.ID ),
			( 4, NEW.ID );
		
		WHEN 2 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 5, NEW.ID ),
			( 6, NEW.ID ),
			( 7, NEW.ID ),
			( 8, NEW.ID );
		
		WHEN 3 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 9, NEW.ID ),
			( 10, NEW.ID ),
			( 11, NEW.ID ),
			( 12, NEW.ID );
		
		WHEN 4 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 13, NEW.ID ),
			( 35, NEW.ID ),
			( 36, NEW.ID ),
			( 37, NEW.ID ),
			( 38, NEW.ID);
		
		WHEN 5 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 14, NEW.ID ),
			( 39, NEW.ID );
		
		WHEN 6 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 15, NEW.ID ),
			( 40, NEW.ID );
		
		WHEN 7 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 16, NEW.ID ),
			( 41, NEW.ID );
		
		WHEN 8 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 17, NEW.ID ),
			( 18, NEW.ID ),
			( 42, NEW.ID ),

			( 19, NEW.ID ),
			( 20, NEW.ID ),
			( 43, NEW.ID ),
			( 44, NEW.ID ),

			( 21, NEW.ID ),
			( 22, NEW.ID ),
			( 45, NEW.ID ),
			( 46, NEW.ID ),

			( 23, NEW.ID ),
			( 47, NEW.ID ),
			( 48, NEW.ID );
		
		WHEN 9 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 24, NEW.ID ),
			( 49, NEW.ID );
		
		WHEN 10 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 50, NEW.ID );
		
		WHEN 11 THEN
		INSERT INTO "DetalleResuesta" ( pregunta_id, seccion_id )
		VALUES
			( 25, NEW.ID ),
			( 51, NEW.ID ),

			( 26, NEW.ID ),
			( 52, NEW.ID ),
			( 33, NEW.ID ),

			( 28, NEW.ID),
			( 29, NEW.ID),
			( 30, NEW.ID),
			( 31, NEW.ID),
			( 32, NEW.ID);
		ELSE 
		END CASE;
	RETURN NEW;
	
END;
$registro_preguntas_f_salud$ LANGUAGE plpgsql;



CREATE TRIGGER registro_preguntas_f_salud_tri AFTER INSERT ON PUBLIC."Seccion" FOR EACH ROW
EXECUTE PROCEDURE registro_preguntas_f_salud ();