--Funcion en la que guardaremos en la tabla usuarios
CREATE OR REPLACE FUNCTION historial_acciones() RETURNS TRIGGER 
AS $historial_acciones$
BEGIN 
	INSERT INTO public."HistorialUsuarios" (
		usu_username, historial_fecha, historial_tipo_accion, 
		historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_persona);
	RETURN NEW;
END; 
$historial_acciones$ LANGUAGE plpgsql;  


CREATE TRIGGER personas_historial
BEFORE INSERT OR UPDATE OR DELETE 
ON public."Personas" FOR EACH ROW 
EXECUTE PROCEDURE historial_acciones();
