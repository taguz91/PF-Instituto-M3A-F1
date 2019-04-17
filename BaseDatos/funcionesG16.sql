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


--funcion actualizar vista

CREATE OR REPLACE FUNCTION actualizar_vistas()
RETURNS TRIGGER AS $actualizar_vistas$
BEGIN
 REFRESH MATERIALIZED VIEW "Usuarios_Persona";
 REFRESH MATERIALIZED VIEW "ViewAlumnoCurso";
 REFRESH MATERIALIZED VIEW "ViewCursosPermisosNotas";
 REFRESH MATERIALIZED VIEW "ViewPeriodoIngresoNotas";
 REFRESH MATERIALIZED VIEW "ViewDocentes";
 
 RETURN NEW;
END;
$actualizar_vistas$ LANGUAGE plpgsql;

--funcion eliminar roles


CREATE OR REPLACE FUNCTION roles_elim()
RETURNS TRIGGER AS $roles_elim$
BEGIN
	IF new.rol_estado = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip )
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_rol,inet_client_addr());
	ELSE 
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip )
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_rol,inet_client_addr());
	END IF;
	RETURN NEW;
END;
$roles_elim$ LANGUAGE plpgsql;


--funciones para guardar en la tabla historial

CREATE OR REPLACE FUNCTION actualiza_usuarios()
RETURNS TRIGGER AS $actualiza_usuarios$
BEGIN
	IF new.usu_estado = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip )
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_usuario,inet_client_addr());
	ELSE 
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip )
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_usuario,inet_client_addr());
	END IF;
	RETURN NEW;
END;
$actualiza_usuarios$ LANGUAGE plpgsql;