--Importamos todas las personas 
Copy (SELECT per_codigo, per_identificacion, per_primerapellido, per_segundoapellido,
per_primernombre, per_segundonombre, per_fechanacimiento, per_genero, per_sexo,
per_estadocivil, per_etnia, per_tiposangre, per_telefono,
per_celular, per_correo, per_fecharegistro, per_calleprincipal,
per_numerocasa, per_callesecundaria, per_referencia, per_sector,
per_canton, per_parroquiareside, per_tiporesidencia
FROM public.personas ORDER BY per_codigo)
To 'C:\Backups Postgresql\personasP1.csv' with CSV HEADER;


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
	"per_telefono" character varying(40), 
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
--24 datos 

Copy personas("per_codigo", "per_identificacion", "per_primerapellido",
	"per_segundoapellido", "per_primernombre", "per_segundonombre",
	"per_fechanacimiento", "per_genero", "per_sexo", "per_estadocivil",
	"per_etnia", "per_tiposangre", "per_telefono", "per_celular",
	"per_correo", "per_fecharegistro", "per_calleprincipal",
	"per_numerocasa", "per_callesecundaria", "per_referencia",
	"per_sector", "per_canton", "per_parroquiareside", "per_tiporesidencia")
From 'C:\Backups Postgresql\personasP1.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;


INSERT INTO public."Personas"(
	id_lugar_natal, 
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
SELECT per_canton, 
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

