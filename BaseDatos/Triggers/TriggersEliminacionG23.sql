
--Materias
--Cuando se edita el valor de activo se guarda en historial como eliminado.
CREATE OR REPLACE FUNCTION materia_elim()
RETURNS TRIGGER AS $materia_elim$
BEGIN
	IF new.materia_activa = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_materia,inet_client_addr());
	ELSE
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_ip)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_materia,inet_client_addr());
	END IF;
	--Tambien eliminamos a lo que depende de esta materia
	UPDATE public."Cursos"
		SET curso_activo = new.materia_activa
		WHERE id_materia = old.id_materia;
	RETURN NEW;
END;
$materia_elim$ LANGUAGE plpgsql;

--Cursos
--Si se elimina cursos se guardan los datos en observacion
CREATE OR REPLACE FUNCTION curso_elim()
RETURNS TRIGGER AS $curso_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion, historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_curso,
			old.id_materia || '%' || old.id_prd_lectivo || '%' ||
			old.id_docente || '%' || old.id_jornada || '%' ||
			old.curso_nombre || '%' || old.curso_capacidad,inet_client_addr());
		RETURN OLD;
END;
$curso_elim$ LANGUAGE plpgsql;


--Eliminacion logica de un curso
CREATE OR REPLACE FUNCTION curso_elimlog()
RETURNS TRIGGER AS $curso_elimlog$
BEGIN
	IF new.curso_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla. historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_curso,inet_client_addr());
	 ELSE
	    INSERT INTO public."HistorialUsuarios"(
	    usu_username, historial_fecha, historial_tipo_accion,
	    historial_nombre_tabla, historial_pk_tabla, historial_ip)
	    VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_curso, inet_client_addr());
	END IF;
	UPDATE public."AlumnoCurso" 
	SET almn_curso_activo = new.curso_activo
	WHERE id_curso = old.id_curso;
	RETURN NEW;
END;
$curso_elimlog$ LANGUAGE plpgsql;


--Docentes
--Si se elimina un docente se deben eliminar muchas otras cosas mas
CREATE OR REPLACE FUNCTION docente_elimlog()
RETURNS TRIGGER AS $docente_elimlog$
BEGIN
	IF new.docente_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_docente,inet_client_addr());
	ELSE
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_docente,inet_client_addr());
	END IF;
	--Tambien ocultamos todo lo que tenga que ver con docnete
	UPDATE public."DocentesMateria"
		SET docente_mat_activo = new.docente_activo
		WHERE id_docente = old.id_docente;

	UPDATE public."Cursos"
		SET curso_activo = new.docente_activo
		WHERE id_docente = old.id_docente;

	RETURN NEW;
END;
$docente_elimlog$ LANGUAGE plpgsql;


--MateriaRequisitos
--Si se elimina una materia requsito se guardara
--En historial observaciones
CREATE OR REPLACE FUNCTION materia_requisito_elim()
RETURNS TRIGGER AS $materia_requisito_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_requisito,
			old.id_materia || '%' || old.id_materia_requisito || '%' ||
			old.tipo_requisito,inet_client_addr());
		RETURN OLD;
END;
$materia_requisito_elim$ LANGUAGE plpgsql;


--DocentesMateria
CREATE OR REPLACE FUNCTION docentes_materia_elimlog()
RETURNS TRIGGER AS $docentes_materia_elimlog$
BEGIN
	IF new.docente_mat_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_docente_mat,inet_client_addr());
	ELSE
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_docente_mat,inet_client_addr());
	END IF;
	RETURN NEW;
END;
$docentes_materia_elimlog$ LANGUAGE plpgsql;



--Carreras
CREATE OR REPLACE FUNCTION carrera_elimlog()
RETURNS TRIGGER AS $carrera_elimlog$
BEGIN
	IF new.carrera_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_carrera,inet_client_addr());
	ELSE
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_carrera,inet_client_addr());
	END IF;
	--Actualizamos todo en loq ue interviene la carrera
	UPDATE public."AlumnosCarrera"
		SET almn_carrera_activo = new.carrera_activo
		WHERE id_carrera = old.id_carrera;
	UPDATE public."PeriodoLectivo"
		SET prd_lectivo_activo = new.carrera_activo
		WHERE id_carrera = old.id_carrera;
	UPDATE public."Materias"
		SET materia_activa = new.carrera_activo
		WHERE id_carrera = old.id_carrera;
	RETURN NEW;
