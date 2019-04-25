
--Consultamos los matriculados en un un curso de un periodo
SELECT count(*) public."AlumnoCurso"
WHERE id_curso IN (
  SELECT id_curso
  FROM public."Cursos"
  WHERE id_prd_lectivo = 8
  curso_nombre = 'M3A'
)

--Consultamos las matriculas hechas en el periodo
SELECT count(*) public."AlumnoCurso"
WHERE id_curso IN(
  SELECT id_curso
  FROM public."Cursos"
  WHERE id_prd_lectivo = 8
)
