CREATE OR REPLACE FUNCTION set_coordinador()
RETURNS TRIGGER AS $set_coordinador$
BEGIN

  SELECT id_docente_coordinador
  INTO new.id_docente_coordinador
  FROM public."Carreras"
  WHERE id_carrera = (
    SELECT id_carrera
    FROM public."PeriodoLectivo"
    WHERE id_prd_lectivo = new.id_prd_lectivo
  );

END;
$set_coordinador$ LANGUAGE plpgsql;

CREATE TRIGGER agregar_coordinador
BEFORE INSERT ON public."Silabo"
FOR EACH ROW EXECUTE PROCEDURE set_coordinador();
