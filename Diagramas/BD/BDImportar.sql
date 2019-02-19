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

--Reiniciar un auto incremental 
ALTER SEQUENCE "id_eje" RESTART WITH 1;

--Borrar los datos de una tabla y reiniciar el auto incremental  

TRUNCATE TABLE public."EjesFormacion" RESTART IDENTITY CASCADE;

--Para cambiar el tipo de datos de una columna

ALTER TABLE public."Lugares" 
ALTER COLUMN id_lugar
TYPE integer;

--Cambiar el tipo de dato de una columna 
ALTER TABLE public."Materias" ALTER COLUMN "materia_codigo" TYPE character varying(30);
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
From 'C:\Backups Postgresql\lugaresP3.csv' 
delimiter AS ';' NULL AS '' CSV HEADER; 

--Despues de crear la tabla lugares y subir los datos ahi se
--Realiza la siguiente insercion 

INSERT INTO public."Lugares" 
(lugar_codigo, lugar_nombre, lugar_nivel, id_lugar_referencia)
SELECT lug_id, lug_nombre, lug_nivel, lug_referencia 
FROM public.lugares;

DROP TABLE public.lugares; 

/*Aqui terminan todos los pasos para exportar lugares*/

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
/*Aqui terminan los pasos para exportar carreras*/

/*Exportamos ejes de cada materia*/

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
/*Terminamos de exportar ejes de cada materia*/

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
--Ahora se la pasamos a la nueva tabla 

INSERT INTO public."Materias"
(id_carrera, id_eje, materia_codigo, materia_nombre, materia_ciclo, 
materia_creditos, materia_tipo, materia_tipo_acreditacion)
SELECT mat_carrera, mat_eje, mat_id, mat_nombre, mat_ciclo, mat_creditos,
mat_tipo, mat_tipoacreditacion
FROM public.materias ORDER BY mat_codigo ASC;

DROP TABLE public.materias;

--Se cambia la modalidad de id a lo que corresponde  
UPDATE public."Carreras"
	SET  carrera_modalidad='PRESENCIAL'
	WHERE carrera_modalidad = '1';

UPDATE public."Carreras"
	SET  carrera_modalidad='DUAL'
	WHERE carrera_modalidad = '4';
/*Terminamos de importar materias*/

/*Importamos pre y co requisitos de una materia*/
Copy precorrequisitos("pre_numero", "preco_materia", "preco_tipo", "preco_precorrequisito") 
To 'C:\Backups Postgresql\preCoRequisitosP1.csv' 
delimiters ';' with CSV HEADER;

CREATE TABLE precorrequisitos(
	"pre_numero" integer, 
	"preco_materia" integer, 
	"preco_tipo" character varying(1), 
	"preco_precorrequisito" integer
)WITH(OIDS = FALSE); 

Copy precorrequisitos("pre_numero", "preco_materia", "preco_tipo", "preco_precorrequisito")
From 'C:\Backups Postgresql\preCoRequisitosP1.csv' 
delimiter AS ';' NULL AS '' CSV HEADER;

INSERT INTO public."PreCoRequisitos"(
	id_materia, pre_co_tipo, id_materia_requisito)
	VALUES (?, ?, ?);
	SELECT preco_materia, preco_tipo, preco_precorrequisito
	FROM public.precorrequisitos ORDER BY pre_numero;

DROP TABLE public.precorrequisitos;

--Para pruebas en los requisitos existen datos duplicados 
SELECT preco_numero, preco_materia, preco_tipo, DISTINCT preco_precorrequisito
	FROM public.precorrequisitos WHERE preco_materia = 274;

/*Terminamos de importar pre y co requisitos de una materia*/


INSERT INTO public."Personas"(
	 id_tipo_persona, id_lugar_natal, id_lugar_residencia, 
	 persona_identificacion, 
	 persona_primer_apellido, persona_segundo_apellido, 
	 persona_primer_nombre, persona_segundo_nombre,
	 persona_genero, persona_sexo,
	 persona_estado_civil, persona_etnia, 
	 persona_idioma_raiz, persona_tipo_sangre,
	 persona_telefono, persona_celular, 
	 persona_correo, persona_fecha_registro,
	 persona_discapacidad, persona_calle_principal, 
	 persona_numero_casa, persona_calle_secundaria,
	 persona_referencia, persona_sector,
	 persona_idioma, persona_tipo_residencia, 
	 persona_fecha_nacimiento)
	VALUES ( 2, 1, 1, 
		'0107390270',
		'GARCÍA', 'INGA',
		'JOHNNY', 'GUSTAVO',
		'M', 'M',
		'SOLTERO', 'MESTIZO', 
		'ESPAÑOL', 'ORH+', 
		'0968796010', '0968796010', 
		'GUS199811@GMAIL.COM', '15/2/2019', 
		'false', 'AV. AMERICAS',
		'00-00', 'TURUHUAYCO', 
		'FRENTE A UNA TIENDA', 'BELLA VISTA', 
		'ESPAÑOL', 'ARRENDADA', 
		'24/11/1998');