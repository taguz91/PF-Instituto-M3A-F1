--Materias 
--Cuando se edita el valor de activo se guarda en historial como eliminado. 
CREATE OR REPLACE FUNCTION historial_materia_elim()
RETURNS TRIGGER AS $historial_materia_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_materia);
		RETURN NEW;
END;
$historial_materia_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_materia_elim
BEFORE UPDATE OF materia_activa
ON public."Materias" FOR EACH ROW
EXECUTE PROCEDURE historial_materia_elim();

--Cursos 
--Si se elimina cursos se guardan los datos en observacion
CREATE OR REPLACE FUNCTION historial_curso_elim()
RETURNS TRIGGER AS $historial_curso_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_curso, 
			old.id_materia || '%' || old.id_prd_lectivo || '%' || 
			old.id_docente || '%' || old.id_jornada || '%' || 
			old.curso_nombre || '%' || old.curso_capacidad || '%' || 
			old._curso_permiso_ingreso_nt);
		RETURN NEW;
END;
$historial_curso_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_curso_elim
BEFORE DELETE 
ON public."Cursos" FOR EACH ROW
EXECUTE PROCEDURE historial_curso_elim();

--Docentes 
--Si se elimina un docente se deben eliminar muchas otras cosas mas 
CREATE OR REPLACE FUNCTION historial_docente_elim()
RETURNS TRIGGER AS $historial_docente_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_docente);
		RETURN NEW;
END;
$historial_docente_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_docente_elim
BEFORE UPDATE OF docente_activo
ON public."Docentes" FOR EACH ROW
EXECUTE PROCEDURE historial_docente_elim();

--Jornada docente 
--Si se elimina se guardara en la base de datos en observacion 
CREATE OR REPLACE FUNCTION historial_jornada_docente_elim()
RETURNS TRIGGER AS $historial_jornada_docente_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_curso, 
			old.id_jornada_docente || '%' || old.id_jornada || '%' || 
			old.id_docente || '%' || old.id_prd_lectivo);
		RETURN NEW;
END;
$historial_jornada_docente_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_jornada_docente_elim
BEFORE DELETE 
ON public."JornadaDocente" FOR EACH ROW
EXECUTE PROCEDURE historial_jornada_docente_elim();

--MateriaRequisitos
--Si se elimina una materia requsito se guardara 
--En historial observaciones
CREATE OR REPLACE FUNCTION historial_mt_requisito_elim()
RETURNS TRIGGER AS $historial_mt_requisito_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_requisito, 
			old.id_materia || '%' || old.id_materia_requisito || '%' || 
			old.tipo_requisito);
		RETURN NEW;
END;
$historial_mt_requisito_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_mt_requisito_elim
BEFORE DELETE 
ON public."MateriaRequisitos" FOR EACH ROW
EXECUTE PROCEDURE historial_mt_requisito_elim();

--SesionClase
--Si se elimina una sesion de clase se  guardara en observaciones
CREATE OR REPLACE FUNCTION historial_sesion_clase_elim()
RETURNS TRIGGER AS $historial_sesion_clase_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_sesion, 
			old.id_curso || '%' || old.dia_sesion || '%' || 
			old.hora_inicio_sesion || '%' || old.hora_fin_sesion);
		RETURN NEW;
END;
$historial_sesion_clase_elim$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_sesion_clase_elim
BEFORE DELETE 
ON public."SesionClase" FOR EACH ROW
EXECUTE PROCEDURE historial_sesion_clase_elim();