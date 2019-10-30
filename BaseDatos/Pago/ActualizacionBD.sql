-- Tablas para el pago de matriculas

CREATE SCHEMA pago;

CREATE TABLE pago."Pago"(
  id_pago SERIAL NOT NULL,
  id_comprobante INT NOT NULL,
  id_malla_alumno INT NOT NULL,

) WITH (OIDS = FALSE);

CREATE TABLE pago."Comprobante"(
  id_comprobante SERIAL NOT NULL,
  id_almn_carrera INT NOT NULL,
  comprobante BYTEA NOT NULL,
  comprobante_activo BOOLEAN NOT NULL DEFAULT 'true',
  CONSTRAINT comprobante_pk PRIMARY KEY("id_comprobante")
) WITH (OIDS = FALSE);
