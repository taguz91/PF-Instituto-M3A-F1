CREATE TABLE "PeriodoLectivo"(
 "id_prd_lectivo" serial NOT NULL,
 "id_carrera" integer NOT NULL,
 "prd_lectivo_nombre" character varying(50) NOT NULL,
 "prd_lectivo_fecha_inicio" date NOT NULL,
 "prd_lectivo_fecha_fin" date NOT NULL,
 "prd_lectivo_observacion" character varying(200) NOT NULL,
 "prd_lectivo_activo" boolean NOT NULL,
 CONSTRAINT periodolectivo_pk PRIMARY KEY ("id_prd_lectivo")
) WITH (OIDS = FALSE);

--Tipo de persona
CREATE TABLE "TipoPersona"(
  "id_tipo_persona" serial NOT NULL,
  "tipo_persona" character varying(20),
  "tipo_persona_activo" BOOLEAN DEFAULT 'true',
  CONSTRAINT tipo_persona_pk PRIMARY KEY ("id_tipo_persona")
) WITH (OIDS = FALSE);

--Persona
CREATE TABLE "Personas"(
  "id_persona" serial NOT NULL,
  "id_tipo_persona" integer NOT NULL,
  "id_lugar_natal" integer NOT NULL,
  "id_lugar_residencia" integer NOT NULL,
  "persona_foto" bytea,
  "persona_identificacion" character varying(20) NOT NULL,
  "persona_primer_apellido" character varying(25) NOT NULL,
  "persona_segundo_apellido" character varying(25) NOT NULL,
  "persona_primer_nombre" character varying(25) NOT NULL,
  "persona_segundo_nombre" character varying(25) NOT NULL,
  "persona_genero" character varying(10) NOT NULL,
  "persona_sexo" character varying(1) NOT NULL,
  "persona_estado_civil" character varying(25) NOT NULL,
  "persona_etnia" character varying(50) NOT NULL,
  "persona_idioma_raiz" character varying(30) NOT NULL,
  "persona_tipo_sangre" character varying(5) NOT NULL,
  "persona_telefono" character varying(10) NOT NULL,
  "persona_celular" character varying(10) NOT NULL,
  "persona_correo" character varying(30) NOT NULL,
  "persona_fecha_registro" date NOT NULL,
  "persona_discapacidad" boolean NOT NULL DEFAULT 'false',
  "persona_tipo_discapacidad" character varying(20) NOT NULL,
  "persona_porcenta_discapacidad" integer NOT NULL DEFAULT '0',
  "persona_carnet_conadis" character varying(10),
  "persona_calle_principal" character varying(120) NOT NULL,
  "persona_numero_casa" character varying(10) NOT NULL,
  "persona_calle_secundaria" character varying(150) NOT NULL,
  "persona_referencia" character varying(200) NOT NULL,
  "persona_sector" character varying(200) NOT NULL,
  "persona_idioma" character varying(50) NOT NULL,
  "persona_tipo_residencia" character varying(30) NOT NULL,
  CONSTRAINT persona_pk PRIMARY KEY ("id_persona")
) WITH (OIDS = FALSE);

/*
Scrip
*/

--Materias
CREATE TABLE "Materias"(
	"id_materia" serial NOT NULL,
	"id_carrera" INTEGER NOT NULL,
	"materia_codigo" character varying(10) NOT NULL,
	"materia_nombre" character varying(100) NOT NULL,
	"materia_ciclo" INTEGER NOT NULL,
	"materia_creditos" INTEGER NOT NULL,
	"materia_tipo" character varying(1),
	"materia_categoria" character varying(50),
	"materia_eje" character varying(100), 
	"materia_tipo_acreditacion" character varying(1) NOT NULL,
	"materia_horas_teoricas" INTEGER NOT NULL DEFAULT '0',
	"materia_horas_practicas" INTEGER NOT NULL DEFAULT '0',
	"materia_horas_auto_estudio" INTEGER NOT NULL DEFAULT '0',
	"materia_total_horas" integer NOT NULL DEFAULT '0',
	"materia_activa" BOOLEAN NOT NULL DEFAULT 'true',
	"materia_objetivo" character varying(200) NOT NULL DEFAULT 'Sin objetivo', 
	"materia_descripcion" character varying(200) NOT NULL DEFAULT 'Sin descripcion', 
	CONSTRAINT materia_pk PRIMARY KEY ("id_materia")
) WITH (OIDS = FALSE);

