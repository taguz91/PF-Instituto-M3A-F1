--Informacion  
--Postgresql Version 10 
--Nombre: BDInstitutoPF

CREATE TABLE "PeriodoLectivo"(
 "id_prd_lectivo" serial NOT NULL,
 "id_carrera" integer NOT NULL,
 "prd_lectivo_nombre" character varying(100) NOT NULL,
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
  "id_lugar_natal" integer NOT NULL DEFAULT '1',
  "id_lugar_residencia" integer NOT NULL DEFAULT '1',
  "persona_foto" bytea,
  "persona_identificacion" character varying(20) NOT NULL UNIQUE,
  "persona_primer_apellido" character varying(25) NOT NULL,
  "persona_segundo_apellido" character varying(25),
  "persona_primer_nombre" character varying(25) NOT NULL,
  "persona_segundo_nombre" character varying(25),
  "persona_genero" character varying(10),
  "persona_sexo" character varying(1) NOT NULL,
  "persona_estado_civil" character varying(30) NOT NULL,
  "persona_etnia" character varying(50) NOT NULL,
  "persona_idioma_raiz" character varying(30) NOT NULL DEFAULT 'ESPAÑOL',
  "persona_tipo_sangre" character varying(5) NOT NULL,
  "persona_telefono" character varying(10),
  "persona_celular" character varying(10),
  "persona_correo" character varying(30),
  "persona_fecha_registro" date NOT NULL,
  "persona_discapacidad" boolean NOT NULL DEFAULT 'false',
  "persona_tipo_discapacidad" character varying(20),
  "persona_porcenta_discapacidad" integer DEFAULT '0',
  "persona_carnet_conadis" character varying(20),
  "persona_calle_principal" character varying(200) NOT NULL DEFAULT 'SN',
  "persona_numero_casa" character varying(10) NOT NULL DEFAULT '00',
  "persona_calle_secundaria" character varying(200) NOT NULL DEFAULT 'SN',
  "persona_referencia" character varying(200),
  "persona_sector" character varying(200),
  "persona_idioma" character varying(50),
  "persona_tipo_residencia" character varying(30),
  "persona_fecha_nacimiento" date NOT NULL, 
  "persona_activa" BOOLEAN NOT NULL DEFAULT 'true', 
  CONSTRAINT persona_pk PRIMARY KEY ("id_persona")
) WITH (OIDS = FALSE);

/*
Scrip
*/

--Materias
CREATE TABLE "Materias"(
	"id_materia" serial NOT NULL,
	"id_carrera" INTEGER NOT NULL,
	"id_eje" integer NOT NULL,
	"materia_codigo" character varying(30) NOT NULL,
	"materia_nombre" character varying(100) NOT NULL,
	"materia_ciclo" INTEGER NOT NULL,
	"materia_creditos" INTEGER NOT NULL,
	"materia_tipo" character varying(1),
	"materia_categoria" character varying(50),
	"materia_tipo_acreditacion" character varying(1) NOT NULL,
	"materia_horas_docencia" INTEGER NOT NULL DEFAULT '0',
	"materia_horas_practicas" INTEGER NOT NULL DEFAULT '0',
	"materia_horas_auto_estudio" INTEGER NOT NULL DEFAULT '0',
	"materia_horas_presencial" INTEGER NOT NULL DEFAULT '0', 
	"materia_total_horas" integer NOT NULL DEFAULT '0',
	"materia_activa" BOOLEAN NOT NULL DEFAULT 'true',
	"materia_objetivo" TEXT NOT NULL DEFAULT 'Sin objetivo', 
	"materia_descripcion" TEXT NOT NULL DEFAULT 'Sin descripcion', 
	"materia_objetivo_especifico" TEXT,
	"materia_organizacion_curricular" TEXT,  
	"materia_campo_formacion" character varying(200), 
	CONSTRAINT materia_pk PRIMARY KEY ("id_materia")
) WITH (OIDS = FALSE);

--Alumnos
CREATE TABLE "Alumnos"(
	"id_alumno" serial NOT NULL,
	"id_persona" INTEGER NOT NULL UNIQUE, 
	"id_carrera" INTEGER NOT NULL,
	"id_sec_economico" INTEGER NOT NULL, 
	"alumno_tipo_colegio" character varying(30) NOT NULL,
	"alumno_tipo_bachillerato" character varying(100) NOT NULL,
	"alumno_anio_graduacion" character varying(4) NOT NULL,
	"alumno_educacion_superior" BOOLEAN NOT NULL DEFAULT 'false',
	"alumno_titulo_superior" character varying(200),
	"alumno_nivel_academico" character varying(50) NOT NULL,
	"alumno_pension" BOOLEAN NOT NULL DEFAULT 'false', 
	"alumno_ocupacion" character varying(200) NOT NULL,
	"alumno_trabaja" BOOLEAN NOT NULL DEFAULT 'false',
	"alumno_nivel_formacion_padre" character varying(100) NOT NULL,
	"alumno_nivel_formacion_madre" character varying(100) NOT NULL,
	"alumno_nombre_contacto_emergencia" character varying(100) NOT NULL,
	"alumno_parentesco_contacto" character varying(20) NOT NULL,
	"alumno_numero_contacto" character varying(10) NOT NULL,
	"alumno_activo" boolean NOT NULL DEFAULT 'true', 
	"alumno_observacion" character varying(20), 
	CONSTRAINT alumno_pk PRIMARY KEY ("id_alumno")
) WITH (OIDS = FALSE);

