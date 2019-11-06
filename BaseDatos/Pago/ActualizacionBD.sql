-- Tablas para el pago de matriculas

CREATE SCHEMA pago;

CREATE TABLE pago."Pago"(
  id_pago SERIAL NOT NULL,
  id_comprobante INT NOT NULL,
  id_malla_alumno INT,
  pago_observaciones TEXT,
  pago NUMERIC(8, 4) NOT NULL DEFAULT '0.0',
  pago_numero_matricula INT NOT NULL DEFAULT 2,
  pago_activo BOOLEAN NOT NULL DEFAULT 'false',
  CONSTRAINT pago_pk PRIMARY KEY("id_pago")
) WITH (OIDS = FALSE);

CREATE TABLE pago."Comprobante"(
  id_comprobante SERIAL NOT NULL,
  id_prd_lectivo INT NOT NULL,
  id_almno INT NOT NULL,
  comprobante BYTEA,
  comprobante_total NUMERIC(8, 4) NOT NULL DEFAULT '0.0',
  comprobante_codigo character varying(50) NOT NULL DEFAULT '',
  comprobante_fecha_pago TIMESTAMP NOT NULL DEFAULT now(),
  comprobante_usuario_ingreso character varying(50) NOT NULL DEFAULT 'ADMIN',
  comprobante_activo BOOLEAN NOT NULL DEFAULT 'true',
  CONSTRAINT comprobante_pk PRIMARY KEY("id_comprobante")
) WITH (OIDS = FALSE);



ALTER TABLE pago."Comprobante" ADD CONSTRAINT  "comprobante_fk_periodo_lectivo"
  FOREIGN KEY(id_prd_lectivo) REFERENCES "PeriodoLectivo"(id_prd_lectivo)
    ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE pago."Comprobante" ADD CONSTRAINT  "comprobante__fk__alumno"
  FOREIGN KEY(id_almno) REFERENCES "Alumnos"(id_alumno)
    ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE pago."Pago" ADD CONSTRAINT  "pago__fk__comprobante"
  FOREIGN KEY(id_comprobante) REFERENCES "Comprobante"(id_comprobante)
    ON UPDATE CASCADE ON DELETE CASCADE;
