
Copy periodolectivos ("per_codigo", "per_carrera", "per_fechainicio", "per_fechafin")
To 'C:\Backups Postgresql\periodosP1.csv'
delimiters ';' with CSV HEADER;


CREATE TABLE periodolectivos(
	per_codigo integer,
	per_carrera integer,
	per_fechainicio date,
	per_fechafin date
) WITH (OIDS = FALSE);

Copy periodolectivos ("per_codigo", "per_carrera", "per_fechainicio", "per_fechafin")
From 'C:\Backups Postgresql\periodosP1.csv'
delimiter AS ';' NULL AS '' CSV HEADER;

--Cambiamos el id de la carrera de 11 a 10
UPDATE public.periodolectivos
	SET per_carrera = 10
	WHERE per_carrera = 11;

--Seteamos el periodo lectivo

INSERT INTO public."PeriodoLectivo"(
	id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin,
	prd_lectivo_activo)
	SELECT per_carrera, (
	SELECT carrera_codigo
	FROM public."Carreras"
	WHERE id_carrera = per_carrera
) ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
  ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin),
  per_fechainicio, per_fechafin, 'true'
	FROM public.periodolectivos ORDER BY per_fechafin DESC;

DROP TABLE public.periodolectivos
