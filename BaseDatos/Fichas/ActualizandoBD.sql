CREATE TABLE "TipoFicha" (
  id_tipo_ficha serial NOT NULL,
  tipo_ficha character varying(50) NOT NULL,
  tipo_ficha_descripcion TEXT,
  tipo_ficha_activo boolean NOT NULL DEFAULT 'true',
  CONSTRAINT tipo_ficha_pk PRIMARY KEY ("id_tipo_ficha")
) WITH (OIDS = FALSE);

CREATE TABLE "GrupoSocioeconomico" (
  id_grupo_socioeconomico serial NOT NULL,
  id_tipo_ficha int NOT NULL,
  grupo_socioeconomico character varying(50),
  puntaje_minimo numeric(5, 2),
  puntaje_maximo numeric(5, 2),
  grupo_socioeconomico_activo boolean,
  CONSTRAINT grupo_socioeconomico_pk PRIMARY KEY ("id_grupo_socioeconomico")
) WITH (OIDS = FALSE);

CREATE TABLE "SeccionesFicha" (
  id_seccion_ficha serial NOT NULL,
  id_tipo_ficha int NOT NULL,
  seccion_ficha_nombre character varying(255),
  seccion_ficha_activa boolean,
  CONSTRAINT seccion_ficha_pk PRIMARY KEY ("id_seccion_ficha")
) WITH (OIDS = FALSE);

CREATE TABLE "PreguntasFicha" (
  id_pregunta_ficha serial NOT NULL,
  id_seccion_ficha int NOT NULL,
  pregunta_ficha character varying(255) NOT NULL,
  --Actualizacion 12/7/2019
  pregunta_ficha_ayuda TEXT NOT NULL DEFAULT 'Sin ayuda',
  -->
  pregunta_ficha_activa BOOLEAN NOT NULL DEFAULT 'true',
  CONSTRAINT pregunta_ficha_pk PRIMARY KEY ("id_pregunta_ficha")
) WITH (OIDS = FALSE);

CREATE TABLE "RespuestaFicha" (
  id_respuesta_ficha serial NOT NULL,
  id_pregunta_ficha int NOT NULL,
  respuesta_ficha character varying(255) NOT NULL,
  respuesta_ficha_puntaje int NOT NULL DEFAULT '0',
  respuesta_ficha_activa BOOLEAN NOT NULL DEFAULT 'true',
  CONSTRAINT respuesta_ficha_pk PRIMARY KEY ("id_respuesta_ficha")
) WITH (OIDS = FALSE);

CREATE TABLE "PermisoIngresoFichas" (
  id_permiso_ingreso_ficha serial NOT NULL,
  id_prd_lectivo int NOT NULL,
  id_tipo_ficha int NOT NULL,
  permiso_ingreso_fecha_inicio date NOT NULL,
  permiso_ingreso_fecha_fin date NOT NULL,
  permiso_ingreso_activo BOOLEAN DEFAULT 'true',
  CONSTRAINT permiso_ingreso_ficha_pk PRIMARY KEY ("id_permiso_ingreso_ficha")
) WITH (OIDS = FALSE);

CREATE TABLE "PersonaFicha" (
  id_persona_ficha serial NOT NULL,
  id_permiso_ingreso_ficha int NOT NULL,
  id_persona int NOT NULL,
  persona_ficha_clave bytea NOT NULL,
  persona_ficha_fecha_ingreso date,
  persona_ficha_fecha_modificacion date,
  persona_ficha_activa BOOLEAN DEFAULT 'true',
  CONSTRAINT persona_ficha_pk PRIMARY KEY ("id_persona_ficha")
) WITH (OIDS = FALSE);

CREATE TABLE "DocenteRespuestaFO" (
  id_docente_respuesta_fo serial NOT NULL,
  id_persona_ficha int NOT NULL,
  id_pregunta_ficha int NOT NULL,
  docente_fo_respuesta character varying(255) DEFAULT '',
  docente_fo_fecha_ingreso DATE default now(),
  docente_fo_activo BOOLEAN NOT NULL,
  CONSTRAINT docente_respuesta_fo PRIMARY KEY ("id_docente_respuesta_fo")
) WITH (OIDS = FALSE);

