
--Importamos materias docente

Copy (SELECT pro_id, mat_nombre, mat_carrera  FROM public.docentematerias,
public.profesores, public.materias
WHERE profesores.pro_codigo = docm_profesor AND mat_codigo = docm_materia)
To 'C:\Backups Postgresql\docentesMateriaP1.csv'
with CSV HEADER;

CREATE TABLE "docentesmateria"(
	docente_cedula character varying(20),
	nateria_nombre character varying(100),
	id_carrera integer
);

Copy docentesmateria("docente_cedula", "nateria_nombre", "id_carrera")
From 'C:\Backups Postgresql\docentesMateriaP1.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

--Se edita el id de carrera de la tabla importada
UPDATE public.docentesmateria
	SET id_carrera=10
	WHERE id_carrera=11;

--Estas dos personas no se encuentras registradas en docentes
--SELECT * FROM public."Personas" WHERE persona_identificacion = '0103687323'
--En la base de datos nueva solo este no esta:
SELECT * FROM public."Personas" WHERE persona_identificacion = '0104851720'

INSERT INTO public."Docentes"(
	id_persona, docente_codigo, docente_otro_trabajo,
	 docente_categoria, docente_fecha_contrato,
	 docente_tipo_tiempo)
SELECT id_persona, '0103687323', 'false', 6, '2016-10-01', 'C'
		FROM public."Personas"
		WHERE persona_identificacion = '0103687323'

INSERT INTO public."Docentes"(
	id_persona, docente_codigo, docente_otro_trabajo,
	 docente_categoria, docente_fecha_contrato,
	 docente_tipo_tiempo)
SELECT id_persona, '0104851720', 'false', 6, '2015-06-01', 'C'
		FROM public."Personas"
		WHERE persona_identificacion = '0104851720'

--Consultamos el id del docente
SELECT (SELECT id_docente
	   FROM public."Docentes"
	   WHERE docente_codigo = docente_cedula),
	    'true'
FROM public.docentesmateria;

--Consultamos las materias que se repiten en la tabla materias para eliminarlas
SELECT materia_nombre, id_carrera, COUNT(*)
FROM public."Materias"
GROUP BY id_carrera, materia_nombre
HAVING COUNT(*) > 1;

--Consultamos sucodigo para eliminar una de ellas
SELECT *
FROM public."Materias"
WHERE materia_nombre = 'DERECHOS HUMANOS APLICADOS AL CONTEXTO PENITENCIARIO';

DELETE FROM public."Materias"
	WHERE id_materia = 325;

SELECT *
FROM public."Materias"
WHERE materia_nombre = 'SEGURIDAD INDUSTRIAL';

DELETE FROM public."Materias"
	WHERE id_materia = 317;

SELECT *
FROM public."Materias"
WHERE materia_nombre = 'TICS II';

DELETE FROM public."Materias"
	WHERE id_materia = 324;

--Consultamos los docentes que tienen asignado una materia mas de una vez
SELECT docente_cedula, count(*) FROM public.docentesmateria
GROUP BY docente_cedula, nateria_nombre, id_carrera
HAVING count(*) > 1;

--Importante eliminar el campo unico 

--Insertamos docentes materia
INSERT INTO public."DocentesMateria"(
	id_docente, id_materia, docente_mat_activo)
SELECT (SELECT id_docente
	   FROM public."Docentes"
	   WHERE docente_codigo = docente_cedula),
	   (SELECT id_materia
	   FROM public."Materias"
	   WHERE materia_nombre = nateria_nombre AND
	   id_carrera = docentesmateria.id_carrera), 'true'
FROM public.docentesmateria;

DROP TABLE docentesmateria
