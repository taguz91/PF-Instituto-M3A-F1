--El codigo de ecuador es ECU esa no debemos importar 
--Consultamos todos los paises excluyendo ecuador
SELECT * FROM public.country WHERE "Code" <> 'ECU' ORDER BY "Name";

--Importamos lugares porsi ocurran errores  
Copy public."Lugares"("id_lugar", "lugar_codigo", "lugar_nombre", "lugar_nivel", 
			"id_lugar_referencia")
To 'C:\Backups Postgresql\lugares\lugaresPaisesC1.csv' 
delimiters ';' with CSV HEADER;

--Para insertarlo en la tabla lugares  
--Nivel 1 es para paises  
--Nivel 2 es para provincias o estados etc
--Nivel 3 es para ciudades 

--Exportamos todos los paises
Copy (SELECT "Code", "Name" FROM public.country 
	  WHERE "Code" <> 'ECU' ORDER BY "Name")
To 'C:\Backups Postgresql\lugares\paisesP1.csv' 
with CSV HEADER;

CREATE TABLE country(
    "code" character varying(3), 
    "name" character varying(52)
)with (OIDS = FALSE); 

Copy country ("code", "name")
From 'C:\Backups Postgresql\lugares\paisesP1.csv'  
delimiter AS ',' NULL AS '' CSV HEADER; 

INSERT INTO public."Lugares"(
	lugar_nombre, lugar_nivel)
    SELECT UPPER("name"), 1 FROM public.country;

DROP TABLE country;

--Importamos ciudades 
--Para consultar ciudades de un pais  
SELECT * FROM public.city WHERE "CountryCode" = 'ARG';
--Para consultar distritos de un pais
SELECT DISTINCT "District" FROM public.city WHERE "CountryCode" = 'ARG';

--Para consultar todas las ciudades execto ecuador 
SELECT "CountryCode", "Name", "District" FROM public.city WHERE "CountryCode" <> 'ECU';

Copy (SELECT "CountryCode", "Name", "District" FROM public.city WHERE "CountryCode" <> 'ECU')
To 'C:\Backups Postgresql\lugares\ciudadesP1.csv' 
with CSV HEADER;

--Consultamos todas las ciudades y distritos de todos los paises menos ecuador  
SELECT country."Name" AS countr, city."Name", city."District" FROM city, country 
WHERE city."CountryCode" = country."Code" 
AND city."CountryCode" <> 'ECU';

--Guardamos los datos en un csv
Copy (SELECT country."Name" AS countr, city."Name", city."District" FROM city, country 
WHERE city."CountryCode" = country."Code" 
AND city."CountryCode" <> 'ECU')
To 'C:\Backups Postgresql\lugares\ciudadesPaisP1.csv' 
with CSV HEADER;

CREATE TABLE city(
    "country" character varying(100),
    "name"character varying(100), 
    "district" character varying(100)
) with (OIDS = FALSE);

Copy city("country", "name", "district")
From 'C:\Backups Postgresql\lugares\ciudadesPaisP1.csv'  
delimiter AS ',' NULL AS '' CSV HEADER; 

--Pasamos los datos del primer pais
--Consultamos todos los paises de la clase lugares
SELECT * FROM public."Lugares" WHERE id_lugar_referencia IS NULL;
--Consultamos todos los paises menos ecuador 
SELECT * FROM public."Lugares" WHERE id_lugar_referencia IS NULL AND lugar_nombre <>  'ECUADOR';

SELECT "country", "name",  "district" FROM public.city
WHERE "country" = "AFGHANISTAN";

--Consultamos todos los distritos de un pais  
SELECT "country", "name",  "district", (
	SELECT id_lugar FROM public."Lugares" 
	WHERE lugar_nombre = 'AFGHANISTAN'
) FROM public.city
WHERE "country" ILIKE 'AFGHANISTAN';

--Insertamos distritos en lugares pais por pais that is horrible


--COn esta si insertamos bien 

INSERT INTO public."Lugares"(
	lugar_nombre, lugar_nivel, id_lugar_referencia)
    SELECT DISTINCT "district", 2 ,(
	SELECT id_lugar FROM public."Lugares" 
	WHERE lugar_nombre = 'NOMBRE PAIS'
) FROM public.city
WHERE "country" ILIKE 'NOMBRE PAIS MAYUSCULAS';

------


INSERT INTO public."Lugares"(
lugar_nombre, lugar_nivel, id_lugar_referencia)
	SELECT DISTINCT name, 3 ,(
	SELECT id_lugar FROM public."Lugares"
	WHERE lugar_nombre  = 'nombreDistritos' AND 
	lugar_nivel = 2 AND lugar_codigo IS NULL 
) FROM public.city
WHERE "district" ILIKE 'nombreDistritos';

--Ponemos todos los nombres de los paises en mayusculas
UPDATE public."Lugares"
	SET lugar_nombre=UPPER(lugar_nombre);


DROP TABLE public.city;