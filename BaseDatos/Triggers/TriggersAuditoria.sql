--Tabla DetalleJornada 1
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

--Tabla cursos 2
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

--Tabla SesionClase 3
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

--Tabla Jornadas 4

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

CREATE TRIGGER auditoria_jornadas
BEFORE INSERT OR UPDATE
ON public."Jornadas" FOR EACH ROW
EXECUTE PROCEDURE historial_jornadas();

--Tabla JornadaDocente 5

CREATE OR REPLACE FUNCTION historial_jornada_docente()
RETURNS TRIGGER AS $historial_jornada_docente$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_jornada_docente);
		RETURN NEW;
END;
$historial_jornada_docente$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_jornada_docente
BEFORE INSERT OR UPDATE
ON public."JornadaDocente" FOR EACH ROW
EXECUTE PROCEDURE historial_jornada_docente();

-- Tabla periodo lectivo 6
CREATE OR REPLACE FUNCTION historial_periodo_lectivo()
RETURNS TRIGGER AS $historial_periodo_lectivo$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_prd_lectivo);
		RETURN NEW;
END;
$historial_periodo_lectivo$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_periodo_lectivo
BEFORE INSERT OR UPDATE
ON public."PeriodoLectivo" FOR EACH ROW
EXECUTE PROCEDURE historial_periodo_lectivo();

--Tabla docentes 7
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

--Tabla carreras 8
CREATE OR REPLACE FUNCTION historial_carreras()
RETURNS TRIGGER AS $historial_carreras$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_carrera);
		RETURN NEW;
END;
$historial_carreras$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_carreras
BEFORE INSERT OR UPDATE
ON public."Carreras" FOR EACH ROW
EXECUTE PROCEDURE historial_carreras();

--Ejes formacion 9
CREATE OR REPLACE FUNCTION historial_ejes_formacion()
RETURNS TRIGGER AS $historial_ejes_formacion$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_eje);
		RETURN NEW;
END;
$historial_ejes_formacion$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_ejes_formacion
BEFORE INSERT OR UPDATE
ON public."EjesFormacion" FOR EACH ROW
EXECUTE PROCEDURE historial_ejes_formacion();

--Tabla personas 10
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

--Tabla lugares 11
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

--Tabla alumnos 12
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

--Tabla AlumnosCarrera 13
CREATE OR REPLACE FUNCTION historial_alumnos_carrera()
RETURNS TRIGGER AS $historial_alumnos_carrera$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_almn_carrera);
	RETURN NEW;
END;
$historial_alumnos_carrera$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_alumnos_carrera
BEFORE INSERT OR UPDATE
ON public."AlumnosCarrera" FOR EACH ROW
EXECUTE PROCEDURE historial_alumnos_carrera();

--Tabla MallaAlumno 14
CREATE OR REPLACE FUNCTION historial_malla_alumno()
RETURNS TRIGGER AS $historial_malla_alumno$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_malla_alumno);
	RETURN NEW;
END;
$historial_malla_alumno$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_malla_alumno
BEFORE INSERT OR UPDATE
ON public."MallaAlumno" FOR EACH ROW
EXECUTE PROCEDURE historial_malla_alumno();

--Tabla SectorEconomico 15
CREATE OR REPLACE FUNCTION historial_sector_economico()
RETURNS TRIGGER AS $historial_sector_economico$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_sec_economico);
	RETURN NEW;
END;
$historial_sector_economico$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_sector_economico
BEFORE INSERT OR UPDATE
ON public."SectorEconomico" FOR EACH ROW
EXECUTE PROCEDURE historial_sector_economico();

--Tabla AlumnoCurso 16
CREATE OR REPLACE FUNCTION historial_alumno_curso()
RETURNS TRIGGER AS $historial_alumno_curso$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_almn_curso);
	RETURN NEW;
END;
$historial_alumno_curso$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_alumno_curso
BEFORE INSERT OR UPDATE
ON public."AlumnoCurso" FOR EACH ROW
EXECUTE PROCEDURE historial_alumno_curso();

--Tabla DocentesMateria 17
CREATE OR REPLACE FUNCTION historial_docente_materia()
RETURNS TRIGGER AS $historial_docente_materia$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_docente_mat);
	RETURN NEW;
END;
$historial_docente_materia$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_docente_materia
BEFORE INSERT OR UPDATE
ON public."DocentesMateria" FOR EACH ROW
EXECUTE PROCEDURE historial_docente_materia();

--Tabla MateriaRequisito 18
CREATE OR REPLACE FUNCTION historial_materia_requisito()
RETURNS TRIGGER AS $historial_materia_requisito$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_requisito);
	RETURN NEW;
END;
$historial_materia_requisito$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_materia_requisito
BEFORE INSERT OR UPDATE
ON public."MateriaRequisitos" FOR EACH ROW
EXECUTE PROCEDURE historial_materia_requisito();

--Tabla PeriodoIngresoNotas 19
CREATE OR REPLACE FUNCTION historial_periodo_ingreso_notas()
RETURNS TRIGGER AS $historial_periodo_ingreso_notas$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_perd_ingr_notas);
	RETURN NEW;
END;
$historial_periodo_ingreso_notas$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_periodo_ingreso_notas
BEFORE INSERT OR UPDATE
ON public."PeriodoIngresoNotas" FOR EACH ROW
EXECUTE PROCEDURE historial_periodo_ingreso_notas();

