/*Exportamos carreras */
Copy carreras("car_codigo", "car_id", "car_descripcion", "car_fechainicio", "car_fechafin",
	"car_modalidad", "carr_activo")
To 'C:\Backups Postgresql\carrerasP1.csv'
delimiters ';' with CSV HEADER;

CREATE TABLE carreras(
	"car_codigo" integer,
	"car_id" character varying(15),
	"car_descripcion" character varying(100),
	"car_fechainicio" date,
	"car_fechafin" date,
	"car_modalidad" integer,
	"carr_activo" boolean
) WITH (OIDS = FALSE);

Copy carreras("car_codigo", "car_id", "car_descripcion", "car_fechainicio", "car_fechafin",
	"car_modalidad", "carr_activo")
From 'C:\Backups Postgresql\carrerasP1.csv'
delimiter AS ';' NULL AS '' CSV HEADER;

INSERT INTO public."Carreras"(
	carrera_nombre, carrera_codigo, carrera_fecha_inicio, carrera_modalidad)
	SELECT car_descripcion, car_id, car_fechainicio, car_modalidad
	FROM public.carreras ORDER BY car_codigo;

DROP TABLE public.carreras;

UPDATE public."Carreras"
	SET  carrera_modalidad='PRESENCIAL'
	WHERE carrera_modalidad = '1';

UPDATE public."Carreras"
	SET  carrera_modalidad='DUAL'
	WHERE carrera_modalidad = '4';
