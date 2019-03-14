Copy (SELECT id_lugar, lugar_codigo, lugar_nombre, lugar_nivel, id_lugar_referencia
	FROM public."Lugares" ORDER BY id_lugar)
To 'C:\Backups Postgresql\BDActual\lugaresC.csv'
WITH csv HEADER

CREATE TABLE "lugaresC"(
  id integer,
  codigo character varying(20),
  nombre character varying(100),
  nivel integer,
  referencia integer
)WITH(OIDS = FALSE);

Copy public."lugaresC"(id, codigo, nombre, nivel, referencia)
From 'C:\Backups Postgresql\BDActual\lugaresC.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

INSERT INTO public."Lugares"(
	lugar_codigo, lugar_nombre, lugar_nivel, id_lugar_referencia)
  SELECT codigo, nombre, nivel, referencia
	FROM public."lugaresC" ORDER BY id;

DROP TABLE public."lugaresC";
