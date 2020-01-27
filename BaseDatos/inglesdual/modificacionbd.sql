-- Movemos todas las materias de ingles de DUAL a CENTRO DE IDIOMAS

UPDATE public."Materias"
SET id_carrera = 12
WHERE id_materia IN (
  SELECT id_materia
  FROM public."Materias" m
  JOIN public."Carreras" c
  ON m.id_carrera = c.id_carrera
  WHERE carrera_modalidad = 'DUAL' AND (
    materia_nombre ILIKE '%INGL%'
    OR materia_nombre ILIKE '%EXTRA%'
  )
);


--- Idioma extranjero si esta en la malla ajkdhwgfdwajkdawbjdbawdvawgdv

UPDATE public."Materias"
SET id_carrera = 4
WHERE id_materia IN (
	SELECT id_materia FROM public."Materias"
	WHERE materia_nombre ILIKE '%EXTRANJERO%'
)
