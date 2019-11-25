-- Consultamos los IDS de materias de ingles
-- Tambien exiten carreras en las que se llama lenguaje extranjero

SELECT id_materia,
materia_nombre,
carrera_codigo
FROM public."Materias" m
JOIN public."Carreras" c
ON c.id_carrera = m.id_carrera
WHERE materia_nombre ILIKE '%INGL%'
AND carrera_modalidad = 'DUAL';

-- Consultamos los IDS de record academico de ingles en la malla

SELECT
ma.id_materia,
materia_nombre,
malla_almn_estado,
carrera_codigo
FROM public."MallaAlumno" ma
JOIN public."Materias" m
ON m.id_materia = ma.id_materia
JOIN public."Carreras" c
ON c.id_carrera = m.id_carrera
WHERE materia_nombre ILIKE '%INGL%'
AND carrera_modalidad = 'DUAL';
