SELECT
prd_lectivo_nombre,
persona_identificacion,
persona_primer_nombre || ' ' ||
persona_segundo_nombre AS nombre,
persona_primer_apellido || ' ' ||
persona_segundo_apellido AS apellido, (
  SELECT MAX(curso_ciclo)
  FROM public."AlumnoCurso" ac
  JOIN public."Cursos" c ON c.id_curso = ac.id_curso
  AND ac.id_alumno = m.id_alumno
  AND c.id_prd_lectivo = m.id_prd_lectivo
) AS ciclo_max
FROM public."Matricula" m
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = m.id_prd_lectivo
JOIN public."Alumnos" a ON m.id_alumno = a.id_alumno
JOIN public."Personas" p ON p.id_persona = a.id_persona
WHERE matricula_tipo ILIKE '%%'
AND pl.id_prd_lectivo IN (
  31, 32, 33,
  34, 35, 36,
  37, 38, 39
)
ORDER BY ciclo_max,
pl.id_prd_lectivo ;
