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

--Borrar la restriccion not null de una columna 
ALTER TABLE public."Personas" ALTER COLUMN "persona_segundo_apellido" DROP NOT NULL; 

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
From 'C:\Backups Postgresql\materiasP3.csv' 
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

/*Importamos sectores economicos*/
--Importamos el sector economico  

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
	id_sec_economico, sec_economico_codigo, sec_economico_descripcion, sec_economico_activo)
	SELECT sece_codigo, sece_ide, 
	sece_descripcion, sece_activo
	FROM public.sectores; 

DROP TABLE public.sectores;

/*Terminamos de exporat sectores economicos*/

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

--Consultas en la base de datos anterior
--Consultamos todas las personas del base de datos anterior 
SELECT * FROM public.personas ORDER BY per_codigo; 
--Consultamos solo los docentes
SELECT per_codigo, per_identificacion, per_primerapellido, 
per_segundoapellido, per_primernombre, per_segundonombre, 
per_fechanacimiento, per_genero, per_sexo, per_estadocivil,
per_etnia, per_tiposangre, per_telefono, 
per_celular, per_correo, per_fecharegistro,
per_calleprincipal, per_numerocasa, per_callesecundaria, 
per_referencia, per_sector, per_canton, per_parroquiareside,
per_tiporesidencia
FROM public.personas, public.profesores 
WHERE profesores.pro_persona = personas.per_codigo
ORDER BY per_codigo;

--Importamos la consulta a un CSV
Copy (SELECT per_codigo, per_identificacion, per_primerapellido, 
per_segundoapellido, per_primernombre, per_segundonombre, 
per_fechanacimiento, per_genero, per_sexo, per_estadocivil,
per_etnia, per_tiposangre, per_telefono, 
per_celular, per_correo, per_fecharegistro,
per_calleprincipal, per_numerocasa, per_callesecundaria, 
per_referencia, per_sector, per_canton, per_parroquiareside,
per_tiporesidencia
FROM public.personas, public.profesores 
WHERE profesores.pro_persona = personas.per_codigo
ORDER BY per_codigo) 
To 'C:\Backups Postgresql\personasDocentesP2.csv' with CSV HEADER;


CREATE TABLE "personas"(
	"per_codigo" integer, 
	"per_identificacion" character varying(20), 
	"per_primerapellido" character varying(100), 
	"per_segundoapellido" character varying(100), 
	"per_primernombre" character varying(100), 
	"per_segundonombre" character varying(100), 
	"per_fechanacimiento" date, 
	"per_genero" integer, 
	"per_sexo" character varying(1), 
	"per_estadocivil" integer, 
	"per_etnia" integer, 
	"per_tiposangre" character varying(5), 
	"per_telefono" character varying(10), 
	"per_celular" character varying(40), 
	"per_correo" character varying(100), 
	"per_fecharegistro" date, 
	"per_calleprincipal" character varying(400),
	"per_numerocasa" character varying(10), 
	"per_callesecundaria" character varying(200), 
	"per_referencia" character varying(300), 
	"per_sector" character varying(200), 
	"per_canton" integer, 
	"per_parroquiareside" integer, 
	"per_tiporesidencia" character varying(1)
) WITH (OIDS = FALSE); 

ALTER TABLE public."Personas" ALTER COLUMN "id_lugar_natal" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "id_lugar_residencia" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "persona_identificacion" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "persona_telefono" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "persona_numero_casa" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "persona_calle_secundaria" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "persona_calle_principal" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "persona_correo" DROP NOT NULL; 
ALTER TABLE public."Personas" ALTER COLUMN "persona_idioma" DROP NOT NULL; 


Copy personas("per_codigo", "per_identificacion", "per_primerapellido",
	"per_segundoapellido", "per_primernombre", "per_segundonombre",
	"per_fechanacimiento", "per_genero", "per_sexo", "per_estadocivil",
	"per_etnia", "per_tiposangre", "per_telefono", "per_celular",
	"per_correo", "per_fecharegistro", "per_calleprincipal",
	"per_numerocasa", "per_callesecundaria", "per_referencia",
	"per_sector", "per_canton", "per_parroquiareside", "per_tiporesidencia")
