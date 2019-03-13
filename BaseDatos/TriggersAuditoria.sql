--Funcion en la que guardaremos en la tabla usuarios
--Tabla personas
CREATE OR REPLACE FUNCTION historial_personas()
RETURNS TRIGGER AS $historial_personas$
BEGIN
	INSERT INTO public."HistorialUsuarios" (
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_persona);
	RETURN NEW;
END;
$historial_personas$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_personas
BEFORE INSERT OR UPDATE
ON public."Personas" FOR EACH ROW
EXECUTE PROCEDURE historial_personas();

--Tabla alumnos
CREATE OR REPLACE FUNCTION historial_alumnos()
RETURNS TRIGGER AS $historial_alumnos$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_alumno);
	RETURN NEW;
END;
$historial_alumnos$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_alumnos
BEFORE INSERT OR UPDATE
ON public."Alumnos" FOR EACH ROW
EXECUTE PROCEDURE historial_alumnos();

--Tabla docentes
CREATE OR REPLACE FUNCTION historial_docentes()
RETURNS TRIGGER AS $historial_docentes$
BEGIN
	INSERT INTO public."Docentes"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_docente);
		RETURN NEW;
END;
$historial_docentes$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_docentes
BEFORE INSERT OR UPDATE
ON public."Docente" FOR EACH ROW
EXECUTE PROCEDURE historial_docentes(); 
