--Buscamos las materias repetidas
SELECT (
	SELECT carrera_nombre
	FROM public."Carreras"
	WHERE id_carrera = m.id_carrera
), materia_codigo, count(materia_codigo)
FROM public."Materias" m
GROUP BY materia_codigo, id_carrera
HAVING count(materia_codigo) > 1
ORDER BY 1;

SELECT * FROM public."Materias"
WHERE materia_codigo = 'MI-COML';

SELECT * FROM public."Materias"
WHERE materia_nombre = 'COMUNICACIÓN Y LENGUAJE';

UPDATE public."Materias"
SET id_carrera = 10, materia_codigo = 'COML-01'
WHERE id_materia = 357;


UPDATE public."Materias"
SET materia_activa = true
WHERE id_materia = 357;

DELETE FROM public."Materias"
WHERE id_materia =
