-- Tablas para el pago de matriculas

CREATE SCHEMA pago;

CREATE TABLE pago."PagoMateria"(
  id_pago_materia SERIAL NOT NULL,
  id_comprobante INT NOT NULL,
  id_malla_alumno INT,
  pago_materia NUMERIC(8, 4) NOT NULL DEFAULT '0.0',
  pago_numero_matricula INT NOT NULL DEFAULT 2,
  pago_materia_activo BOOLEAN NOT NULL DEFAULT 'false',
  CONSTRAINT pago_materia_pk PRIMARY KEY("id_pago_materia")
) WITH (OIDS = FALSE);

CREATE TABLE pago."ComprobantePago"(
  id_comprobante_pago SERIAL NOT NULL,
  id_prd_lectivo INT NOT NULL,
  id_alumno INT NOT NULL,
  comprobante BYTEA,
  comprobante_total NUMERIC(8, 4) NOT NULL DEFAULT '0.0',
  comprobante_codigo character varying(50) NOT NULL DEFAULT '',
  comprobante_fecha_pago TIMESTAMP NOT NULL DEFAULT now(),
  comprobante_observaciones TEXT DEFAULT '',
  comprobante_usuario_ingreso character varying(50) NOT NULL DEFAULT 'ADMIN',
  comprobante_activo BOOLEAN NOT NULL DEFAULT 'true',
  CONSTRAINT comprobante_pk PRIMARY KEY("id_comprobante_pago")
) WITH (OIDS = FALSE);



ALTER TABLE pago."Comprobante" ADD CONSTRAINT  "comprobante_fk_periodo_lectivo"
  FOREIGN KEY(id_prd_lectivo) REFERENCES "PeriodoLectivo"(id_prd_lectivo)
    ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE pago."Comprobante" ADD CONSTRAINT  "comprobante__fk__alumno"
  FOREIGN KEY(id_alumno) REFERENCES "Alumnos"(id_alumno)
    ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE pago."Pago" ADD CONSTRAINT  "pago__fk__comprobante"
  FOREIGN KEY(id_comprobante) REFERENCES "Comprobante"(id_comprobante)
    ON UPDATE CASCADE ON DELETE CASCADE;
