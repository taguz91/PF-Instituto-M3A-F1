--Importar a un archivo csv

Copy lugares("lug_id", "lug_nombre", "lug_nivel", "lug_referencia") 
To 'C:\Backups Postgresql\lugaresP1.csv' 
delimiters ';' with CSV HEADER; 

--Para exportar de un archivo csv

Copy "Lugares" ("id_lugar", "lugar_codigo", "lugar_nombre", "lugar_nivel", "id_lugar_referencia")
From 'C:\Backups Postgresql\lugares2Nuevo.csv' 
With (delimiter ';'); 

--Con esta exportamos si existen datos nulos  

Copy lugares ("lug_codigo", "lug_id", "lug_nombre", "lug_nivel", "lug_referencia")
From 'C:\Backups Postgresql\lugaresP3.csv' 
delimiter AS ';' NULL AS '' CSV HEADER; 

--Para cambiar el tipo de datos de una columna

ALTER TABLE public."Lugares" 
ALTER COLUMN id_lugar
TYPE integer;
/*Estos pasos segui para exportar los datos de lugares a la nueva base de datos*/

--Crear la tabla de lugares 

CREATE TABLE lugares(
	"lug_codigo" integer NOT NULL, 
	"lug_id" character varying(10), 
	"lug_nombre" character varying(100), 
	"lug_nivel" integer, 
	"lug_referencia" integer, 
	CONSTRAINT lugares_pk PRIMARY KEY ("lug_codigo")
) WITH (OIDS = FALSE); 

--Con esta consulta importamos exitosamente 

Copy lugares("lug_codigo", "lug_id", "lug_nombre", "lug_nivel", "lug_referencia") 
To 'direccion donde se guardara' 
delimiters ';' with CSV HEADER;

--Pasos para subir lugares 

Copy lugares ("lug_codigo", "lug_id", "lug_nombre", "lug_nivel", "lug_referencia")
From 'direccion de donde se leera' 
delimiter AS ';' NULL AS '' CSV HEADER; 

--Despues de crear la tabla lugares y subir los datos ahi se
--Realiza la siguiente insercion 

INSERT INTO public."Lugares" 
(lugar_codigo, lugar_nombre, lugar_nivel, id_lugar_referencia)
SELECT lug_id, lug_nombre, lug_nivel, lug_referencia 
FROM public.lugares;

/*Aqui terminan todos los pasos para exportar lugares*/

/*Importando las materias*/

Copy materias("mat_codigo", "mat_carrera", "mat_id", "mat_nombre", "mat_creditos", 
	"mat_ciclo", "mat_tipo", "mat_eje", "mat_tipoacreditacion", "mat_totalhoras") 
To 'C:\Backups Postgresql\materiasP2.csv' 
delimiters ';' with CSV HEADER; 

CREATE TABLE materias(
	"mat_codigo" integer, 
	"mat_carrera" integer, 
	"mat_id" character varying(154), 
	"mat_nombre" character varying(100), 
	"mat_creditos" integer, 
	"mat_ciclo" integer, 
	"mat_tipo" character varying(1), 
	"mat_eje" integer, 
	"mat_tipoacreditacion" character varying(1), 
	"mat_totalhoras" integer
) WITH (OIDS = FALSE);

Copy materias("mat_codigo", "mat_carrera", "mat_id", "mat_nombre", "mat_creditos", 
	"mat_ciclo", "mat_tipo", "mat_eje", "mat_tipoacreditacion", "mat_totalhoras")
From 'C:\Backups Postgresql\materiasP2.csv' 
delimiter AS ';' NULL AS '' CSV HEADER;

--Se selecionan unicamente las meterias de la carrera de desarrollo del software por
--eso el id 2 

--IMPORTANTE
--Primero se debe guardar una carrera la carrera de desarrollo de software 

INSERT INTO public."Carreras"(
 carrera_nombre, carrera_codigo, 
 carrera_fecha_inicio, carrera_modalidad)
VALUES ('TECNOLOGÍA SUPERIOR EN DESARROLLO DE SOFTWARE', 
		'SDS', '2012-10-10', 'PRESENCIAL');

INSERT INTO public."Materias"
(id_carrera, materia_codigo, materia_nombre, materia_ciclo, 
materia_creditos, materia_tipo, materia_categoria, 
materia_eje, materia_tipo_acreditacion)
SELECT 1, mat_id, mat_nombre, mat_ciclo, mat_creditos, mat_tipo,
'Sin categoria', 'Sin eje', mat_tipoacreditacion
FROM public.materias 
WHERE mat_carrera = 2 ORDER BY mat_ciclo ASC;