CREATE TABLE "AlumnoRespuestaFS" (
  id_almn_respuesta_fs serial NOT NULL,
  id_persona_ficha int NOT NULL,
  id_respuesta_ficha int NOT NULL,
  respuesta_almn_puntaje int DEFAULT '0',
  respuesta_almn_fecha_ingreso date DEFAULT now(),
  respuesta_almn_activo boolean DEFAULT 'true',
  CONSTRAINT almn_respuesta_fs PRIMARY KEY ("id_almn_respuesta_fs")
) WITH (OIDS = FALSE);

ALTER TABLE "GrupoSocioeconomico" ADD CONSTRAINT
"tipo_ficha_grupo_socioeconomico_fk"
FOREIGN KEY ("id_tipo_ficha") REFERENCES "TipoFicha"("id_tipo_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;

--Actualizacion 12/7/2019
CREATE TABLE "AlumnoRespuestaLibreFS" (
  id_almn_respuesta_libre_fs serial NOT NULL,
  id_persona_ficha int NOT NULL,
  id_pregunta_ficha int NOT NULL,
  alumno_fs_libre int DEFAULT '0',
  alumno_fs_fecha_ingreso date DEFAULT now(),
  alumno_fs_activo boolean DEFAULT 'true',
  CONSTRAINT almn_respuesta_libre_fs PRIMARY KEY ("id_almn_respuesta_libre_fs")
) WITH (OIDS = FALSE);

ALTER TABLE "SeccionesFicha" ADD CONSTRAINT
"secciones_ficha_tipo"
FOREIGN KEY ("id_tipo_ficha") REFERENCES "TipoFicha"("id_tipo_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "PreguntasFicha" ADD CONSTRAINT
"pregunta_seccion_ficha"
FOREIGN KEY ("id_seccion_ficha") REFERENCES "SeccionesFicha"("id_seccion_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "RespuestaFicha" ADD CONSTRAINT
"respuesta_seccion_ficha"
FOREIGN KEY ("id_pregunta_ficha") REFERENCES "PreguntasFicha"("id_pregunta_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "PermisoIngresoFichas" ADD CONSTRAINT
"tipo_ficha_permiso_ingreso_fk"
FOREIGN KEY ("id_tipo_ficha") REFERENCES "TipoFicha"("id_tipo_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "PermisoIngresoFichas" ADD CONSTRAINT
"periodo_permiso_ingreso_fk"
FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "PersonaFicha" ADD CONSTRAINT
"persona_ficha_permiso_ingreso_fk"
FOREIGN KEY ("id_permiso_ingreso_ficha") REFERENCES "PermisoIngresoFichas"("id_permiso_ingreso_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "PersonaFicha" ADD CONSTRAINT
"persona_ficha_fk"
FOREIGN KEY ("id_persona") REFERENCES "Personas"("id_persona")
ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "DocenteRespuestaFO" ADD CONSTRAINT
"persona_ficha_docente_respuesta_fk"
FOREIGN KEY ("id_persona_ficha") REFERENCES "PersonaFicha"("id_persona_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "DocenteRespuestaFO" ADD CONSTRAINT
"pregunta_docente_respuesta_fk"
FOREIGN KEY ("id_pregunta_ficha") REFERENCES "PreguntasFicha"("id_pregunta_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "AlumnoRespuestaFS" ADD CONSTRAINT
"persona_ficha_alumno_respuesta_fk"
FOREIGN KEY ("id_persona_ficha") REFERENCES "PersonaFicha"("id_persona_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "AlumnoRespuestaFS" ADD CONSTRAINT
"respuesta_alumno_respuesta_fk"
FOREIGN KEY ("id_respuesta_ficha") REFERENCES "RespuestaFicha"("id_respuesta_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;


--Actualizacion 12/7/2019
ALTER TABLE "AlumnoRespuestaLibreFS" ADD CONSTRAINT
"persona_almn_respuesta_libre_fk"
FOREIGN KEY ("id_persona_ficha") REFERENCES
"PersonaFicha"("id_persona_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE "AlumnoRespuestaLibreFS" ADD CONSTRAINT
"pregunta_almn_respuesta_libre_fk"
FOREIGN KEY ("id_pregunta_ficha") REFERENCES "PreguntasFicha"("id_pregunta_ficha")
ON DELETE CASCADE ON UPDATE CASCADE;
