--Arreglar los alumnos no matriculados.
--Todos los alumnos con matricula anulada sin que conste en alumno curso
SELECT * FROM public."AlumnoCurso"
WHERE id_almn_curso IN (
	SELECT id_almn_curso FROM public."AlumnoCursoRetirados"
	WHERE retiro_activo = true
) AND almn_curso_activo = true

--Actualizamos
UPDATE public."AlumnoCurso"
SET almn_curso_activo = false
WHERE id_almn_curso IN (
  SELECT id_almn_curso
  FROM public."AlumnoCursoRetirados"
	WHERE retiro_activo = true
)