--Alumnos
CREATE TABLE "Alumnos"(
	"id_alumno" serial NOT NULL,
	"id_persona" INTEGER NOT NULL UNIQUE, 
	"id_carrera" INTEGER NOT NULL,
	"alumno_codigo" character varying(10) NOT NULL,
	"alumno_tipo_colegio" character varying(30) NOT NULL,
	"alumno_tipo_bachillerato" character varying(100) NOT NULL,
	"alumno_anio_graduacion" character varying(4) NOT NULL,
	"alumno_educacion_superior" BOOLEAN NOT NULL DEFAULT 'false',
	"alumno_titulo_superior" character varying(200) NOT NULL,
	"alumno_nivel_academico" character varying(50) NOT NULL,
	"alumno_pension" character varying(2) NOT NULL DEFAULT 'No',
	"alumno_ocupacion" character varying(100) NOT NULL,
	"alumno_trabaja" BOOLEAN NOT NULL DEFAULT 'false',
	"alumno_sector_economico" character varying(100) NOT NULL,
	"alumno_nivel_formacion_padre" character varying(100) NOT NULL,
	"alumno_nivel_formacion_madre" character varying(100) NOT NULL,
	"alumno_nombre_contacto_emergencia" character varying(100) NOT NULL,
	"alumno_parentesco_contacto" character varying(20) NOT NULL,
	"alumno_numero_contacto" character varying(10) NOT NULL,
	CONSTRAINT alumno_pk PRIMARY KEY ("id_alumno")
) WITH (OIDS = FALSE);

--carrera
CREATE TABLE "Carreras"(
  "id_carrera" serial NOT NULL,
  "id_docente_coordinador" integer, 
  "carrera_nombre" character varying(100) NOT NULL,
  "carrera_codigo" character varying(15) NOT NULL,
  "carrera_fecha_inicio" DATE NOT NULL,
  "carrera_fecha_fin" DATE,
  "carrera_modalidad" character varying(20) NOT NULL,
  "carrera_activo" boolean DEFAULT 'true', 
  CONSTRAINT carrera_pk PRIMARY KEY ("id_carrera")
)WITH(OIDS = false);


--Docente
CREATE TABLE "Docentes"(
  "id_docente" serial NOT NULL,
  "id_persona" integer NOT NULL UNIQUE,
  "docente_codigo" character varying(10),
  "docente_otro_trabajo" boolean DEFAULT 'false',
  "docente_categoria" integer,
  "docente_fecha_contrato" date,
  "docente_capacitador" boolean,
  "docente_fecha_fin" date,
  "docente_tipo_tiempo" character varying(1),
  "docente_activo" boolean DEFAULT 'true',
  CONSTRAINT docente_pk PRIMARY KEY ("id_docente")
) WITH (OIDS = false);

--Lugar
CREATE TABLE "Lugares"(
  "id_lugar" serial NOT NULL,
  "lugar_codigo" character varying(20) NOT NULL,
  "lugar_nombre" character varying(100) NOT NULL,
  "lugar_nivel" integer NOT NULL,
  "id_lugar_referencia" integer,
  CONSTRAINT lugar_pk PRIMARY KEY ("id_lugar")
) WITH (OIDS = FALSE);

--Cursos
CREATE TABLE "Cursos"(
  "id_curso" serial NOT NULL,
  "id_materia" integer NOT NULL,
  "id_prd_lectivo" integer NOT NULL,
  "id_docente" integer NOT NULL,
  "curso_nombre" character varying(10) NOT NULL,
  "curso_jornada" character varying(20) NOT NULL,
  "curso_capacidad" integer NOT NULL,
  "curso_ciclo" integer NOT NULL,
  "curso_permiso_ingreso_nt" boolean NOT NULL DEFAULT 'false', 
  CONSTRAINT curso_pk PRIMARY KEY ("id_curso")
) WITH (OIDS = FALSE);

--AlumnoCurso
CREATE TABLE "AlumnoCurso"(
  "id_almn_curso" serial NOT NULL,
  "id_alumno" integer NOT NULL,
  "id_curso" integer NOT NULL,
  "almn_curso_nt_1_parcial" numeric(3, 2) DEFAULT '0',
  "almn_curso_nt_examen_interciclo" numeric(3, 2) DEFAULT '0',
  "almn_curso_nt_2_parcial" numeric(3, 2) DEFAULT '0',
  "almn_curso_nt_examen_final" numeric(3, 2) DEFAULT '0' ,
  "almn_curso_nt_examen_supletorio" numeric(3, 2) DEFAULT '0',
  "almn_curso_asistencia" character varying(30) DEFAULT 'Asiste', 
  "almn_curso_nota_final" numeric(3 ,2) DEFAULT '0', 
  "almn_curso_estado" character varying(30) DEFAULT 'Reprobado', 
  "almn_curso_num_faltas" integer DEFAULT '0', 
  CONSTRAINT alumno_curso_pk PRIMARY KEY ("id_almn_curso")
) WITH (OIDS = FALSE);