From 'C:\Backups Postgresql\personasDocentesP3.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;

--Esa persona tiene un null y me da error al ingresar
UPDATE public.personas
	SET per_parroquiareside=1
	WHERE per_codigo = 857;

--Tambien existe un docente duplicado  
--904 es el id
INSERT INTO public."Personas"(
	id_tipo_persona, id_lugar_natal, 
	id_lugar_residencia, persona_identificacion, 
	persona_primer_apellido, persona_segundo_apellido,
	persona_primer_nombre, persona_segundo_nombre, 
	persona_genero, persona_sexo,
	persona_estado_civil, persona_etnia, persona_idioma_raiz,
	persona_tipo_sangre, persona_fecha_registro,
    persona_idioma, persona_tipo_residencia, 
	persona_fecha_nacimiento, 
	persona_telefono, persona_celular, persona_correo, 
	persona_calle_principal, persona_numero_casa, 
	persona_calle_secundaria, persona_referencia, 
	persona_sector)
SELECT 1, per_canton, 
	per_parroquiareside, per_identificacion,
	per_primerapellido, per_segundoapellido,
	per_primernombre, per_segundonombre, 
	per_genero, per_sexo, 
	per_estadocivil, per_etnia, 'ESPAÑOL', 
	per_tiposangre, per_fecharegistro, 
	'ESPAÑOL', per_tiporesidencia,
	per_fechanacimiento, 
	per_telefono, per_celular, per_correo, 
	per_calleprincipal, per_numerocasa, 
	per_callesecundaria, per_referencia, 
	per_sector
	FROM public.personas;

TRUNCATE TABLE public.personas;

--Consultamos estudiantes 
Copy (SELECT per_codigo, per_identificacion, per_primerapellido, 
per_segundoapellido, per_primernombre, per_segundonombre, 
per_fechanacimiento, per_genero, per_sexo, per_estadocivil,
per_etnia, per_tiposangre, per_telefono, 
per_celular, per_correo, per_fecharegistro,
per_calleprincipal, per_numerocasa, per_callesecundaria, 
per_referencia, per_sector, per_canton, per_parroquiareside,
per_tiporesidencia
FROM public.personas, public.alumnos 
WHERE alumnos.alu_persona = personas.per_codigo
ORDER BY per_codigo)
To 'C:\Backups Postgresql\personasAlumnosP3.csv' with CSV HEADER

Copy personas("per_codigo", "per_identificacion", "per_primerapellido",
	"per_segundoapellido", "per_primernombre", "per_segundonombre",
	"per_fechanacimiento", "per_genero", "per_sexo", "per_estadocivil",
	"per_etnia", "per_tiposangre", "per_telefono", "per_celular",
	"per_correo", "per_fecharegistro", "per_calleprincipal",
	"per_numerocasa", "per_callesecundaria", "per_referencia",
	"per_sector", "per_canton", "per_parroquiareside", "per_tiporesidencia")
From 'C:\Backups Postgresql\personasAlumnosP3.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;

INSERT INTO public."Personas"(
	id_tipo_persona, id_lugar_natal, 
	id_lugar_residencia, persona_identificacion, 
	persona_primer_apellido, persona_segundo_apellido,
	persona_primer_nombre, persona_segundo_nombre, 
	persona_genero, persona_sexo,
	persona_estado_civil, persona_etnia, persona_idioma_raiz,
	persona_tipo_sangre, persona_fecha_registro,
    persona_idioma, persona_tipo_residencia, 
	persona_fecha_nacimiento, 
	persona_telefono, persona_celular, persona_correo, 
	persona_calle_principal, persona_numero_casa, 
	persona_calle_secundaria, persona_referencia, 
	persona_sector)
