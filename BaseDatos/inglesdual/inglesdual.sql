SELECT
m.id_carrera,
id_materia,
carrera_codigo,
materia_nombre
FROM public."Materias" m
JOIN public."Carreras" c
ON m.id_carrera = c.id_carrera
WHERE carrera_modalidad = 'DUAL' AND (
  materia_nombre ILIKE '%INGL%'
  OR materia_nombre ILIKE '%EXTRA%'
)
ORDER BY
id_carrera,
materia_nombre;

-- Consultamos malla de alumnos con ingles
SELECT
id_malla_alumno,
malla_almn_nota1,
malla_almn_num_matricula,
malla_almn_estado
FROM public."MallaAlumno"
WHERE id_materia IN (
  SELECT id_materia
  FROM public."Materias" m
  JOIN public."Carreras" c
  ON m.id_carrera = c.id_carrera
  WHERE carrera_modalidad = 'DUAL' AND (
    materia_nombre ILIKE '%INGL%'
    OR materia_nombre ILIKE '%EXTRA%'
  )
)
ORDER BY malla_almn_estado;

-- Crear un formulario en el que solo se matriculen en alumno curso
