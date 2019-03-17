--Restriccion de campos unicos
--No se puede guardar una materia en el mismo periodo
--con el mismo docente en la misma jornada
ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_docente,
	id_jornada, curso_ciclo, curso_paralelo);
ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_jornada, curso_ciclo, curso_paralelo);

ALTER TABLE public."MateriaRequisitos" ADD UNIQUE(id_materia, id_materia_requisito);

--No se puede guardar un alumno en el mismo curso
ALTER TABLE public."AlumnoCurso" ADD UNIQUE(id_alumno, id_curso);

ALTER TABLE public."MallaAlumno" ADD UNIQUE(id_materia, id_almn_carrera);
--A un docente solo se le asigna una materia una sola vez
ALTER TABLE public."DocentesMateria" ADD UNIQUE(id_docente, id_materia);
