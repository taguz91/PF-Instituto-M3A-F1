--Copiamos alumnos
Copy (SELECT alu_codigo, per_identificacion,
			 alu_tipocolegio, alu_tipobachillerato,
			 alu_aniograduacion, alu_educacionsuperior,
			 alu_titulosuperior, alu_nivelacademico,
			 alu_pension, alu_ocupacion, alu_trabaja,
			 alu_sectoreconomico,
			 alu_nivelformacionpadre,
			 alu_nivelformacionmadre, alu_contactoemergencia,
			 alu_parentescocontacto, alu_numerocontacto
FROM public.alumnos, public.personas
WHERE alu_persona = per_codigo)
To 'C:\Backups Postgresql\alumnosP2.csv' with CSV HEADER;

CREATE TABLE "estudiantes"(
	alu_codigo integer,
	alu_identifiacion character varying(20),
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

Copy estudiantes(alu_codigo, alu_identifiacion,
			 alu_tipocolegio, alu_tipobachillerato,
			 alu_aniograduacion, alu_educacionsuperior,
			 alu_titulosuperior, alu_nivelacademico,
			 alu_pension, alu_ocupacion, alu_trabaja,
			 alu_sectoreconomico, alu_nivelformacionpadre,
			 alu_nivelformacionmadre, alu_contactoemergencia,
			 alu_parentescocontacto, alu_numerocontacto)
From 'C:\Backups Postgresql\alumnosP2.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

--Consultamos si existen alumnos repetidos
select alu_identifiacion, count(alu_identifiacion)
from public.estudiantes GROUP BY alu_identifiacion
HAVING count(alu_identifiacion) > 1;

--Debemos actualizar el sector economivo
UPDATE public.estudiantes
	SET alu_sectoreconomico = alu_sectoreconomico - 1
	WHERE alu_sectoreconomico > 7;


INSERT INTO public."Alumnos"(
	id_sec_economico, alumno_codigo, alumno_tipo_colegio, alumno_tipo_bachillerato,
	alumno_anio_graduacion, alumno_educacion_superior, alumno_titulo_superior,
	alumno_nivel_academico, alumno_ocupacion, alumno_trabaja,
	alumno_nivel_formacion_padre, alumno_nivel_formacion_madre,
	alumno_nombre_contacto_emergencia, alumno_parentesco_contacto,
	alumno_numero_contacto, id_persona)
SELECT  alu_sectoreconomico, alu_identifiacion, alu_tipocolegio, alu_tipobachillerato, alu_aniograduacion,
 alu_educacionsuperior, alu_titulosuperior, alu_nivelacademico,
 alu_ocupacion, alu_trabaja,
 alu_nivelformacionpadre, alu_nivelformacionmadre, alu_contactoemergencia,
 alu_parentescocontacto, alu_numerocontacto, (
 	SELECT id_persona
	FROM public."Personas"
	WHERE persona_identificacion = alu_identifiacion
)FROM public.estudiantes;


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