SELECT 2, per_canton, 
	per_parroquiareside, per_identificacion,
	per_primerapellido, per_segundoapellido,
	per_primernombre, per_segundonombre, 
	per_genero, per_sexo, 
	per_estadocivil, per_etnia, 'ESPAÑOL', 
	per_tiposangre, per_fecharegistro, 
	'ESPAÑOL', per_tiporesidencia,
	per_fechanacimiento, 
	per_telefono, per_celular, per_correo, 
	per_calleprincipal, per_numerocasa, 
	per_callesecundaria, per_referencia, 
	per_sector
	FROM public.personas;


DROP TABLE public.personas;

--Updates a persona 

UPDATE public."Personas"
	SET persona_genero='MASCULINO'
	WHERE persona_genero = '1';

UPDATE public."Personas"
	SET persona_genero='FEMENINO'
	WHERE persona_genero = '2';

UPDATE public."Personas"
	SET persona_genero='LGBTI'
	WHERE persona_genero = '3';

UPDATE public."Personas"
	SET persona_estado_civil='SOLTERO/A'
	WHERE persona_estado_civil = '1';
	
UPDATE public."Personas"
	SET persona_estado_civil='CASADO/A'
	WHERE persona_estado_civil = '2';

UPDATE public."Personas"
	SET persona_estado_civil='DIVORCIADO/A'
	WHERE persona_estado_civil = '3';

UPDATE public."Personas"
	SET persona_estado_civil='UNION LIBRE'
	WHERE persona_estado_civil = '4';

UPDATE public."Personas"
	SET persona_estado_civil='VIUDO/A'
	WHERE persona_estado_civil = '5';

UPDATE public."Personas"
	SET persona_etnia='INDIGENA'
	WHERE persona_etnia = '1';

UPDATE public."Personas"
	SET persona_etnia='AFROECUATORIANO'
	WHERE persona_etnia = '2';		

UPDATE public."Personas"
	SET persona_etnia='NEGRO'
	WHERE persona_etnia = '3';	

UPDATE public."Personas"
	SET persona_etnia='MULATO'
	WHERE persona_etnia = '4';

UPDATE public."Personas"
	SET persona_etnia='MONTUVIO'
	WHERE persona_etnia = '5';	

UPDATE public."Personas"
	SET persona_etnia='MESTIZO'
	WHERE persona_etnia = '6';		


--Importamos docentes 
Copy profesores(pro_codigo, pro_persona, pro_id, pro_nivel, 
				pro_otrotrabajo, pro_categoria, 
				pro_fechacontrato, pro_capacitador, 
				pro_fechafin, pro_tipotiempo)
TO 'C:\Backups Postgresql\profesoresP1.csv' 
delimiters ';' with CSV HEADER;

CREATE TABLE "profesores"(
	pro_codigo integer, 
	pro_persona integer, 
	pro_id character varying(10), 
	pro_nivel integer, 
	pro_otrotrabajo boolean, 
	pro_categoria integer, 
	pro_fechacontrato date, 
	pro_capacitador boolean,  
	pro_fechafin date, 
	pro_tipotiempo character varying(10)
) WITH (OIDS = FALSE); 

Copy profesores(pro_codigo, pro_persona, pro_id, pro_nivel, 
				pro_otrotrabajo, pro_categoria, 
				pro_fechacontrato, pro_capacitador, 
				pro_fechafin, pro_tipotiempo)
From 'C:\Backups Postgresql\profesoresP1.csv' 
delimiter AS ';' NULL AS '' CSV HEADER;

INSERT INTO public."Docentes"
(docente_codigo, docente_otro_trabajo, docente_categoria, 
docente_fecha_contrato, docente_tipo_tiempo, 
docente_capacitador, id_persona)
SELECT pro_id, pro_otrotrabajo, pro_categoria,
pro_fechacontrato, pro_tipotiempo, pro_capacitador, (
	SELECT id_persona 
FROM public."Personas"
WHERE persona_identificacion = '0103574257'
) FROM public.profesores 
WHERE pro_id = '9398956254';

DROP TABLE public."profesores";


