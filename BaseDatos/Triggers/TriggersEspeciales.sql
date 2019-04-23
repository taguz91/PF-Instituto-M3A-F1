--Con este trigger actualizamos el nombre del periodo lectivo si es que
--Se cambia el codigo de la carrera

CREATE OR REPLACE FUNCTION actualiza_nom_prd()
RETURNS TRIGGER AS $actualiza_nom_prd$
BEGIN
  UPDATE public."PeriodoLectivo"
  SET prd_lectivo_nombre = REPLACE (prd_lectivo_nombre, old.carrera_codigo, new.carrera_codigo)
  WHERE id_carrera = new.id_carrera;
  RETURN NEW;
END;
$actualiza_nom_prd$ LANGUAGE plpgsql;

CREATE TRIGGER actualizar_cod_carrera
BEFORE UPDATE OF carrera_codigo
ON public."Carreras" FOR EACH ROW
EXECUTE PROCEDURE actualiza_nom_prd();

--Trigger que inicia ingreso de notas
--Para borrar los triggers si es necesario
--DROP TRIGGER inicia_ingreso_notas ON public."Cursos";
--DROP FUNCTION iniciar_ingreso_notas;

CREATE OR REPLACE FUNCTION iniciar_ingreso_notas()
RETURNS TRIGGER AS $iniciar_ingreso_notas$
BEGIN
INSERT INTO public."IngresoNotas"(id_curso)
VALUES (new.id_curso);
  RETURN NEW;
END;
$iniciar_ingreso_notas$ LANGUAGE plpgsql;

CREATE TRIGGER inicia_ingreso_notas
AFTER INSERT
ON public."Cursos" FOR EACH ROW
EXECUTE PROCEDURE iniciar_ingreso_notas();

--Trigger para cuando se ingresa un alumno en un curso
CREATE OR REPLACE FUNCTION actualiza_malla_matricula()
RETURNS TRIGGER AS $actualiza_malla_matricula$
BEGIN
UPDATE public."MallaAlumno"
  SET malla_almn_estado = 'M', malla_almn_num_matricula = malla_almn_num_matricula + 1
  WHERE id_materia = (
    SELECT id_materia
    FROM public."Cursos"
    WHERE id_curso = new.id_curso
  ) AND id_almn_carrera = (
    SELECT id_almn_carrera
    FROM public."AlumnosCarrera"
    WHERE id_alumno = new.id_alumno AND
    id_carrera = (
      SELECT id_carrera
      FROM public."PeriodoLectivo"
      WHERE id_prd_lectivo = (
        SELECT id_prd_lectivo
        FROM public."Cursos"
        WHERE id_curso = new.id_curso)
    )
  );
  RETURN NEW;
END;
$actualiza_malla_matricula$ LANGUAGE plpgsql;

CREATE TRIGGER actualiza_malla_matricula
AFTER INSERT
ON public."AlumnoCurso" FOR EACH ROW
EXECUTE PROCEDURE actualiza_malla_matricula();

--Al volver reactivar una persona se activa tambien en docente o alumno
CREATE OR REPLACE FUNCTION persona_activada()
RETURNS TRIGGER AS $persona_activada$
BEGIN
	IF new.persona_activa = TRUE THEN
		INSERT INTO public."HistorialUsuarios"(
		usu_username, historial_fecha, historial_tipo_accion,
		historial_nombre_tabla, historial_pk_tabla)
		VALUES(USER, now(), 'UPDATE', TG_TABLE_NAME, old.id_persona);
		--Eliminalos el docente o alumno que este registrado como esta persona
		UPDATE public."Docentes"
		SET docente_activo = 'True'
		WHERE id_persona = old.id_persona;

		UPDATE public."Alumnos"
		SET alumno_activo = 'True'
		WHERE id_persona = old.id_persona;

		UPDATE public."Usuarios"
		SET usu_estado = 'True'
		WHERE id_persona = old.id_persona;
	END IF;
	RETURN NEW;
END;
$persona_activada$ LANGUAGE plpgsql;

CREATE TRIGGER auditoria_persona_activa
BEFORE UPDATE OF persona_activa
ON public."Personas" FOR EACH ROW
EXECUTE PROCEDURE persona_activada();

