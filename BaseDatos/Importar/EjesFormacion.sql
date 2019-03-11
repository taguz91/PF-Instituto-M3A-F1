Copy ejesformacion("eje_codigo", "eje_id", "eje_nombre", "eje_carrera") 
To 'C:\Backups Postgresql\ejesFormacionP1.csv' 
delimiters ';' with CSV HEADER; 

CREATE TABLE ejesformacion(
	"eje_codigo" integer, 
	"eje_id" character varying(15), 
	"eje_nombre" character varying(100), 
	"eje_carrera" integer
)WITH(OIDS = FALSE);

Copy ejesformacion("eje_codigo", "eje_id", "eje_nombre", "eje_carrera") 
From 'C:\Backups Postgresql\ejesFormacionP1.csv' 
delimiter AS ';' NULL AS '' CSV HEADER;

INSERT INTO public."EjesFormacion"(
	 id_carrera, eje_codigo, eje_nombre)
	SELECT eje_carrera, eje_id, eje_nombre 
	FROM public.ejesformacion ORDER BY eje_codigo ASC; 

DROP TABLE public.ejesformacion;