--Copiamos alumnos 
Copy alumnos(alu_codigo, alu_id, 
			 alu_tipocolegio, alu_tipobachillerato,
			 alu_aniograduacion, alu_educacionsuperior, 
			 alu_titulosuperior, alu_nivelacademico, 
			 alu_pension, alu_ocupacion, alu_trabaja,
			 alu_sectoreconomico, 
			 alu_nivelformacionpadre, 
			 alu_nivelformacionmadre, alu_contactoemergencia, 
			 alu_parentescocontacto, alu_numerocontacto)
To 'C:\Backups Postgresql\alumnosP1.csv' 
delimiters ';' with CSV HEADER;

CREATE TABLE "estudiantes"(
	alu_codigo integer, 
	alu_id character varying(20), 
	alu_tipocolegio integer, 
	alu_tipobachillerato integer, 
	alu_aniograduacion integer,
	alu_educacionsuperior boolean,
	alu_titulosuperior character varying(200), 
	alu_nivelacademico integer,  
	alu_pension character varying(5),
	alu_ocupacion character varying(200), 
	alu_trabaja boolean, 
	alu_sectoreconomico integer, 
	alu_nivelformacionpadre integer, 
	alu_nivelformacionmadre integer, 
	alu_contactoemergencia character varying(100), 
	alu_parentescocontacto integer, 
	alu_numerocontacto character varying(20)
) WITH (OIDS = FALSE); 


ALTER TABLE public."Alumnos" ALTER COLUMN "alumno_ocupacion" DROP NOT NULL; 
ALTER TABLE public."Alumnos" ALTER COLUMN "alumno_nivel_formacion_padre" DROP NOT NULL; 
ALTER TABLE public."Alumnos" ALTER COLUMN "alumno_nivel_formacion_madre" DROP NOT NULL; 
ALTER TABLE public."Alumnos" ALTER COLUMN "id_sec_economico" DROP NOT NULL; 
ALTER TABLE public."Alumnos" ALTER COLUMN "alumno_numero_contacto" TYPE character varying(20); 

Copy estudiantes(alu_codigo, alu_id, 
			 alu_tipocolegio, alu_tipobachillerato,
			 alu_aniograduacion, alu_educacionsuperior, 
			 alu_titulosuperior, alu_nivelacademico, 
			 alu_pension, alu_ocupacion, alu_trabaja,
			 alu_sectoreconomico, alu_nivelformacionpadre, 
			 alu_nivelformacionmadre, alu_contactoemergencia, 
			 alu_parentescocontacto, alu_numerocontacto)
From 'C:\Backups Postgresql\alumnosP1.csv' 
delimiter AS ';' NULL AS '' CSV HEADER;


INSERT INTO public."Alumnos"(
	id_sec_economico, alumno_tipo_colegio, alumno_tipo_bachillerato,
	alumno_anio_graduacion, alumno_educacion_superior, alumno_titulo_superior, 
	alumno_nivel_academico, alumno_ocupacion, alumno_trabaja,
	alumno_nivel_formacion_padre, alumno_nivel_formacion_madre,
	alumno_nombre_contacto_emergencia, alumno_parentesco_contacto,
	alumno_numero_contacto, id_persona)
SELECT  alu_sectoreconomico, alu_tipocolegio, alu_tipobachillerato, alu_aniograduacion,
 alu_educacionsuperior, alu_titulosuperior, alu_nivelacademico, 
 alu_ocupacion, alu_trabaja, 
 alu_nivelformacionpadre, alu_nivelformacionmadre, alu_contactoemergencia, 
 alu_parentescocontacto, alu_numerocontacto, (
 	SELECT id_persona 
	FROM public."Personas"
	WHERE persona_identificacion = '0106777402'
)FROM public.estudiantes;
 WHERE alu_id = '0106777402';


DROP TABLE public.estudiantes;


UPDATE public."Alumnos"
	SET alumno_tipo_colegio= 'FISCAL'
	WHERE alumno_tipo_colegio = '1';

UPDATE public."Alumnos"
	SET alumno_tipo_colegio= 'FISCOMISIONAL'
	WHERE  alumno_tipo_colegio = '2';

UPDATE public."Alumnos"
	SET alumno_tipo_colegio= 'PARTICULAR'
	WHERE  alumno_tipo_colegio = '3';

