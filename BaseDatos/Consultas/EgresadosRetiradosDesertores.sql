SELECT
carrera_codigo,
carrera_nombre,
COUNT(id_matricula) AS "Ordinarias"
FROM public."Matricula" m
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = m.id_prd_lectivo
JOIN public."Carreras" c
ON c.id_carrera = pl.id_carrera
WHERE m.id_prd_lectivo >= 30
AND matricula_tipo = 'ORDINARIA'
GROUP BY
carrera_codigo,
carrera_nombre