--carrera
CREATE TABLE "Carreras"(
  "id_carrera" serial NOT NULL,
  "id_docente_coordinador" integer, 
  "carrera_nombre" character varying(100) NOT NULL,
  "carrera_codigo" character varying(15) NOT NULL,
  "carrera_fecha_inicio" DATE,
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
  "docente_fecha_fin" date,
  "docente_tipo_tiempo" character varying(20),
  "docente_activo" boolean NOT NULL DEFAULT 'true',
  "docente_observacion" character varying(20),
  "docente_capacitador" boolean NOT NULL DEFAULT 'false',
  CONSTRAINT docente_pk PRIMARY KEY ("id_docente")
) WITH (OIDS = false);

--Lugar
CREATE TABLE "Lugares"(
  "id_lugar" serial NOT NULL,
  "lugar_codigo" character varying(20),
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
  "id_jornada" integer NOT NULL, 
  "curso_nombre" character varying(10) NOT NULL,
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
	"pre_requisito" boolean NOT NULL DEFAULT 'true',  
	"id_materia_requisito" integer NOT NULL, 
	CONSTRAINT pre_co_requisito_pk PRIMARY KEY ("id_pre_co_requisito")
) WITH (OIDS = FALSE); 


--Malla Estudiante en su carrera  
CREATE TABLE "MallaEstudiante"(
	"id_malla_alumno" character varying(10) NOT NULL, 
	"id_materia" integer NOT NULL, 
	"id_alumno" integer NOT NULL, 
	"malla_almn_ciclo" integer NOT NULL, 
	"malla_almn_num_matricula" integer NOT NULL DEFAULT '0', 
	"malla_almn_nota1" numeric(3, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota2" numeric(3, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota3" numeric(3, 2) NOT NULL DEFAULT '0',
	"malla_almn_estado" character varying(1) NOT NULL DEFAULT 'P', 
	CONSTRAINT malla_estudiante_pk PRIMARY KEY ("id_malla_alumno")
) WITH (OIDS = FALSE); 


--Historial de usuarios  
CREATE TABLE "HistorialUsuarios"(
	"id_historial_user" serial NOT NULL, 
	"id_usuario" integer NOT NULL, 
	"historial_fecha" TIMESTAMP NOT NULL, 
	"historial_tipo_accion" character varying(30) NOT NULL, 
	"historial_nombre_tabla" character varying(30) NOT NULL, 
	"historial_pk_tabla" integer NOT NULL, 
	CONSTRAINT historial_user_pk PRIMARY KEY ("id_historial_user") 
) WITH (OIDS = FALSE);

--Ejes de formacion 
CREATE TABLE "EjesFormacion"(
	"id_eje" serial NOT NULL, 
	"id_carrera" integer NOT NULL, 
	"eje_codigo" character varying(10) NOT NULL, 
	"eje_nombre" character varying(100) NOT NULL, 
	CONSTRAINT eje_formacion_pk PRIMARY KEY ("id_eje") 
) WITH (OIDS = FALSE); 

--Sector economico  
CREATE TABLE "SectorEconomico"(
	"id_sec_economico" serial NOT NULL, 
	"sec_economico_codigo" character varying(10),
	"sec_economico_descripcion" character varying(200), 
	"sec_economico_activo" boolean NOT NULL DEFAULT 'true',
	CONSTRAINT sector_economico_pk PRIMARY KEY ("id_sec_economico")
) WITH (OIDS = FALSE); 

/*
	TABLAS GRUPO 16
*/


CREATE TABLE "Usuarios"(
	"id_usuario" serial NOT NULL,
	"usu_username" VARCHAR(200) NOT NULL,
	"usu_password" bytea NOT NULL,
	"id_persona" INTEGER NOT NULL,
	"id_rol" INTEGER NOT NULL,
	
	CONSTRAINT usuario_pk PRIMARY KEY("id_usuario")

)WITH (OIDS = FALSE); 


CREATE TABLE "RolesUsuarios"(
	"id_rol" serial NOT NULL,
	"rol_nombre" VARCHAR(150) NOT NULL,
	
	CONSTRAINT rol_usuario_pk PRIMARY KEY("id_rol")
	
) WITH(OIDS = FALSE);



CREATE TABLE "PeriodoIngresoNotas"(
	"id_perd_ingr_notas" serial NOT NULL,
	"perd_notas_fecha_inicio" DATE NOT NULL,
	"perd_notas_fecha_cierre" DATE NOT NULL,
	
	"id_prd_lectivo" INTEGER NOT NULL,
	"id_tipo_nota" INTEGER NOT NULL,
	
	CONSTRAINT perio_ingreso_notas_pk PRIMARY KEY("id_perd_ingr_notas")
)WITH(OIDS = FALSE);


CREATE TABLE "TipoDeNota"(
	"id_tipo_nota" serial NOT NULL,
	"tipo_nota_nombre" VARCHAR(50) NOT NULL,
	"tipo_nota_valor_minimo" NUMERIC(3,2) NOT NULL,
	"tipo_nota_valor_maximo" NUMERIC(3,2) NOT NULL,
	
	CONSTRAINT tipo_de_nota_pk PRIMARY KEY("id_tipo_nota")
)WITH(OIDS = FALSE);


CREATE TABLE "Accesos"(
	"id_acceso" INTEGER NOT NULL,
	"acc_nombre" VARCHAR(30) NOT NULL,
	"acc_descripcion" VARCHAR(150),
	
	CONSTRAINT Acceso_pk PRIMARY KEY("id_acceso")
)WITH (OIDS = FALSE);

CREATE TABLE "AccesosDelRol"(
	"id_acceso_del_rol" serial NOT NULL,
	"id_rol" INTEGER NOT NULL,
	"id_acceso" INTEGER NOT NULL,
	
	CONSTRAINT acceso_del_rol_pk PRIMARY KEY("id_acceso_del_rol")
)WITH(OIDS = FALSE);

/* Tablas GRUPO */
CREATE TABLE "Jornadas"(
	"id_jornada" serial NOT NULL, 
	"jornada_nombre" character varying(100),
	CONSTRAINT jornada_pk PRIMARY KEY ("id_jornada")
) WITH (OIDS = FALSE); 


/*
FK G 23
*/

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

ALTER TABLE "Cursos" ADD CONSTRAINT "curso_fk3"
FOREIGN KEY ("id_docente") REFERENCES "Docentes"("id_docente")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Cursos" ADD CONSTRAINT "curso_fk4"
FOREIGN KEY ("id_jornada") REFERENCES "Jornadas"("id_jornada")
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

ALTER TABLE "Alumnos" ADD CONSTRAINT "alumnos_fk3"
FOREIGN KEY ("id_sec_economico") REFERENCES "SectorEconomico"("id_sec_economico")
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

ALTER TABLE "Materias" ADD CONSTRAINT "materia_fk2"
FOREIGN KEY ("id_eje") REFERENCES "EjesFormacion"("id_eje")
ON UPDATE CASCADE ON DELETE CASCADE; 


ALTER TABLE "HistorialUsuarios" ADD CONSTRAINT "historial_user_fk1"
FOREIGN KEY("id_usuario") REFERENCES "Usuarios"("id_usuario")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "EjesFormacion" ADD CONSTRAINT "eje_formacion_fk1"
FOREIGN KEY("id_carrera") REFERENCES "Carreras"("id_carrera")
ON UPDATE CASCADE ON DELETE CASCADE;

/*
	FK GRUPO 16
*/

ALTER TABLE "Usuarios" ADD CONSTRAINT "persona_fk"
	FOREIGN KEY("id_persona") REFERENCES "Personas"("id_persona")
		ON UPDATE CASCADE ON DELETE CASCADE;
		
ALTER TABLE "Usuarios" ADD CONSTRAINT "roles_usuarios_fk"
	FOREIGN KEY("id_rol") REFERENCES "RolesUsuarios"("id_rol")
		ON UPDATE CASCADE ON DELETE CASCADE;
		
ALTER TABLE "PeriodoIngresoNotas" ADD CONSTRAINT "periodo_lectivo_fk"
	FOREIGN KEY("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
		ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "PeriodoIngresoNotas" ADD CONSTRAINT "Tipo_de_nota_fk"
	FOREIGN KEY("id_tipo_nota") REFERENCES "TipoDeNota"("id_tipo_nota")
		ON UPDATE CASCADE ON DELETE CASCADE;
	
	
ALTER TABLE "AccesosDelRol" ADD CONSTRAINT "accesos_fk"
	FOREIGN KEY("id_acceso") REFERENCES "Accesos" ("id_acceso")
		 ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "AccesosDelRol" ADD CONSTRAINT "rolesUsuarios_pk"
	FOREIGN KEY("id_rol") REFERENCES "RolesUsuarios"("id_rol")
		ON UPDATE CASCADE ON DELETE CASCADE;

/*VALORES POR DEFECTO EN LA BASE DE DATOS*/

INSERT INTO public."TipoPersona"(
	tipo_persona)
	VALUES ('DOCENTE'),
	('ALUMNO'),
	('ADMINISTRADOR');