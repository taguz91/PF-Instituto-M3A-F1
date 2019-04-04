 --Consultamos las materias que tiene descripcion de nuestra carrera
SELECT materia_codigo AS codigo, id_carrera AS carrera,
materia_objetivo AS objetivo, materia_descripcion AS descripcion,
materia_objetivo_especifico AS objetivo_especifico,
materia_organizacion_curricular AS organizacion_curricular,
materia_campo_formacion AS campo
FROM public."Materias" WHERE id_carrera = 2 AND
materia_objetivo_especifico IS NOT NULL AND
materia_organizacion_curricular IS NOT NUll AND
materia_campo_formacion IS NOT NULL;

Copy (SELECT materia_codigo AS codigo, id_carrera AS carrera,
materia_objetivo AS objetivo, materia_descripcion AS descripcion,
materia_objetivo_especifico AS objetivo_especifico,
materia_organizacion_curricular AS organizacion_curricular,
materia_campo_formacion AS campo
FROM public."Materias" WHERE id_carrera = 2 AND
materia_objetivo_especifico IS NOT NULL AND
materia_organizacion_curricular IS NOT NUll AND
materia_campo_formacion IS NOT NULL)
To 'C:\Backups Postgresql\BDActual\materiaDesP1.csv'
WITH csv HEADER

--codigo,carrera,objetivo,descripcion,objetivo_especifico,organizacion_curricular,campo

CREATE TABLE materias(
  codigo character varying(30),
  carrera integer,
  objetivo text,
  descripcion text,
  objetivo_especifico text,
  organizacion_curricular text,
  campo character varying(200)
)WITH (OIDS = FALSE);

Copy public.materias(codigo,carrera,objetivo,descripcion,objetivo_especifico,organizacion_curricular,campo)
From 'C:\Backups Postgresql\BDActual\materiaDesP1.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

CREATE OR REPLACE FUNCTION migrar_materias()
RETURNS VOID AS $migrar_materias$
DECLARE
  materia INTEGER := 0;
  reg RECORD;
  c_materias CURSOR FOR SELECT codigo, carrera, objetivo, descripcion,
  objetivo_especifico, organizacion_curricular,
  campo FROM public.materias;
BEGIN
  OPEN c_materias;
  FETCH c_materias INTO reg;
  WHILE(FOUND) LOOP
    SELECT id_materia INTO materia
    FROM public."Materias"
    WHERE materia_codigo = reg.codigo AND
      id_carrera = reg.carrera;

    UPDATE public."Materias"
    	SET materia_objetivo=reg.objetivo, materia_descripcion=reg.descripcion,
    		materia_objetivo_especifico=reg.objetivo_especifico,
    		materia_organizacion_curricular=reg.organizacion_curricular,
    		materia_campo_formacion=reg.campo
    	WHERE id_materia = materia;

    RAISE NOTICE 'Materia codigo %', materia;
    FETCH c_materias INTO reg;
  END LOOP;
  RETURN;
END;
$migrar_materias$ LANGUAGE plpgsql;

DROP TABLE public.materias;
