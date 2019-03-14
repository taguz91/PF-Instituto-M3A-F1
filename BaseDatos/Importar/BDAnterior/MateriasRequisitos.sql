--Consultamos todos los materias requitios y co requisitos con el nombre de la materia 

SELECT pre_numero, preco_materia, (
	SELECT mat_nombre 
	FROM public.materias
	WHERE mat_codigo = preco_materia
), preco_tipo, preco_precorrequisito, (
	SELECT mat_nombre 
	FROM public.materias
	WHERE mat_codigo = preco_precorrequisito
)
FROM public.precorrequisitos

--Consultamos las materias con la carrera a la que pertenecen 
	
SELECT pre_numero, preco_materia, (
	SELECT mat_nombre
	FROM public.materias
	WHERE mat_codigo = preco_materia
), (
	SELECT mat_carrera
	FROM public.materias
	WHERE mat_codigo = preco_materia
), preco_tipo, preco_precorrequisito, (
	SELECT mat_nombre 
	FROM public.materias
	WHERE mat_codigo = preco_precorrequisito
), (
	SELECT mat_carrera
	FROM public.materias
	WHERE mat_codigo = preco_precorrequisito
)
FROM public.precorrequisitos
ORDER BY preco_materia, preco_precorrequisito;

--importamos todas las materias preoc requisitos 
Copy (SELECT pre_numero, preco_materia, (
	SELECT mat_nombre
	FROM public.materias
	WHERE mat_codigo = preco_materia
), (
	SELECT mat_carrera
	FROM public.materias
	WHERE mat_codigo = preco_materia
), preco_tipo, preco_precorrequisito, (
	SELECT mat_nombre 
	FROM public.materias
	WHERE mat_codigo = preco_precorrequisito
), (
	SELECT mat_carrera
	FROM public.materias
	WHERE mat_codigo = preco_precorrequisito
)
FROM public.precorrequisitos
ORDER BY preco_materia, preco_precorrequisito) 
To 'C:\Backups Postgresql\materiaPreCoRequisitosP1.csv' 
with CSV HEADER;

CREATE TABLE precorrequisitos(
	pre_numero integer, 
	preco_materia integer, 
	nombre_mat_pre character varying(200),
	carrera_preco integer, 
	tipo character varying(5), 
	requisito integer, 
	nombre_mat_requisito character varying(200), 
	carrera_requisito integer
) WITH (OIDS = FALSE); 

ALTER TABLE public.precorrequisitos ADD UNIQUE(preco_materia, requisito);
--Son los codigos de las materias en la base de datos original
--Este se repite 98 - 104  - 128 (Este es diferente P C) - 202 - 274
--Antes de exportar debo eliminar todos estos datos repetidos

Copy precorrequisitos(pre_numero, preco_materia, nombre_mat_pre, 
	carrera_preco, tipo, requisito, nombre_mat_requisito, 
	carrera_requisito)
From 'C:\Backups Postgresql\materiaPreCoRequisitosP1.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;

--Actualizamos el id de la carrera 
UPDATE public.precorrequisitos
	SET carrera_preco= 10
	WHERE carrera_preco = 11;

UPDATE public.precorrequisitos
	SET carrera_requisito= 10
	WHERE carrera_requisito = 11;

ALTER TABLE public."MateriaRequisitos" ADD UNIQUE(id_materia, id_materia_requisito);

--Consultamos las materias con el id de la base de datos nueva 
SELECT preco_materia, nombre_mat_pre, 
carrera_preco, tipo, requisito, 
nombre_mat_requisito, carrera_requisito, (
	SELECT id_materia AS Materia
	FROM public."Materias"
	WHERE materia_nombre = nombre_mat_pre 
	AND id_carrera = carrera_preco
), (
	SELECT id_materia AS Requisito
	FROM public."Materias"
	WHERE materia_nombre = nombre_mat_requisito 
	AND id_carrera = carrera_requisito
)
FROM public.precorrequisitos 
ORDER BY carrera_preco, carrera_requisito;

--Insertamos 
INSERT INTO public."MateriaRequisitos"(
	 id_materia, id_materia_requisito, tipo_requisito)
SELECT (
	SELECT id_materia AS Materia
	FROM public."Materias"
	WHERE materia_nombre = nombre_mat_pre 
	AND id_carrera = carrera_preco
), (
	SELECT id_materia AS Requisito
	FROM public."Materias"
	WHERE materia_nombre = nombre_mat_requisito 
	AND id_carrera = carrera_requisito
), tipo
FROM public.precorrequisitos 
ORDER BY carrera_preco, carrera_requisito;

DROP TABLE public.precorrequisitos;

