SELECT
prd_lectivo_fecha_inicio,
prd_lectivo_fecha_fin
FROM public."PeriodoLectivo"
WHERE id_prd_lectivo = 31


SELECT
*
FROM public."SesionClase"
WHERE id_curso IN (
  SELECT id_curso
  FROM public."Cursos"
  WHERE id_prd_lectivo = 31
  AND id_docente = (
    SELECT id_docente
    FROM public."Docentes" d
    JOIN public."Personas" p
    ON d.id_persona = p.id_persona
    WHERE persona_identificacion = '0103050274'
  )
) AND hora_inicio_sesion > '7:00'
AND dia_sesion = 1
ORDER BY dia_sesion;

-- Cursos del dia en el que inicia clases


SELECT
c.id_curso,
materia_nombre,
COUNT(c.id_curso)
FROM public."SesionClase" sc
JOIN public."Cursos" c
ON sc.id_curso = c.id_curso
JOIN public."Materias" m
ON m.id_materia = c.id_materia
WHERE
id_prd_lectivo = 31
AND id_docente = (
  SELECT id_docente
  FROM public."Docentes" d
  JOIN public."Personas" p
  ON d.id_persona = p.id_persona
  WHERE persona_identificacion = '0104553078'
) AND dia_sesion = 2

GROUP BY
c.id_curso,
materia_nombre,
c.id_curso,
dia_sesion
ORDER BY dia_sesion;

-- Lista de fechas por periodo

SELECT
dia_sesion, (
  SELECT prd_lectivo_fecha_inicio
  FROM public."PeriodoLectivo" plr
  JOIN public."Cursos" cr
  ON cr.id_prd_lectivo = plr.id_prd_lectivo
  WHERE id_curso = 1218
) AS "fecha_inicio_periodo"
FROM public."SesionClase"
WHERE id_curso = 1218
GROUP BY dia_sesion;

--

SELECT prd_lectivo_fecha_inicio,
prd_lectivo_fecha_fin, (
  SELECT MIN(dia_sesion)
  FROM public."SesionClase"
  WHERE id_curso = cr.id_curso
) dia_inicia, (
  SELECT  MAX(dia_sesion)
  FROM public."SesionClase"
  WHERE id_curso = cr.id_curso
)AS dia_fin, (
  SELECT array_to_json(
    array_agg(d.*)
  ) AS horas FROM (
    SELECT
    dia_sesion,
    COUNT(dia_sesion) AS num_horas
    FROM public."SesionClase"
    WHERE id_curso = cr.id_curso
    GROUP BY dia_sesion
  ) AS d
) AS dias

FROM public."PeriodoLectivo" plr
JOIN public."Cursos" cr
ON cr.id_prd_lectivo = plr.id_prd_lectivo
WHERE id_curso = 1218

-- Lista de alumnos

SELECT
id_almn_curso,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido
FROM public."AlumnoCurso" ac
JOIN public."Alumnos" a
ON ac.id_alumno = a.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE ac.id_curso = 1218;
