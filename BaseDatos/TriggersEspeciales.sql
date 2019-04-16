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

CREATE TRIGGER inicia_malla_
AFTER INSERT
ON public."AlumnosCarrera" FOR EACH ROW
EXECUTE PROCEDURE iniciar_malla();
