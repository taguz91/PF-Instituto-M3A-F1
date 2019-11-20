CREATE OR REPLACE FUNCTION llenar_coordinadores_silabo()
RETURNS VOID AS $llenar_coordinadores_silabo$
DECLARE
  reg RECORD;
  carreras CURSOR FOR SELECT id_carrera,
  id_docente_coordinador
  FROM public."Carreras";
  
BEGIN
  OPEN carreras;
  FETCH carreras INTO reg;

  WHILE ( FOUND ) LOOP
   UPDATE public."Silabo"
   SET id_docente_coordinador = reg.id_docente_coordinador
   WHERE id_prd_lectivo IN (
     SELECT id_prd_lectivo
     FROM public."PeriodoLectivo"
     WHERE id_carrera = reg.id_carrera
   );
   FETCH carreras INTO reg;
  END LOOP;

  CLOSE carreras;
  RETURN;
END;
$llenar_coordinadores_silabo$ LANGUAGE plpgsql;
