Copy (SELECT sece_codigo, sece_id, sece_descripcion, sece_activo
FROM public.sectoreconomicos)
To 'C:\Backups Postgresql\sectorEconomicoP1.csv' with CSV HEADER

CREATE TABLE "sectores"(
	"sece_codigo" integer, 
	"sece_ide" character varying(10), 
	"sece_descripcion" character varying(200), 
	"sece_activo" boolean
) WITH (OIDS = FALSE);

Copy sectores("sece_codigo", "sece_ide", "sece_descripcion", 
"sece_activo")
From 'C:\Backups Postgresql\sectorEconomicoP1.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;

INSERT INTO public."SectorEconomico"(
	sec_economico_codigo, sec_economico_descripcion, sec_economico_activo)
	SELECT sece_ide, 
	sece_descripcion, sece_activo
	FROM public.sectores; 

DROP TABLE public.sectores;