--Iniciar malla al inscribir un alumno en un curso

CREATE OR REPLACE FUNCTION iniciar_malla()
RETURNS TRIGGER AS $iniciar_malla$
BEGIN
  INSERT INTO public."MallaAlumno"(id_almn_carrera, id_materia, malla_almn_ciclo)
  SELECT new.id_almn_carrera, id_materia, materia_ciclo
  FROM public."Materias"
  WHERE id_carrera = new.id_carrera;
  RETURN NEW;
END;
$iniciar_malla$ LANGUAGE plpgsql;

CREATE TRIGGER inicia_malla
AFTER INSERT
ON public."AlumnosCarrera" FOR EACH ROW
EXECUTE PROCEDURE iniciar_malla();

--Trigger al retirar a un alumno de una materia
CREATE OR REPLACE FUNCTION retirar_almn_clase()
RETURNS TRIGGER AS $retirar_almn_clase$
BEGIN
  UPDATE public."AlumnoCurso"
    SET almn_curso_estado = 'RETIRADO'
    WHERE id_almn_curso = new.id_almn_curso;

  UPDATE public."MallaAlumno"
  SET malla_almn_estado = 'A', malla_almn_num_matricula = malla_almn_num_matricula - 1
  WHERE id_almn_carrera = (
  	SELECT id_almn_carrera
  	FROM public."AlumnosCarrera"
  	WHERE id_alumno = (
  		SELECT id_alumno
  		FROM public."AlumnoCurso"
  		WHERE id_almn_curso = new.id_almn_curso) AND
  	id_carrera = (
  		SELECT id_carrera
  		FROM public."Materias"
  		WHERE id_materia = (
  			SELECT id_materia
  			FROM public."Cursos"
  			WHERE id_curso = (
  				SELECT id_curso
  				FROM public."AlumnoCurso"
  				WHERE id_almn_curso = new.id_almn_curso
  			)
  		)
  	)
  ) AND id_materia = (
  	SELECT id_materia
  		FROM public."Cursos"
  		WHERE id_curso = (
  			SELECT id_curso
  			FROM public."AlumnoCurso"
  			WHERE id_almn_curso = new.id_almn_curso)
  );

  RETURN NEW;
END;
$retirar_almn_clase$ LANGUAGE plpgsql;

CREATE TRIGGER retira_almn_clase
AFTER INSERT
ON public."AlumnoCursoRetirados" FOR EACH ROW
EXECUTE PROCEDURE retirar_almn_clase();

--Eliminar retiro alumno
/*
CREATE OR REPLACE FUNCTION retirar_almn_clase()
RETURNS TRIGGER AS $retirar_almn_clase$
BEGIN
  UPDATE public."AlumnoCurso"
    SET almn_curso_estado = 'RETIRADO'
    WHERE id_almn_curso = new.id_almn_curso;

  UPDATE public."MallaAlumno"
  SET malla_almn_estado = 'A', malla_almn_num_matricula = malla_almn_num_matricula - 1
  WHERE id_almn_carrera = (
  	SELECT id_almn_carrera
  	FROM public."AlumnosCarrera"
  	WHERE id_alumno = (
  		SELECT id_alumno
  		FROM public."AlumnoCurso"
  		WHERE id_almn_curso = new.id_almn_curso) AND
  	id_carrera = (
  		SELECT id_carrera
  		FROM public."Materias"
  		WHERE id_materia = (
  			SELECT id_materia
  			FROM public."Cursos"
  			WHERE id_curso = (
  				SELECT id_curso
  				FROM public."AlumnoCurso"
  				WHERE id_almn_curso = new.id_almn_curso
  			)
  		)
  	)
  ) AND id_materia = (
  	SELECT id_materia
  		FROM public."Cursos"
  		WHERE id_curso = (
  			SELECT id_curso
  			FROM public."AlumnoCurso"
  			WHERE id_almn_curso = new.id_almn_curso)
  );

  RETURN NEW;
END;
$retirar_almn_clase$ LANGUAGE plpgsql;

CREATE TRIGGER retira_almn_clase
AFTER UPDATE OF retiro_activo
ON public."AlumnoCursoRetirados" FOR EACH ROW
EXECUTE PROCEDURE retirar_almn_clase();*/
