--No se realizaron triggers de
--	UnidadSilabo
--	EvaluacionSilabo
--	TipoActividad
-- 	RolesDelUsuario
--	Roles
--	AccesosDelRol
-- 	Accesos

--Jornada docente
--Si se elimina se guardara en la base de datos en observacion
CREATE OR REPLACE FUNCTION jornada_docente_elim()
RETURNS TRIGGER AS $jornada_docente_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_jornada_docente,
			old.id_jornada || '%' || old.id_docente || '%' || old.id_prd_lectivo);
		RETURN OLD;
END;
$jornada_docente_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_jornada_docente_elim
BEFORE DELETE
ON public."JornadaDocente" FOR EACH ROW
EXECUTE PROCEDURE jornada_docente_elim();

--SesionClase
--Si se elimina una sesion de clase se  guardara en observaciones
CREATE OR REPLACE FUNCTION sesion_clase_elim()
RETURNS TRIGGER AS $sesion_clase_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_sesion,
			old.id_curso || '%' || old.dia_sesion || '%' ||
			old.hora_inicio_sesion || '%' || old.hora_fin_sesion);
		RETURN OLD;
END;
$sesion_clase_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_sesion_clase_elim
BEFORE DELETE
ON public."SesionClase" FOR EACH ROW
EXECUTE PROCEDURE sesion_clase_elim();

--DetalleJornada
--Cuando se elimine una jornada se le guardara en el campo observacion
CREATE OR REPLACE FUNCTION detalle_jrd_elim()
RETURNS TRIGGER AS $detalle_jrd_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_detalle_jornada,
			old.id_jornada || '%' || old.hora_inicio_jornada || '%' ||
			old.hora_fin_jornada || '%' || old.dia_inicio_jornada|| '%' ||
			old.dia_fin_jornada);
		RETURN OLD;
END;
$detalle_jrd_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_detalle_jornada_elim
BEFORE DELETE
ON public."DetalleJornada" FOR EACH ROW
EXECUTE PROCEDURE detalle_jrd_elim();

--Silabo
--Trigger no funcional
/*CREATE OR REPLACE FUNCTION silabo_elim()
RETURNS TRIGGER AS $silabo_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,
		historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_silabo,
			old.id_materia);
		RETURN NEW;
END;
$silabo_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_silabo_elim
BEFORE DELETE
ON public."Silabo" FOR EACH ROW
EXECUTE PROCEDURE silabo_elim();
*/

--PeriodoIngresoNotas
CREATE OR REPLACE FUNCTION prd_ingreso_nt_elim()
RETURNS TRIGGER AS $prd_ingreso_nt_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_perd_ingr_notas,
			old.perd_notas_fecha_inicio || '%' || old.perd_notas_fecha_cierre
			|| '%' || old.perd_notas_estado || '%' || old.id_prd_lectivo
			|| '%' || old.id_tipo_nota);
		RETURN OLD;
END;
$prd_ingreso_nt_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_prd_ingreso_nt_elim
BEFORE DELETE
ON public."PeriodoIngresoNotas" FOR EACH ROW
EXECUTE PROCEDURE prd_ingreso_nt_elim();

--TipoDeNota
CREATE OR REPLACE FUNCTION tipo_nota_elim()
RETURNS TRIGGER AS $tipo_nota_elim$
BEGIN
	IF new.tipo_nota_estado = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_tipo_nota);
	END IF;
	RETURN NEW;
END;
$tipo_nota_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_tipo_nota_elim
BEFORE UPDATE OF tipo_nota_estado
ON public."TipoDeNota" FOR EACH ROW
EXECUTE PROCEDURE tipo_nota_elim();

--EjesFormacion
CREATE OR REPLACE FUNCTION eje_formacion_elim()
RETURNS TRIGGER AS $eje_formacion_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_eje,
			old.id_carrera || '%' || old.eje_codigo
			|| '%' || old.eje_nombre);
		RETURN OLD;
END;
$eje_formacion_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_eje_formacion_elim
BEFORE DELETE
ON public."EjesFormacion" FOR EACH ROW
EXECUTE PROCEDURE eje_formacion_elim();

--Usuarios
CREATE OR REPLACE FUNCTION usuario_elim()
RETURNS TRIGGER AS $usuario_elim$
BEGIN
	IF new.usu_estado = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_usuario);
	END IF;
	RETURN NEW;
END;
$usuario_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_usuario_elim
BEFORE UPDATE OF usu_estado
ON public."Usuarios" FOR EACH ROW
EXECUTE PROCEDURE usuario_elim();

--Triggers grupo andres

--ReferenciaSilabo
CREATE OR REPLACE FUNCTION referencia_silabo_elim()
RETURNS TRIGGER AS $referencia_silabo_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_referencia_silabo,
			old.id_referencia || '%' || old.id_referencia);
		RETURN OLD;
END;
$referencia_silabo_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_referencia_silabo_elim
BEFORE DELETE
ON public."ReferenciaSilabo" FOR EACH ROW
EXECUTE PROCEDURE referencia_silabo_elim();
