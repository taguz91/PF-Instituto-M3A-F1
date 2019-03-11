--Con esta consulta importamos exitosamente 

Copy lugares("lug_codigo", "lug_id", "lug_nombre", "lug_nivel", "lug_referencia") 
To 'C:\Backups Postgresql\lugaresP3.csv' 
delimiters ';' with CSV HEADER;

--Crear la tabla de lugares 

CREATE TABLE lugares(
	"lug_codigo" integer NOT NULL, 
	"lug_id" character varying(10), 
	"lug_nombre" character varying(100), 
	"lug_nivel" integer, 
	"lug_referencia" integer, 
	CONSTRAINT lugares_pk PRIMARY KEY ("lug_codigo")
) WITH (OIDS = FALSE); 

--Pasos para subir lugares 

Copy lugares ("lug_codigo", "lug_id", "lug_nombre", "lug_nivel", "lug_referencia")
From 'C:\Backups Postgresql\lugaresP3.csv' 
delimiter AS ';' NULL AS '' CSV HEADER; 

--Despues de crear la tabla lugares y subir los datos ahi se
--Realiza la siguiente insercion 

INSERT INTO public."Lugares" 
(lugar_codigo, lugar_nombre, lugar_nivel, id_lugar_referencia)
SELECT lug_id, lug_nombre, lug_nivel, lug_referencia 
FROM public.lugares;

DROP TABLE public.lugares; 

--Importamos los paises de la base de datos WORLD 

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

--Importamos ciudades y distritos para luego ingresarlo con codigo  

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

--Consultamos todos los distritos que no tengan nombre largo 
	
SELECT name, country || name
FROM public.city WHERE country  || name IN (
SELECT country || name
FROM public.city WHERE char_length(district) < 3);

--Antes de importar actualizamos la tabla de city 
UPDATE public.city
	SET district='South Hill'
	WHERE country || name = 'AnguillaSouth Hill';

UPDATE public.city
	SET district='Oranjestad'
	WHERE country || name = 'ArubaOranjestad';

UPDATE public.city
	SET district='The Valley'
	WHERE country || name = 'AnguillaThe Valley';

UPDATE public.city
	SET district='Douglas'
	WHERE country || name = 'United KingdomDouglas';

UPDATE public.city
	SET district='Gibraltar'
	WHERE country || name = 'GibraltarGibraltar';

UPDATE public.city
	SET district='Tamuning'
	WHERE country || name = 'GuamTamuning';

UPDATE public.city
	SET district='Agaña'
	WHERE country || name = 'GuamAgaña';

UPDATE public.city
	SET district='Flying Fish Cove'
	WHERE country || name = 'Christmas IslandFlying Fish Cove';

UPDATE public.city
	SET district='Monte-Carlo'
	WHERE country || name = 'MonacoMonte-Carlo';	

UPDATE public.city
	SET district='Monaco-Ville'
	WHERE country || name = 'MonacoMonaco-Ville';

UPDATE public.city
	SET district='Yangor'
	WHERE country || name = 'NauruYangor';

UPDATE public.city
	SET district='Yaren'
	WHERE country || name = 'NauruYaren';

UPDATE public.city
	SET district='Alofi'
	WHERE country || name = 'NiueAlofi';

UPDATE public.city
	SET district='Kingston'
	WHERE country || name = 'Norfolk IslandKingston';

UPDATE public.city
	SET district='Adamstown'
	WHERE country || name = 'PitcairnAdamstown';

UPDATE public.city
	SET district='Singapore'
	WHERE country || name = 'SingaporeSingapore';

UPDATE public.city
	SET district='Taiping'
	WHERE country || name = 'TaiwanTaiping';

UPDATE public.city
	SET district='Taliao'
	WHERE country || name = 'TaiwanTaliao';

UPDATE public.city
	SET district='Kueishan'
	WHERE country || name = 'TaiwanKueishan';

UPDATE public.city
	SET district='Nouméa'
	WHERE country || name = 'New CaledoniaNouméa';

UPDATE public.city
	SET district='Città del Vaticano'
	WHERE country || name = 'Holy See (Vatican City State)Città del Vaticano';

UPDATE public.city
	SET district='Ciudad Losada'
	WHERE country || name = 'VenezuelaCiudad Losada';
--Ahora usamos un codigo hecho en java para ingresarlos. 

--La clase se llama LlenarLugaresBD

--Consultamos los distritos de un pais 
SELECT DISTINCT "district", 2 ,(
	SELECT id_lugar FROM public."Lugares" 
	WHERE lugar_nombre = 'Anguilla'
) FROM public.city
WHERE "country" ILIKE 'Anguilla';


--Consultamos las ciudadesde de los distritos 
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