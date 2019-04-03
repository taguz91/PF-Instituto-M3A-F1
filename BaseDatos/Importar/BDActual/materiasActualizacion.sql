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
