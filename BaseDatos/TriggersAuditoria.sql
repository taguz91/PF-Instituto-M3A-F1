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
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_docente);
		RETURN NEW;
END;
$historial_docentes$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_docentes
BEFORE INSERT OR UPDATE
ON public."Docentes" FOR EACH ROW
EXECUTE PROCEDURE historial_docentes();

--Tabla lugares
CREATE OR REPLACE FUNCTION historial_lugares()
RETURNS TRIGGER AS $historial_lugares$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_lugar);
		RETURN NEW;
END;
$historial_lugares$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_lugares
BEFORE INSERT OR UPDATE
ON public."Lugares" FOR EACH ROW
EXECUTE PROCEDURE historial_lugares();

--Tabla detalle jornada
CREATE OR REPLACE FUNCTION historial_detalle_jornada()
RETURNS TRIGGER AS $historial_detalle_jornada$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_detalle_jornada);
		RETURN NEW;
END;
$historial_detalle_jornada$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_detalle_jornada
BEFORE INSERT OR UPDATE
ON public."DetalleJornada" FOR EACH ROW
EXECUTE PROCEDURE historial_detalle_jornada();

--Tabla cursos
CREATE OR REPLACE FUNCTION historial_cursos()
RETURNS TRIGGER AS $historial_cursos$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_curso);
		RETURN NEW;
END;
$historial_cursos$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_cursos
BEFORE INSERT OR UPDATE
ON public."Cursos" FOR EACH ROW
EXECUTE PROCEDURE historial_cursos();

--Tabla Sesion clase
CREATE OR REPLACE FUNCTION historial_sesion_clase()
RETURNS TRIGGER AS $historial_sesion_clase$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_sesion);
		RETURN NEW;
END;
$historial_sesion_clase$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_sesion_clase
BEFORE INSERT OR UPDATE
ON public."SesionClase" FOR EACH ROW
EXECUTE PROCEDURE historial_sesion_clase();

--Tabla Jornadas

CREATE OR REPLACE FUNCTION historial_jornadas()
RETURNS TRIGGER AS $historial_jornadas$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_jornada);
		RETURN NEW;
END;
$historial_jornadas$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_sesion_clase
BEFORE INSERT OR UPDATE
ON public."Jornadas" FOR EACH ROW
EXECUTE PROCEDURE historial_jornadas();
