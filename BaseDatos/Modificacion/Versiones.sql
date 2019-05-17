CREATE TABLE "versiones"(
  id_version serial NOT NULL,
  usu_username VARCHAR(50) NOT NULL,
  version character varying(5),
  nombre character varying(50),
  url character varying(500) NOT NULL,
  notas text NOT NULL DEFAULT,
  fecha TIMESTAMP DEFAULT now(),
  version_activa boolean DEFAULT 'false',
  CONSTRAINT verison_pk PRIMARY KEY("id_version")
) WITH (OIDS = FALSE);
