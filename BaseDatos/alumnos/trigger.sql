-- Trigger cuando un alumno se retira
CREATE OR REPLACE FUNCTION alumno_retirado()
RETURNS TRIGGER AS $alumno_retirado$
BEGIN
  UPDATE public."MallaAlumno"
  SET malla_almn_estado = 'X'
  WHERE id_almn_carrera = new.id_almn_carrera
  AND malla_almn_estado = 'P';
  RETURN NEW;
END;
$alumno_retirado$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION alumno_no_retirado()
RETURNS TRIGGER AS $alumno_retirado$
BEGIN
  UPDATE public."MallaAlumno"
  SET malla_almn_estado = 'P'
  WHERE id_almn_carrera = new.id_almn_carrera
  AND malla_almn_estado = 'X';
  RETURN NEW;
END;
$alumno_retirado$ LANGUAGE plpgsql;

-- Trigger

CREATE TRIGGER actualizar_malla_retirado
BEFORE INSERT ON alumno."Retirados" FOR EACH ROW
EXECUTE PROCEDURE alumno_retirado();

CREATE TRIGGER actualizar_malla_no_retirado
AFTER DELETE ON alumno."Retirados" FOR EACH ROW
EXECUTE PROCEDURE alumno_no_retirado();
