ALTER TABLE public."AlumnosCarrera" ALTER COLUMN "almn_carrera_fecha_registro" TYPE TIMESTAMP;

ALTER TABLE public."AlumnosCarrera" DROP COLUMN "almn_carrera_modalidad";

--Consultamos los alumnos de una carrera con los datos que necesitamos
SELECT aluc_codigo,(
	SELECT per_identificacion
	FROM public.personas, public.alumnos
	WHERE alu_codigo = aluc_alumnos
	AND per_codigo = alu_persona
),aluc_carreras , aluc_fecharegistro
	FROM public.alumnocarreras;

Copy (SELECT aluc_codigo,(
	SELECT per_identificacion
	FROM public.personas, public.alumnos
	WHERE alu_codigo = aluc_alumnos
	AND per_codigo = alu_persona
),aluc_carreras , aluc_fecharegistro
	FROM public.alumnocarreras)
To 'C:\Backups Postgresql\alumnosCarreraP1.csv'
with CSV HEADER;

CREATE TABLE alumnocarreras(
	aluc_codigo integer,
	per_identificacion character varying(20),
	aluc_carreras integer,
	aluc_fecharegistro TIMESTAMP
)with(oids = false);

ALTER TABLE public.alumnocarreras ADD UNIQUE(per_identificacion, aluc_carreras);

Copy alumnocarreras(aluc_codigo, per_identificacion,
	aluc_carreras, aluc_fecharegistro)
FROM 'C:\Backups Postgresql\alumnosCarreraP1.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

UPDATE public.alumnocarreras
	SET aluc_carreras=10
	WHERE aluc_carreras=11;

ALTER TABLE public."AlumnosCarrera" ADD UNIQUE(id_alumno, id_carrera);

--Lo exportamos
INSERT INTO public."AlumnosCarrera"(
	id_alumno, id_carrera, almn_carrera_fecha_registro)
SELECT (
	SELECT id_alumno
	FROM public."Alumnos"
	WHERE alumno_codigo = per_identificacion
),aluc_carreras, aluc_fecharegistro
	FROM public.alumnocarreras
	ORDER BY aluc_carreras;

DROP TABLE public.alumnocarreras;