END;
$carrera_elimlog$ LANGUAGE plpgsql;



--PeriodoLectivo
CREATE OR REPLACE FUNCTION prd_lectivo_elim()
RETURNS TRIGGER AS $prd_lectivo_elim$
BEGIN
	IF new.prd_lectivo_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_prd_lectivo,inet_client_addr());
	ELSE
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_prd_lectivo,inet_client_addr());
	END IF;
	--Actualizamos tambien lo que depende de este periodo
	UPDATE public."Cursos"
		SET curso_activo = new.prd_lectivo_activo
		WHERE id_prd_lectivo = old.id_prd_lectivo;
	UPDATE public."RolesDocente"
		SET rol_activo = new.prd_lectivo_activo
		WHERE id_prd_lectivo = old.id_prd_lectivo;
	RETURN NEW;
END;
$prd_lectivo_elim$ LANGUAGE plpgsql;



--Personas
--Si se elimina una persona se eliminan docente o estudiante con ellos
--Unicamente el campo de activo
CREATE OR REPLACE FUNCTION persona_elimlog()
RETURNS TRIGGER AS $persona_elimlog$
BEGIN
	IF new.persona_activa = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_persona,inet_client_addr());
	ELSE
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_persona,inet_client_addr());
	END IF;
	--Actualizamos tambien en docente, alumno y usuarios
	UPDATE public."Usuarios"
	SET usu_estado = new.persona_activa
		WHERE id_persona = old.id_persona;

	UPDATE public."Docentes"
	SET docente_activo = new.persona_activa
		WHERE id_persona = old.id_persona;

	UPDATE public."Alumnos"
	SET alumno_activo = new.persona_activa
		WHERE id_persona = old.id_persona;

	RETURN NEW;
END;
$persona_elimlog$ LANGUAGE plpgsql;



--Alumnos
CREATE OR REPLACE FUNCTION alumno_elimlog()
RETURNS TRIGGER AS $alumno_elimlog$
BEGIN
	IF new.alumno_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_alumno,inet_client_addr());
	ELSE
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'ACTIVACION', TG_TABLE_NAME, old.id_alumno,inet_client_addr());
	END IF;
	--Eliminamos tambien el alumno carrera
	UPDATE public."AlumnosCarrera"
	SET almn_carrera_activo = new.alumno_activo
	WHERE id_alumno = old.id_alumno;

	UPDATE public."AlumnoCurso"
	SET almn_curso_activo = new.alumno_activo
	WHERE id_alumno = old.id_alumno;

	RETURN NEW;
END;
$alumno_elimlog$ LANGUAGE plpgsql;



--AlumnosCarrera
CREATE OR REPLACE FUNCTION almn_carrera_elimlog()
RETURNS TRIGGER AS $almn_carrera_elimlog$
BEGIN
	IF new.almn_carrera_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_almn_carrera,inet_client_addr());
	END IF;
	RETURN NEW;
END;
$almn_carrera_elimlog$ LANGUAGE plpgsql;



--AlumnoCurso
CREATE OR REPLACE FUNCTION almn_curso_elim()
RETURNS TRIGGER AS $almn_curso_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_almn_curso,
			old.id_alumno || '%' || old.id_curso
			|| '%' || old.almn_curso_nt_1_parcial || '%' ||
			old.almn_curso_nt_examen_interciclo || '%' ||
			old.almn_curso_nt_2_parcial || '%' ||
			old.almn_curso_nt_examen_final || '%' ||
			old.almn_curso_nt_examen_supletorio || '%' ||
			old.almn_curso_asistencia || '%' ||
			old.almn_curso_nota_final || '%' ||
			old.almn_curso_estado || '%' ||
			old.almn_curso_num_faltas,inet_client_addr());
		RETURN OLD;
