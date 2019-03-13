
Copy materias("mat_codigo", "mat_carrera", "mat_id", "mat_nombre", "mat_creditos", 
	"mat_ciclo", "mat_tipo", "mat_eje", "mat_tipoacreditacion", "mat_totalhoras") 
To 'C:\Backups Postgresql\materiasP2.csv' 
delimiters ';' with CSV HEADER; 

CREATE TABLE materias(
	"mat_codigo" integer, 
	"mat_carrera" integer, 
	"mat_id" character varying(154), 
	"mat_nombre" character varying(100), 
	"mat_creditos" integer, 
	"mat_ciclo" integer, 
	"mat_tipo" character varying(1), 
	"mat_eje" integer, 
	"mat_tipoacreditacion" character varying(1), 
	"mat_totalhoras" integer
) WITH (OIDS = FALSE);

Copy materias("mat_codigo", "mat_carrera", "mat_id", "mat_nombre", "mat_creditos", 
	"mat_ciclo", "mat_tipo", "mat_eje", "mat_tipoacreditacion", "mat_totalhoras")
From 'C:\Backups Postgresql\materiasP3.csv' 
delimiter AS ';' NULL AS '' CSV HEADER;
--Ahora se la pasamos a la nueva tabla 

--Al importar revisamos las materias que se repiten 
SELECT mat_nombre, mat_carrera, COUNT(*) 
FROM public.materias 
GROUP BY mat_carrera, mat_nombre 
HAVING COUNT(*) > 1;
--Eliminamos las materias que se repiten 

--Consultamos coidos para eliminarlos 
SELECT * 
FROM public.materias 
WHERE mat_nombre = 'DERECHOS HUMANOS APLICADOS AL CONTEXTO PENITENCIARIO';

DELETE FROM public.materias
	WHERE mat_codigo = 325;

SELECT * 
FROM public.materias 
WHERE mat_nombre = 'TICS II';

DELETE FROM public.materias
	WHERE mat_codigo = 324;

SELECT * 
FROM public.materias 
WHERE mat_nombre = 'SEGURIDAD INDUSTRIAL';

DELETE FROM public.materias
	WHERE mat_codigo = 317;

INSERT INTO public."Materias"
(id_carrera, id_eje, materia_codigo, materia_nombre, materia_ciclo, 
materia_creditos, materia_tipo, materia_tipo_acreditacion)
SELECT mat_carrera, mat_eje, mat_id, mat_nombre, mat_ciclo, mat_creditos,
mat_tipo, mat_tipoacreditacion
FROM public.materias ORDER BY mat_carrera, mat_ciclo ASC;

DROP TABLE public.materias;