--Pre y Co requisitos  
CREATE TABLE "PreCoRequisitos" (
    "id_pre_co_requisito" serial NOT NULL, 
	"id_materia" integer NOT NULL, 
	"pre_co_tipo" character varying(1), 
	"id_materia_requisito" integer NOT NULL, 
	CONSTRAINT pre_co_requisito_pk PRIMARY KEY ("id_pre_co_requisito")
) WITH (OIDS = FALSE); 


--Malla Estudiante en su carrera  
CREATE TABLE "MallaEstudiante"(
	"id_malla_estudiante" serial NOT NULL, 
	"id_materia" integer NOT NULL, 
	"id_alumno" integer NOT NULL, 
	"malla_est_nota1" numeric(3, 2) NOT NULL DEFAULT '0',
	"malla_est_nota2" numeric(3, 2) NOT NULL DEFAULT '0',
	"malla_est_nota3" numeric(3, 2) NOT NULL DEFAULT '0',
	"malla_est_estado" character varying(1) NOT NULL DEFAULT 'P', 
	CONSTRAINT malla_estudiante_pk PRIMARY KEY ("id_malla_estudiante")
) WITH (OIDS = FALSE); 



ALTER TABLE "Carreras" ADD CONSTRAINT "carrera_fk1"
FOREIGN KEY ("id_docente_coordinador") REFERENCES "Docentes"("id_docente")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "PeriodoLectivo" ADD CONSTRAINT "periodo_lectivo_fk1"
FOREIGN KEY ("id_carrera") REFERENCES "Carreras"("id_carrera")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Cursos" ADD CONSTRAINT "curso_fk1"
FOREIGN KEY ("id_materia") REFERENCES "Materias"("id_materia")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Cursos" ADD CONSTRAINT "curso_fk2"
FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "AlumnoCurso" ADD CONSTRAINT "alumno_curso_fk1"
FOREIGN KEY ("id_alumno") REFERENCES "Alumnos"("id_alumno")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "AlumnoCurso" ADD CONSTRAINT "alumno_curso_fk2"
FOREIGN KEY ("id_curso") REFERENCES "Cursos"("id_curso")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Alumnos" ADD CONSTRAINT "alumnos_fk1"
FOREIGN KEY ("id_persona") REFERENCES "Personas"("id_persona")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Alumnos" ADD CONSTRAINT "alumnos_fk2"
FOREIGN KEY ("id_carrera") REFERENCES "Carreras"("id_carrera")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Docentes" ADD CONSTRAINT "docentes_fk1"
FOREIGN KEY ("id_persona") REFERENCES "Personas"("id_persona")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Personas" ADD CONSTRAINT "personas_fk1"
FOREIGN KEY ("id_tipo_persona") REFERENCES "TipoPersona"("id_tipo_persona")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Personas" ADD CONSTRAINT "personas_fk2"
FOREIGN KEY ("id_lugar_natal") REFERENCES "Lugares"("id_lugar")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Personas" ADD CONSTRAINT "personas_fk3"
FOREIGN KEY ("id_lugar_residencia") REFERENCES "Lugares"("id_lugar")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Lugares" ADD CONSTRAINT "lugares_fk1"
FOREIGN KEY ("id_lugar_referencia") REFERENCES "Lugares"("id_lugar")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "PreCoRequisitos" ADD CONSTRAINT "pre_co_requisito_fk1"
FOREIGN KEY ("id_materia") REFERENCES "Materias"("id_materia")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "PreCoRequisitos" ADD CONSTRAINT "pre_co_requisito_fk2"
FOREIGN KEY ("id_materia_requisito") REFERENCES "Materias"("id_materia")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "MallaEstudiante" ADD CONSTRAINT "malla_estudiante_fk1"
FOREIGN KEY ("id_materia") REFERENCES "Materias"("id_materia")
ON UPDATE CASCADE ON DELETE CASCADE; 

ALTER TABLE "MallaEstudiante" ADD CONSTRAINT "malla_estudiante_fk2"
FOREIGN KEY ("id_alumno") REFERENCES "Alumnos"("id_alumno")
ON UPDATE CASCADE ON DELETE CASCADE; 

ALTER TABLE "Materias" ADD CONSTRAINT "materia_fk1"
FOREIGN KEY ("id_carrera") REFERENCES "Carreras"("id_carrera")
ON UPDATE CASCADE ON DELETE CASCADE; 
