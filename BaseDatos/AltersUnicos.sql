--Restriccion de campos unicos
--No se puede guardar una materia en el mismo periodo
--con el mismo docente en la misma jornada
ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_docente,
	id_jornada, curso_ciclo, curso_paralelo);

ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_docente,
	id_jornada, curso_ciclo, curso_paralelo,curso_activo);

ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_jornada, curso_ciclo, curso_paralelo);

ALTER TABLE public."MateriaRequisitos" ADD UNIQUE(id_materia, id_materia_requisito);

--No se puede guardar un alumno en el mismo curso
ALTER TABLE public."AlumnoCurso" ADD UNIQUE(id_alumno, id_curso);

ALTER TABLE public."MallaAlumno" ADD UNIQUE(id_materia, id_almn_carrera);
--Un alumno solo puede estar en una carrera una vez
ALTER TABLE public."AlumnosCarrera" ADD UNIQUE(id_alumno, id_carrera);
--No se puede asignar un docente a una materia mas de una vez
ALTER TABLE public."DocentesMateria" ADD UNIQUE(id_docente, id_materia);
--No se puede matricualr a un alumno mas de una vez en un mismo periodo
ALTER TABLE public."Matricula" ADD UNIQUE(id_alumno, id_prd_lectivo);
--No se puede duplicar un silabo de una materia y prd lectivo
ALTER TABLE public."Sialbo" ADD UNIQUE(id_materia, id_prd_lectivo);
--No se puede retirar a un alumno de una materia mas de una vez
ALTER TABLE public."AlumnoCursoRetirados" ADD UNIQUE(id_almn_curso);
--SOlo puede tener un tipo de nota en un periodo
ALTER TABLE public."Notas" ADD UNIQUE(id_almn_curso, id_tipo_nota);
