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