END;
$almn_curso_elim$ LANGUAGE plpgsql;

--Eliminacion logica de AlumnoCurso
CREATE OR REPLACE FUNCTION almn_curso_elimlog()
RETURNS TRIGGER AS $almn_curso_elimlog$
BEGIN
	IF new.almn_curso_activo = FALSE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_almn_curso,inet_client_addr());
	END IF;
	RETURN NEW;
END;
$almn_curso_elimlog$ LANGUAGE plpgsql;




--MallaAlumno
CREATE OR REPLACE FUNCTION malla_almn_elim()
RETURNS TRIGGER AS $malla_almn_elim$
BEGIN
	INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla, historial_observacion,historial_ip)
		VALUES(USER, now(), 'DELETE', TG_TABLE_NAME, old.id_malla_alumno,
			old.id_materia || '%' || old.almn_carrera
			|| '%' || old.malla_almn_ciclo || '%' ||
			old.malla_almn_num_matricula || '%' ||
			old.malla_almn_nota1 || '%' ||
			old.malla_almn_nota2 || '%' ||
			old.malla_almn_nota3 || '%' ||
			old.malla_almn_estado || '%' ||
			old.malla_almn_observacion,inet_client_addr());
		RETURN OLD;
END;
$malla_almn_elim$ LANGUAGE plpgsql;

--Materias
CREATE TRIGGER auditoria_materia_elim
BEFORE UPDATE OF materia_activa
ON public."Materias" FOR EACH ROW
EXECUTE PROCEDURE materia_elim();


--MallaAlumno

CREATE TRIGGER auditoria_malla_almn_elim
BEFORE DELETE
ON public."MallaAlumno" FOR EACH ROW
EXECUTE PROCEDURE malla_almn_elim();

--Cursos
CREATE TRIGGER auditoria_curso_elim
BEFORE DELETE
ON public."Cursos" FOR EACH ROW
EXECUTE PROCEDURE curso_elim();

--Docentes
CREATE TRIGGER auditoria_docente_elimlog
BEFORE UPDATE OF docente_activo
ON public."Docentes" FOR EACH ROW
EXECUTE PROCEDURE docente_elimlog();

--MateriaRequisitos
CREATE TRIGGER auditoria_mt_requisito_elim
BEFORE DELETE
ON public."MateriaRequisitos" FOR EACH ROW
EXECUTE PROCEDURE materia_requisito_elim();

--DocentesMateria
CREATE TRIGGER auditoria_docentes_mt_elimlog
BEFORE UPDATE OF docente_mat_activo
ON public."DocentesMateria" FOR EACH ROW
EXECUTE PROCEDURE docentes_materia_elimlog();

--Carreras
CREATE TRIGGER auditoria_carrera_elim
BEFORE UPDATE OF carrera_activo
ON public."Carreras" FOR EACH ROW
EXECUTE PROCEDURE carrera_elimlog();

--PeriodoLectivo
CREATE TRIGGER auditoria_prd_lectivo_elim
BEFORE UPDATE OF prd_lectivo_activo
ON public."PeriodoLectivo" FOR EACH ROW
EXECUTE PROCEDURE prd_lectivo_elim();

--Personas
CREATE TRIGGER auditoria_persona_elimlog
BEFORE UPDATE OF persona_activa
ON public."Personas" FOR EACH ROW
EXECUTE PROCEDURE persona_elimlog();

--Alumnos
CREATE TRIGGER auditoria_alumno_elimlog
BEFORE UPDATE OF alumno_activo
ON public."Alumnos" FOR EACH ROW
EXECUTE PROCEDURE alumno_elimlog();

--AlumnosCarrera
CREATE TRIGGER auditoria_almn_carrera_elimlog
BEFORE UPDATE OF almn_carrera_activo
ON public."AlumnosCarrera" FOR EACH ROW
EXECUTE PROCEDURE almn_carrera_elimlog();

--AlumnosCurso
CREATE TRIGGER auditoria_almn_curso_elim
BEFORE DELETE
ON public."AlumnoCurso" FOR EACH ROW
EXECUTE PROCEDURE almn_curso_elim();





