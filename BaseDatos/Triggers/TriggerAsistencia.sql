CREATE OR REPLACE FUNCTION ingresando_asistencia()
RETURNS TRIGGER AS $ingresando_asistencia$
DECLARE
  faltas INTEGER := 0;

BEGIN
  SELECT SUM(numero_faltas) INTO faltas
  FROM public."Asistencia"
  WHERE id_almn_curso = new.id_almn_curso;

  UPDATE public."AlumnoCurso"
    SET almn_curso_num_faltas = faltas
  WHERE id_almn_curso = new.id_almn_curso;
  RETURN NEW;
END;
$ingresando_asistencia$ LANGUAGE plpgsql;

CREATE TRIGGER actualizando_faltas
AFTER INSERT ON public."Asistencia" FOR EACH ROW
EXECUTE PROCEDURE ingresando_asistencia();