UPDATE public."Alumnos"
	SET alumno_tipo_colegio= 'MUNICIPAL'
	WHERE  alumno_tipo_colegio = '4';

UPDATE public."Alumnos"
	SET alumno_tipo_colegio= 'EXTRANJERO'
	WHERE  alumno_tipo_colegio = '5';

UPDATE public."Alumnos"
	SET alumno_tipo_colegio= 'NO REGISTRA'
	WHERE  alumno_tipo_colegio = '6';

	

UPDATE public."Alumnos"
	SET alumno_tipo_bachillerato= 'TECNICO'
	WHERE  alumno_tipo_bachillerato = '1';

UPDATE public."Alumnos"
	SET alumno_tipo_bachillerato= 'TECNICO PRODUCTIVO'
	WHERE  alumno_tipo_bachillerato = '2';

UPDATE public."Alumnos"
	SET alumno_tipo_bachillerato= 'BGU'
	WHERE  alumno_tipo_bachillerato = '3';

UPDATE public."Alumnos"
	SET alumno_tipo_bachillerato= 'BI'
	WHERE  alumno_tipo_bachillerato = '4';

UPDATE public."Alumnos"
	SET alumno_tipo_bachillerato= 'OTRO'
	WHERE  alumno_tipo_bachillerato = '5';

	

UPDATE public."Alumnos"
	SET alumno_nivel_academico= 'NINGUNO'
	WHERE  alumno_nivel_academico = '1';

UPDATE public."Alumnos"
	SET alumno_nivel_academico= 'PRIMARIA'
	WHERE  alumno_nivel_academico = '2';

UPDATE public."Alumnos"
	SET alumno_nivel_academico= 'SECUNDARIA'
	WHERE  alumno_nivel_academico = '3';

UPDATE public."Alumnos"
	SET alumno_nivel_academico= 'SUPERIOR'
	WHERE  alumno_nivel_academico = '4';

UPDATE public."Alumnos"
	SET alumno_nivel_academico= 'OTROS'
	WHERE  alumno_nivel_academico = '5';



UPDATE public."Alumnos"
	SET alumno_nivel_formacion_padre= 'NINGUNO'
	WHERE  alumno_nivel_formacion_padre = '1';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_padre= 'PRIMARIA'
	WHERE  alumno_nivel_formacion_padre = '2';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_padre= 'SECUNDARIA'
	WHERE  alumno_nivel_formacion_padre = '3';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_padre= 'SUPERIOR'
	WHERE  alumno_nivel_formacion_padre = '4';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_padre= 'OTROS'
	WHERE  alumno_nivel_formacion_padre = '5';



UPDATE public."Alumnos"
	SET alumno_nivel_formacion_madre= 'NINGUNO'
	WHERE  alumno_nivel_formacion_madre = '1';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_madre= 'PRIMARIA'
	WHERE  alumno_nivel_formacion_madre = '2';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_madre= 'SECUNDARIA'
	WHERE  alumno_nivel_formacion_madre = '3';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_madre= 'SUPERIOR'
	WHERE  alumno_nivel_formacion_madre = '4';

UPDATE public."Alumnos"
	SET alumno_nivel_formacion_madre= 'OTROS'
	WHERE  alumno_nivel_formacion_madre = '5';



UPDATE public."Alumnos"
	SET alumno_parentesco_contacto= 'PADRE'
	WHERE  alumno_parentesco_contacto = '1';

UPDATE public."Alumnos"
	SET alumno_parentesco_contacto= 'MADRE'
	WHERE  alumno_parentesco_contacto = '2';

UPDATE public."Alumnos"
	SET alumno_parentesco_contacto= 'HERMANO/A'
	WHERE  alumno_parentesco_contacto = '3';

UPDATE public."Alumnos"
	SET alumno_parentesco_contacto= 'ESPOSO/A'
	WHERE  alumno_parentesco_contacto = '4';

UPDATE public."Alumnos"
	SET alumno_parentesco_contacto= 'OTRO'
	WHERE  alumno_parentesco_contacto = '5';


