


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


CREATE TRIGGER Elimina_Roles
AFTER UPDATE OF rol_estado
ON public."Roles" FOR EACH ROW
EXECUTE PROCEDURE roles_elim();

--trigger para grabar en el historial 

CREATE TRIGGER up_date_usuarios
AFTER UPDATE OF usu_estado
ON public."Usuarios" FOR EACH ROW
 EXECUTE PROCEDURE actualiza_usuarios();