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

--Revisamos la persona que se repite en docentes
SELECT pro_id, count(pro_id) FROM public.profesores GROUP BY pro_id HAVING count(pro_id) > 1;

--Consultamos a los que se repiten
SELECT * FROM public.profesores WHERE pro_id = '0105112890';
--Borramos uno de los docentes que se repite

DELETE FROM public.profesores
	WHERE pro_codigo = '48';

DELETE FROM public.profesores
	WHERE pro_codigo = '80';

DELETE FROM public.profesores
	WHERE pro_codigo = '130';

DELETE FROM public.profesores
		WHERE pro_codigo = '63';

DELETE FROM public.profesores
		WHERE pro_codigo = '82';

INSERT INTO public."Docentes"
(docente_codigo, docente_otro_trabajo, docente_categoria,
docente_fecha_contrato, docente_tipo_tiempo,
docente_capacitador, id_persona)
SELECT pro_id, pro_otrotrabajo, pro_categoria,
pro_fechacontrato, pro_tipotiempo, pro_capacitador, (
	SELECT id_persona
FROM public."Personas"
WHERE persona_identificacion = pro_id
) FROM public.profesores;

DROP TABLE public."profesores";

UPDATE public."Docentes"
	SET docente_tipo_tiempo = 'TIEMPO COMPLETO'
	WHERE docente_tipo_tiempo = 'C';

UPDATE public."Docentes"
	SET docente_tipo_tiempo='MEDIO TIEMPO'
	WHERE docente_tipo_tiempo = 'M';
