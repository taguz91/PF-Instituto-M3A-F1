--Agregamos el campo de paralelo en curso  
ALTER TABLE public."Cursos" ADD COLUMN curso_paralelo character varying(2) NOT NULL

ALTER TABLE public."Alumnos" ALTER COLUMN "alumno_nombre_contacto_emergencia" TYPE character varying(200)

ALTER TABLE public."MallaEstudiante" ALTER COLUMN "id_malla_alumno" TYPE serial

INSERT INTO public."Jornadas"(
	 nombre_jornada)
	VALUES ('MATUTINA'),
	('VESPERTINA'),
	('NOCTURNA'),
	('INTENSIVA');

--Restriccion de campos unicos  
--No se puede guardar una materia en el mismo periodo  
--con el mismo docente en la misma jornada  
ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_docente, 
	id_jornada, curso_ciclo, curso_paralelo);
ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_jornada, curso_ciclo, curso_paralelo);

--No se puede guardar un alumno en el mismo curso
ALTER TABLE public."AlumnoCurso" ADD UNIQUE(id_alumno, id_curso);