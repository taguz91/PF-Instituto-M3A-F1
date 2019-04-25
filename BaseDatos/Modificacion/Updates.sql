UPDATE public."Personas"
SET persona_identificacion = TRIM(persona_identificacion);

--Actualizamos el nombre de la provincia
UPDATE public."Lugares"
SET lugar_nombre = REPLACE (lugar_nombre, 'PROVINCIA DEL', '')
WHERE id_lugar_referencia = 1;

UPDATE public."Lugares"
SET lugar_nombre = REPLACE (lugar_nombre, 'PROVINCIA DE', '')
WHERE id_lugar_referencia = 1;

UPDATE public."Lugares"
SET lugar_nombre = REPLACE (lugar_nombre, 'L ', '')
WHERE id_lugar_referencia = 1;

UPDATE public."Lugares"
SET lugar_nombre = TRIM(lugar_nombre);

SELECT * FROM public."Lugares"
WHERE id_lugar_referencia = 1;
