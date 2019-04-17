
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

CREATE TRIGGER actualizar_usuarios
AFTER INSERT OR UPDATE
ON public."Usuarios" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_personas
AFTER INSERT OR UPDATE
ON public."Personas" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();


CREATE TRIGGER actualizar_AlumnoCurso
AFTER INSERT OR UPDATE
ON public."AlumnoCurso" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_Alumnos
AFTER INSERT OR UPDATE
ON public."Alumnos" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_Cursos
AFTER INSERT OR UPDATE
ON public."Cursos" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_periodoLectivo
AFTER INSERT OR UPDATE
ON public."PeriodoLectivo" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_ingresonotas
AFTER INSERT OR UPDATE
ON public."IngresoNotas" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_materias
AFTER INSERT OR UPDATE
ON public."Materias" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_docentes
AFTER INSERT OR UPDATE
ON public."Docentes" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_periodoingresonotas
AFTER INSERT OR UPDATE
ON public."PeriodoIngresoNotas" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_tipodenota
AFTER INSERT OR UPDATE
ON public."TipoDeNota" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();
 
 CREATE TRIGGER actualizar_mMallaAlumno
AFTER INSERT OR UPDATE
ON public."MallaAlumno" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vistas();

--TRIGGER ELIMINACION DE ROLES

CREATE OR REPLACE FUNCTION roles_elim()
RETURNS TRIGGER AS $roles_elim$
BEGIN
	IF new.rol_estado = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_rol);
	ELSE 
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_rol);
	END IF;
	RETURN NEW;
END;
$roles_elim$ LANGUAGE plpgsql;

CREATE TRIGGER Elimina_Roles
AFTER UPDATE OF rol_estado
ON public."Roles" FOR EACH ROW
EXECUTE PROCEDURE roles_elim();