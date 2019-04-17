CREATE 
	OR REPLACE FUNCTION actualiza_ingrPeriodoNotas ( id_ingreso IN NUMERIC, campo IN VARCHAR, id_curso IN NUMERIC, estado BOOLEAN ) RETURNS SETOF RECORD AS $BODY$ DECLARE
BEGIN
	IF
		( campo = 'nota_primer_inteciclo' ) THEN
			UPDATE "IngresoNotas" 
			SET nota_primer_inteciclo = estado 
		WHERE
			id_ingreso_notas = id_ingreso;
		ELSE
		IF
			( campo = 'nota_examen_intecilo' ) THEN
				UPDATE "IngresoNotas" 
				SET nota_examen_intecilo = estado 
			WHERE
				id_ingreso_notas = id_ingreso;
			ELSE
			IF
				( campo = 'nota_segundo_inteciclo' ) THEN
					UPDATE "IngresoNotas" 
					SET nota_segundo_inteciclo = estado 
				WHERE
					id_ingreso_notas = id_ingreso;
				ELSE
				IF
					( campo = 'nota_examen_final' ) THEN
						UPDATE "IngresoNotas" 
						SET nota_examen_final = estado 
					WHERE
						id_ingreso_notas = id_ingreso;
					ELSE
					IF
						( campo = 'nota_examen_de_recuperacion' ) THEN
							UPDATE "IngresoNotas" 
							SET nota_examen_de_recuperacion = estado 
						WHERE
							id_ingreso_notas = id_ingreso;
						
					END IF;
					
				END IF;
				
			END IF;
			
		END IF;
		
	END IF;
	
END;
$BODY$ LANGUAGE plpgsql;