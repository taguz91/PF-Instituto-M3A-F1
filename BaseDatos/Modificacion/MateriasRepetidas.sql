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


--
UPDATE public."Materias"
	SET
	materia_descripcion='La asignatura de MAQUINAS HERRAMIENTAS, nos posibilitará desarrollar las siguientes habilidades y destrezas en los estudiantes:
Leer y medir con instrumentos de medición.
Conocer las calidades superficiales y tolerancias.
Identificar los procesos constructivos en el torno, la fresadora, en el taladro y la rectificadora.',
materia_objetivo_especifico='1.- Intrepreta planos de elementos mecanicos para su construccion
2.- Identifica y conoce el funcionamiento del entorno y la fresadora
3.- Construye elementos mecanicos maquinas herramientas.
4.- Realiza controles de calidad en elementos mecanicos construidos
5.- Elabora fases de trabajo y operacion de maquinas herramientas'
WHERE id_materia= 318;
