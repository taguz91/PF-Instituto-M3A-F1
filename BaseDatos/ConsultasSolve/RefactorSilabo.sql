SELECT carrera_modalidad
FROM "Carreras"
WHERE id_carrera = (
  SELECT id_carrera
  FROM "PeriodoLectivo"
  WHERE id_prd_lectivo = (
    SELECT id_prd_lectivo FROM "Cursos"
    WHERE id_curso = ?
  )
);
