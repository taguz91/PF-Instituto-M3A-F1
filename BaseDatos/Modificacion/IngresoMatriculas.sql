--Alumno 404 matriculado dos veces en el periodo 4 //El ID es 6855
--Alumno 406 en el periodo 4
--Los dos son el mismo dia 2018-10-30

SELECT id_almn_curso, id_alumno, almn_curso_fecha_registro
FROM public."AlumnoCurso" ac, public."Cursos" c
	WHERE c.id_curso = ac.id_curso
	AND id_alumno = 406 AND id_prd_lectivo = 4;

--Consultamos todos los alumnos matriculas
SELECT DISTINCT id_alumno, id_prd_lectivo, almn_curso_fecha_registro
	FROM public."AlumnoCurso" ac, public."Cursos" c
	WHERE c.id_curso = ac.id_curso
	ORDER BY id_prd_lectivo;

SELECT DISTINCT id_alumno, id_prd_lectivo
  	FROM public."AlumnoCurso" ac, public."Cursos" c
  	WHERE c.id_curso = ac.id_curso
  	ORDER BY id_prd_lectivo;

INSERT INTO public."Matricula"(
id_alumno, id_prd_lectivo, matricula_fecha)
  SELECT DISTINCT id_alumno, id_prd_lectivo, (
  	SELECT almn_curso_fecha_registro
  	FROM public."AlumnoCurso" ac, public."Cursos" c
  	WHERE c.id_curso = ac.id_curso
  	AND id_alumno = ac.id_alumno
  	AND id_prd_lectivo = c.id_prd_lectivo
  	LIMIT 1)
	FROM public."AlumnoCurso" ac, public."Cursos" c
	WHERE c.id_curso = ac.id_curso
	ORDER BY id_prd_lectivo;

TRUNCATE TABLE public."Matricula";
