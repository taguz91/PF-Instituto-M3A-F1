--Arreglar los alumnos no matriculados.
--Todos los alumnos con matricula anulada sin que conste en alumno curso
SELECT id_almn_curso FROM public."AlumnoCurso"
WHERE id_almn_curso IN (
	SELECT id_almn_curso FROM public."AlumnoCursoRetirados"
	WHERE retiro_activo = true
) AND almn_curso_activo = true

--Actualizamos
UPDATE public."AlumnoCurso"
SET almn_curso_activo = false
WHERE id_almn_curso IN (
  SELECT id_almn_curso FROM public."AlumnoCurso"
  WHERE id_almn_curso IN (
  	SELECT id_almn_curso FROM public."AlumnoCursoRetirados"
  	WHERE retiro_activo = true
  ) AND almn_curso_activo = true
)


---De notas
SELECT ac.id_almn_curso, curso_nombre, materia_nombre FROM Public."AlumnoCurso" ac
JOIN public."Cursos" c ON c.id_curso = ac.id_curso
JOIN public."Materias" m ON m.id_materia = c.id_materia
WHERE
ac.id_alumno = (
	SELECT id_alumno
	FROM public."Alumnos"
	WHERE alumno_codigo = '0105549919'
)



SELECT * FROM public."AlumnoCurso" WHERE id_almn_curso = 15063;

UPDATE public."AlumnoCurso"
SET almn_curso_activo = true
WHERE id_almn_curso = 15063;


SELECT id_almn_curso FROM public."AlumnoCurso"
WHERE id_almn_curso IN (
	SELECT id_almn_curso FROM public."AlumnoCursoRetirados"
	WHERE retiro_activo = true
) AND almn_curso_activo = true