--Tabla TipoDeNota 20
CREATE OR REPLACE FUNCTION historial_tipo_nota()
RETURNS TRIGGER AS $historial_tipo_nota$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_tipo_nota);
	RETURN NEW;
END;
$historial_tipo_nota$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_tipo_nota
BEFORE INSERT OR UPDATE
ON public."TipoDeNota" FOR EACH ROW
EXECUTE PROCEDURE historial_tipo_nota();

--Tabla EvaluacionSilabo 21
CREATE OR REPLACE FUNCTION historial_evaluacion_silabo()
RETURNS TRIGGER AS $historial_evaluacion_silabo$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_evaluacion);
	RETURN NEW;
END;
$historial_evaluacion_silabo$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_evaluacion_silabo
BEFORE INSERT OR UPDATE
ON public."EvaluacionSilabo" FOR EACH ROW
EXECUTE PROCEDURE historial_evaluacion_silabo();

--Tabla UnidadSilabo 22
CREATE OR REPLACE FUNCTION historial_unidad_silabo()
RETURNS TRIGGER AS $historial_unidad_silabo$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_unidad);
	RETURN NEW;
END;
$historial_unidad_silabo$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_evaluacion_silabo
BEFORE INSERT OR UPDATE
ON public."UnidadSilabo" FOR EACH ROW
EXECUTE PROCEDURE historial_unidad_silabo();

--Tabla TipoActividad 23
CREATE OR REPLACE FUNCTION historial_tipo_actividad()
RETURNS TRIGGER AS $historial_tipo_actividad$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_tipo_actividad);
	RETURN NEW;
END;
$historial_tipo_actividad$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_tipo_actividad
BEFORE INSERT OR UPDATE
ON public."TipoActividad" FOR EACH ROW
EXECUTE PROCEDURE historial_tipo_actividad();

--Tabla Silabo 24
CREATE OR REPLACE FUNCTION historial_silabo()
RETURNS TRIGGER AS $historial_silabo$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_silabo);
	RETURN NEW;
END;
$historial_silabo$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_silabo
BEFORE INSERT OR UPDATE
ON public."Silabo" FOR EACH ROW
EXECUTE PROCEDURE historial_silabo();

--Tabla ReferenciaSilabo 25
CREATE OR REPLACE FUNCTION historial_referencia_silabo()
RETURNS TRIGGER AS $historial_referencia_silabo$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_referencia_silabo);
	RETURN NEW;
END;
$historial_referencia_silabo$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_referencia_silabo
BEFORE INSERT OR UPDATE
ON public."ReferenciaSilabo" FOR EACH ROW
EXECUTE PROCEDURE historial_referencia_silabo();

--Tabla Referencias 26
CREATE OR REPLACE FUNCTION historial_referencias()
RETURNS TRIGGER AS $historial_referencias$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_referencia);
	RETURN NEW;
END;
$historial_referencias$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_referencias
BEFORE INSERT OR UPDATE
ON public."Referencias" FOR EACH ROW
EXECUTE PROCEDURE historial_referencias();

--Tabla RolesDelUsuario 27
CREATE OR REPLACE FUNCTION historial_roles_usuario()
RETURNS TRIGGER AS $historial_roles_usuario$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_roles_usuarios);
	RETURN NEW;
END;
$historial_roles_usuario$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_roles_usuario
BEFORE INSERT OR UPDATE
ON public."RolesDelUsuario" FOR EACH ROW
EXECUTE PROCEDURE historial_roles_usuario();

--Tabla Roles 28
CREATE OR REPLACE FUNCTION historial_roles()
RETURNS TRIGGER AS $historial_roles$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_rol);
	RETURN NEW;
END;
$historial_roles$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_roles
BEFORE INSERT OR UPDATE
ON public."Roles" FOR EACH ROW
EXECUTE PROCEDURE historial_roles();

--Tabla AccesosDelRol 29
CREATE OR REPLACE FUNCTION historial_accesos_rol()
RETURNS TRIGGER AS $historial_accesos_rol$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_acceso_del_rol);
	RETURN NEW;
END;
$historial_accesos_rol$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_accesos_rol
BEFORE INSERT OR UPDATE
ON public."AccesosDelRol" FOR EACH ROW
EXECUTE PROCEDURE historial_accesos_rol();

--Tabla Accesos 30
CREATE OR REPLACE FUNCTION historial_accesos()
RETURNS TRIGGER AS $historial_accesos$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_acceso);
	RETURN NEW;
END;
$historial_accesos$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_accesos
BEFORE INSERT OR UPDATE
ON public."Accesos" FOR EACH ROW
EXECUTE PROCEDURE historial_accesos();

--Tabla Materias 31
CREATE OR REPLACE FUNCTION historial_materias()
RETURNS TRIGGER AS $historial_materias$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_materia);
	RETURN NEW;
END;
$historial_materias$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_materias
BEFORE INSERT OR UPDATE
ON public."Materias" FOR EACH ROW
EXECUTE PROCEDURE historial_materias();

-- Tabla Usuarios
CREATE OR REPLACE FUNCTION historial_usuarios()
RETURNS TRIGGER AS $historial_usuarios$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
	  historial_nombre_tabla, historial_pk_tabla)
	VALUES(USER, now(), TG_OP, TG_TABLE_NAME, new.id_usuario);
	RETURN NEW;
END;
$historial_usuarios$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_usuarios
BEFORE INSERT OR UPDATE
ON public."Usuarios" FOR EACH ROW
EXECUTE PROCEDURE historial_usuarios();
