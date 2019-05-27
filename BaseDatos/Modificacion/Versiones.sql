CREATE TABLE "Versiones"(
  id_version serial NOT NULL,
  usu_username VARCHAR(50) NOT NULL,
  version character varying(5),
  nombre character varying(50),
  url character varying(500) NOT NULL,
  notas text NOT NULL DEFAULT,
  fecha TIMESTAMP DEFAULT now(),
  version_activa boolean DEFAULT 'true',
  CONSTRAINT verison_pk PRIMARY KEY("id_version")
) WITH (OIDS = FALSE);

--Slect todo
SELECT id_version, usu_username,
version, nombre, url, notas
FROM public."Versiones"
WHERE id_version = ?;

--Select
SELECT id_version, usu_username,
version, nombre
FROM public."Versiones"
WHERE version_activa = true
ORDER BY fecha DESC;

--Insertamos
INSERT INTO public."Versiones"(usu_username,
  version, nombre, url, notas)
  VALUES(?, ?, ?, ?, ?);

--Eliminamos
UPDATE public."Versiones"
  SET version_activa = false
  WHERE id_version = ?;

--Activamos
UPDATE public."Versiones"
  SET version_activa = true
  WHERE id_version = ?;

--Editamos
UPDATE public."Versiones" SET
  usu_username = ?,
  version = ?,
  nombre = ?,
  url = ?,
  notas = ?
  WHERE id_version = ?;
