--Consultamos con los datos que necesitamos 
SELECT (
	SELECT per_identificacion
	FROM public.personas, public.alumnos, public.alumnocarreras
	WHERE aluc_codigo = alum_alumnocarrera AND 
	alu_codigo = aluc_alumnos AND per_codigo = alu_persona
), (
	SELECT mat_nombre 
	FROM public.materias
	WHERE mat_codigo = alum_materia
), (
	SELECT mat_carrera 
	FROM public.materias
	WHERE mat_codigo = alum_materia
),alum_ciclo, alum_nromatricula, 
alum_nota1, alum_nota2, alum_nota3, alum_estado
	FROM public.alumnomaterias ORDER BY alum_alumnocarrera;

--Importamos los datos 
Copy (SELECT (
	SELECT per_identificacion
	FROM public.personas, public.alumnos, public.alumnocarreras
	WHERE aluc_codigo = alum_alumnocarrera AND 
	alu_codigo = aluc_alumnos AND per_codigo = alu_persona
), (
	SELECT mat_nombre 
	FROM public.materias
	WHERE mat_codigo = alum_materia
), (
	SELECT mat_carrera 
	FROM public.materias
	WHERE mat_codigo = alum_materia
),alum_ciclo, alum_nromatricula, 
alum_nota1, alum_nota2, alum_nota3, alum_estado
	FROM public.alumnomaterias ORDER BY alum_alumnocarrera)
To 'C:\Backups Postgresql\alumnosMateriasP1.csv' 
with CSV HEADER;

CREATE TABLE alumnomaterias(
	per_identificacion character varying(20), 
	mat_nombre character varying(200),
	mat_carrera integer, 
	alum_ciclo integer, 
	alum_nromatricula integer, 
	alum_nota1 numeric(5, 2),
	alum_nota2 numeric(5, 2),
	alum_nota3 numeric(5, 2),
	alum_estado character varying(5)
) WITH (OIDS = FALSE);

ALTER TABLE public.alumnomaterias ADD UNIQUE(per_identificacion, mat_nombre, mat_carrera);

--Debemos borrar un buen de datos repetidos

Copy alumnomaterias(per_identificacion, mat_nombre, 
	mat_carrera, alum_ciclo, alum_nromatricula, 
	alum_nota1, alum_nota2, alum_nota3, alum_estado)
FROM 'C:\Backups Postgresql\alumnosMateriasP1.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;

--Debemos eliminar y crearla de nuevo

--Malla Estudiante en su carrera 
DROP TABLE public."MallaAlumno";

CREATE TABLE "MallaAlumno"(
	"id_malla_alumno" serial NOT NULL, 
	"id_materia" integer NOT NULL, 
	"id_almn_carrera" integer NOT NULL, 
	"malla_almn_ciclo" integer NOT NULL, 
	"malla_almn_num_matricula" integer NOT NULL DEFAULT '0', 
	"malla_almn_nota1" numeric(5, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota2" numeric(5, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota3" numeric(5, 2) NOT NULL DEFAULT '0',
	"malla_almn_estado" character varying(1) NOT NULL DEFAULT 'P', 
	"malla_alm_observacion" character varying(200),
	CONSTRAINT malla_estudiante_pk PRIMARY KEY ("id_malla_alumno")
) WITH (OIDS = FALSE); 

ALTER TABLE "MallaAlumno" ADD CONSTRAINT "malla_alumno_fk1"
FOREIGN KEY ("id_materia") REFERENCES "Materias"("id_materia")
ON UPDATE CASCADE ON DELETE CASCADE; 

ALTER TABLE "MallaAlumno" ADD CONSTRAINT "malla_alumno_fk2"
FOREIGN KEY ("id_almn_carrera") REFERENCES "AlumnosCarrera"("id_almn_carrera")
ON UPDATE CASCADE ON DELETE CASCADE; 


ALTER TABLE public."MallaAlumno" ADD UNIQUE(id_materia, id_almn_carrera);

--Con este consultamos los datos bien 
SELECT per_identificacion,(
	SELECT id_materia 
	FROM public."Materias"
	WHERE materia_nombre = mat_nombre
	AND id_carrera = mat_carrera
),(
	SELECT id_almn_carrera 
	FROM public."AlumnosCarrera", public."Alumnos"
	WHERE "Alumnos".alumno_codigo = per_identificacion
	AND "AlumnosCarrera".id_alumno = "Alumnos".id_alumno
	AND "AlumnosCarrera".id_carrera = mat_carrera
), alum_ciclo, alum_nromatricula, alum_nota1, 
alum_nota2, alum_nota3, alum_estado
	FROM public.alumnomaterias;

--Importamos bien 

INSERT INTO public."MallaAlumno"(
	id_materia, id_almn_carrera, 
	malla_almn_ciclo, malla_almn_num_matricula, 
	malla_almn_nota1, malla_almn_nota2, 
	malla_almn_nota3, malla_almn_estado)
SELECT (
	SELECT id_materia 
	FROM public."Materias"
	WHERE materia_nombre = mat_nombre
	AND id_carrera = mat_carrera
),(
	SELECT id_almn_carrera 
	FROM public."AlumnosCarrera", public."Alumnos"
	WHERE "Alumnos".alumno_codigo = per_identificacion
	AND "AlumnosCarrera".id_alumno = "Alumnos".id_alumno
	AND "AlumnosCarrera".id_carrera = mat_carrera
), alum_ciclo, alum_nromatricula, alum_nota1, 
alum_nota2, alum_nota3, alum_estado
	FROM public.alumnomaterias;
	
DROP TABLE public.alumnomaterias;