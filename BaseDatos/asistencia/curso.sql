SELECT
c.id_curso,
materia_nombre,
curso_nombre
FROM public."SesionClase" sc
JOIN public."Cursos" c
ON sc.id_curso = c.id_curso
JOIN public."Materias" m
ON m.id_materia = c.id_materia
JOIN public."Docentes" d
ON d.id_docente = c.id_docente
JOIN public."Personas" p
ON p.id_persona = d.id_persona
WHERE
id_prd_lectivo = (
  SELECT MAX(id_prd_lectivo)
  FROM public."Cursos"
  WHERE id_docente = d.id_docente
)
AND persona_identificacion = '0104553078'
AND dia_sesion = EXTRACT(DOW FROM now())
AND hora_inicio_sesion = (EXTRACT(HOUR FROM now()) || ':00')::time

GROUP BY
c.id_curso,
materia_nombre

--

UNION

SELECT
c.id_curso,
materia_nombre,
curso_nombre
FROM public."SesionClase" sc
JOIN public."Cursos" c
ON sc.id_curso = c.id_curso
JOIN public."Materias" m
ON m.id_materia = c.id_materia
JOIN public."Docentes" d
ON d.id_docente = c.id_docente
JOIN public."Personas" p
ON p.id_persona = d.id_persona
WHERE
id_prd_lectivo = (
  SELECT MAX(id_prd_lectivo)
  FROM public."Cursos"
  WHERE id_docente = d.id_docente
)
AND persona_identificacion = '0104553078'

GROUP BY
c.id_curso,
materia_nombre

ORDER BY
materia_nombre,
curso_nombre


--- Consultamos las materias que tienen en el ultimo periodo

SELECT
c.id_curso,
prd_lectivo_nombre,
materia_nombre,
curso_nombre,
dia_sesion
FROM public."SesionClase" sc
JOIN public."Cursos" c
ON sc.id_curso = c.id_curso
JOIN public."Materias" m
ON m.id_materia = c.id_materia
JOIN public."Docentes" d
ON d.id_docente = c.id_docente
JOIN public."Personas" p
ON p.id_persona = d.id_persona
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = c.id_prd_lectivo
WHERE persona_identificacion = '0104553078'
AND prd_lectivo_estado = true
GROUP BY
c.id_curso,
prd_lectivo_nombre,
materia_nombre,
curso_nombre,
dia_sesion,
prd_lectivo_fecha_fin
ORDER BY prd_lectivo_fecha_fin DESC,
dia_sesion,
materia_nombre;

-- Informacion del curso al que tomaremos lista

SELECT
c.id_curso,
prd_lectivo_nombre,
materia_nombre,
curso_nombre,
dia_sesion,
COUNT(dia_sesion)
FROM public."SesionClase" sc
JOIN public."Cursos" c
ON sc.id_curso = c.id_curso
JOIN public."Materias" m
ON m.id_materia = c.id_materia
JOIN public."Docentes" d
ON d.id_docente = c.id_docente
JOIN public."Personas" p
ON p.id_persona = d.id_persona
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = c.id_prd_lectivo
WHERE persona_identificacion = '0104553078'
AND prd_lectivo_estado = true
AND c.id_curso = 1181
GROUP BY
c.id_curso,
prd_lectivo_nombre,
materia_nombre,
curso_nombre,
dia_sesion,
prd_lectivo_fecha_fin
ORDER BY prd_lectivo_fecha_fin DESC,
dia_sesion,
materia_nombre;
