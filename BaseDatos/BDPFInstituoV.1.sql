--Informacion
--Postgresql Version 10
--Nombre: BDInstitutoPFM3A

CREATE TABLE "PeriodoLectivo"(
 "id_prd_lectivo" serial NOT NULL,
 "id_carrera" integer NOT NULL,
 "prd_lectivo_nombre" character varying(100) NOT NULL,
 "prd_lectivo_fecha_inicio" date NOT NULL,
 "prd_lectivo_fecha_fin" date NOT NULL,
 "prd_lectivo_observacion" character varying(200) DEFAULT 'SN',
 "prd_lectivo_estado" boolean NOT NULL DEFAULT 'true',
 "prd_lectivo_activo" boolean NOT NULL DEFAULT 'true',
 CONSTRAINT periodolectivo_pk PRIMARY KEY ("id_prd_lectivo")
) WITH (OIDS = FALSE);


--Aumentar los campos de telefono
--Persona
CREATE TABLE "Personas"(
  "id_persona" serial NOT NULL,
  "id_lugar_natal" integer DEFAULT '1',
  "id_lugar_residencia" integer DEFAULT '1',
  "persona_foto" bytea,
  "persona_identificacion" character varying(20) UNIQUE,
  "persona_primer_apellido" character varying(25),
  "persona_segundo_apellido" character varying(25),
  "persona_primer_nombre" character varying(25),
  "persona_segundo_nombre" character varying(25),
  "persona_genero" character varying(10),
  "persona_sexo" character varying(1),
  "persona_estado_civil" character varying(30),
  "persona_etnia" character varying(50),
  "persona_idioma_raiz" character varying(30) DEFAULT 'ESPAÑOL',
  "persona_tipo_sangre" character varying(5),
  "persona_telefono" character varying(40),
  "persona_celular" character varying(40),
  "persona_correo" character varying(50),
  --Guardamos con la fecha y la hora del sistema con defecto
  "persona_fecha_registro" TIMESTAMP DEFAULT now(),
  "persona_discapacidad" boolean DEFAULT 'false',
  "persona_tipo_discapacidad" character varying(20),
  "persona_porcenta_discapacidad" integer DEFAULT '0',
  "persona_carnet_conadis" character varying(20),
  "persona_calle_principal" character varying(200) DEFAULT 'SN',
  "persona_numero_casa" character varying(10) DEFAULT '00',
  "persona_calle_secundaria" character varying(200) DEFAULT 'SN',
  "persona_referencia" character varying(200),
  "persona_sector" character varying(200),
  "persona_idioma" character varying(50),
  "persona_tipo_residencia" character varying(30),
  "persona_fecha_nacimiento" date,
  "persona_activa" BOOLEAN DEFAULT 'true',
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
	"materia_nucleo" boolean DEFAULT 'false',
	CONSTRAINT materia_pk PRIMARY KEY ("id_materia")
) WITH (OIDS = FALSE);

--Alumnos
CREATE TABLE "Alumnos"(
	"id_alumno" serial NOT NULL,
	"id_persona" INTEGER NOT NULL UNIQUE,
	"id_sec_economico" INTEGER,
	"alumno_codigo" character varying(30), 	--Solo es para exportar
	"alumno_tipo_colegio" character varying(30) NOT NULL,
	"alumno_tipo_bachillerato" character varying(100) NOT NULL,
	"alumno_anio_graduacion" character varying(4) NOT NULL,
	"alumno_educacion_superior" BOOLEAN NOT NULL DEFAULT 'false',
	"alumno_titulo_superior" character varying(200),
	"alumno_nivel_academico" character varying(50) NOT NULL,
	"alumno_pension" BOOLEAN NOT NULL DEFAULT 'false',
	"alumno_ocupacion" character varying(200),
	"alumno_trabaja" BOOLEAN NOT NULL DEFAULT 'false',
	"alumno_nivel_formacion_padre" character varying(100),
	"alumno_nivel_formacion_madre" character varying(100),
	"alumno_nombre_contacto_emergencia" character varying(200) NOT NULL,
	"alumno_parentesco_contacto" character varying(20) NOT NULL,
	"alumno_numero_contacto" character varying(20) NOT NULL,
	"alumno_activo" boolean NOT NULL DEFAULT 'true',
	"alumno_observacion" character varying(100),
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

--Asignar un docente por carrera
--Docente
CREATE TABLE "Docentes"(
  "id_docente" serial NOT NULL,
  "id_persona" integer NOT NULL UNIQUE,
  "docente_codigo" character varying(20), --Para guardar la cedula
  "docente_otro_trabajo" boolean DEFAULT 'false',
  "docente_categoria" integer,
  "docente_fecha_contrato" date,
  "docente_fecha_fin" date,
  "docente_tipo_tiempo" character varying(20),
  "docente_activo" boolean NOT NULL DEFAULT 'true',
  "docente_observacion" character varying(20),
  "docente_capacitador" boolean NOT NULL DEFAULT 'false',
	"docente_titulo" character varying(200),
	"docente_abreviatura" character varying(20),
	"docente_en_funcion" boolean NOT NULL DEFAULT 'true',
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
  "curso_paralelo" character varying(5) NOT NULL DEFAULT 'NA',
	"curso_activo" boolean NOT NULL default 'true',
  CONSTRAINT curso_pk PRIMARY KEY ("id_curso")
) WITH (OIDS = FALSE);

--AlumnoCurso
CREATE TABLE "AlumnoCurso"(
  "id_almn_curso" serial NOT NULL,
  "id_alumno" integer NOT NULL,
  "id_curso" integer NOT NULL,
  "almn_curso_fecha_registro" DATE default now();
  "almn_curso_nt_1_parcial" numeric(6, 2) DEFAULT '0',
  "almn_curso_nt_examen_interciclo" numeric(6, 2) DEFAULT '0',
  "almn_curso_nt_2_parcial" numeric(6, 2) DEFAULT '0',
  "almn_curso_nt_examen_final" numeric(6, 2) DEFAULT '0' ,
  "almn_curso_nt_examen_supletorio" numeric(6, 2) DEFAULT '0',
  "almn_curso_asistencia" character varying(30) DEFAULT 'Asiste',
  "almn_curso_nota_final" numeric(6 ,2) DEFAULT '0',
  "almn_curso_estado" character varying(30) DEFAULT 'Reprobado',
  "almn_curso_num_faltas" integer DEFAULT '0',
	"almn_curso_activo" boolean DEFAULT 'true',
  CONSTRAINT alumno_curso_pk PRIMARY KEY ("id_almn_curso")
) WITH (OIDS = FALSE);


--Malla Estudiante en su carrera
CREATE TABLE "MallaAlumno"(
	"id_malla_alumno" serial NOT NULL,
	"id_materia" integer NOT NULL,
	"id_almn_carrera" integer NOT NULL,
	"malla_almn_ciclo" integer NOT NULL,
	"malla_almn_num_matricula" integer NOT NULL DEFAULT '0',
	"malla_almn_nota1" numeric(6, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota2" numeric(6, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota3" numeric(6, 2) NOT NULL DEFAULT '0',
	"malla_almn_estado" character varying(1) NOT NULL DEFAULT 'P',
	"malla_alm_observacion" character varying(200),
	CONSTRAINT malla_estudiante_pk PRIMARY KEY ("id_malla_alumno")
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

--Alumnos por carrera
CREATE TABLE "AlumnosCarrera"(
	"id_almn_carrera" serial NOT NULL,
	"id_alumno" integer NOT NULL,
	"id_carrera" integer NOT NULL,
	"almn_carrera_activo" BOOLEAN NOT NULL DEFAULT 'true',
	"almn_carrera_fecha_registro" TIMESTAMP,
	CONSTRAINT alumno_carrera_pk PRIMARY KEY ("id_almn_carrera")
) WITH (OIDS = FALSE);

--Docentes por materia
CREATE TABLE "DocentesMateria"(
	"id_docente_mat" serial NOT NULL,
	"id_docente" integer NOT NULL,
	"id_materia" integer NOT NULL,
	"docente_mat_activo" BOOLEAN NOT NULL DEFAULT 'true',
	CONSTRAINT docente_materia_pk PRIMARY  KEY ("id_docente_mat")
) WITH (OIDS = FALSE);

--Modificaciones 15/4/2019

CREATE TABLE "RolesPeriodo"(
	"id_rol_prd" serial NOT NULL,
	"id_prd_lectivo" integer NOT NULL,
	"rol_prd" character varying(200) NOT NULL,
	"rol_activo" boolean DEFAULT 'true',
	CONSTRAINT rol_prd_pk PRIMARY KEY ("id_rol_prd")
) WITH (OIDS = FALSE);

CREATE TABLE "RolesDocente"(
	"id_rol_docente" serial NOT NULL,
	"id_docente" integer NOT NULL,
	"id_rol_prd" integer NOT NULL,
	"rol_docente_activo" boolean default 'true',
	CONSTRAINT id_rol_docente PRIMARY KEY("id_rol_docente")
) WITH (OIDS = FALSE);

--FK de roles por periodo lectivo
ALTER TABLE "RolesPeriodo" ADD CONSTRAINT "rol_prd_fk1"
FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
ON UPDATE CASCADE ON DELETE CASCADE;

--FK de la tabla Roles DOcente
ALTER TABLE "RolesDocente" ADD CONSTRAINT "rol_docente_fk1"
FOREIGN KEY ("id_rol_prd") REFERENCES "RolesPeriodo"("id_rol_prd")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "RolesDocente" ADD CONSTRAINT "rol_docente_fk2"
FOREIGN KEY ("id_docente") REFERENCES "Docentes"("id_docente")
ON UPDATE CASCADE ON DELETE CASCADE;

--Para matricula

CREATE TABLE "Matricula"(
	"id_matricula" serial NOT NULL,
	"id_alumno" integer NOT NULL,
	"id_prd_lectivo" integer NOT NULL,
	"matricula_fecha" TIMESTAMP DEFAULT now(),
	"matricula_activa" boolean NOT NULL DEFAULT 'true',
	CONSTRAINT id_matricula_pk PRIMARY KEY("id_matricula")
) WITH (OIDS = FALSE);

ALTER TABLE "Matricula" ADD CONSTRAINT "matricula_fk1"
FOREIGN KEY ("id_alumno") REFERENCES "Alumnos"("id_alumno")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Matricula" ADD CONSTRAINT "matricula_fk2"
FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
ON UPDATE CASCADE ON DELETE CASCADE;

--Para retirar un alumno 
CREATE TABLE "Retirados"(
	"id_retirado" serial NOT NULL,
	"id_malla_alumno" integer NOT NULL,
	"id_almn_curso" integer NOT NULL,
	"retiro_fecha" TIMESTAMP DEFAULT now(),
	"retiro_observacion" text, 
	CONSTRAINT id_retirado_pk PRIMARY KEY("id_retirado")
) WITH (OIDS = FALSE);

ALTER TABLE "Retirados" ADD CONSTRAINT "retirado_.fk1"
FOREIGN KEY ("id_malla_alumno") REFERENCES "MallaAlumno"("id_malla_alumno")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Retirados" ADD CONSTRAINT "retirado_fk2"
FOREIGN KEY ("id_almn_curso") REFERENCES "AlumnoCurso"("id_almn_curso")
ON UPDATE CASCADE ON DELETE CASCADE;


/*
	TABLAS GRUPO 16
*/


CREATE TABLE "Usuarios"(
  "id_usuario" serial NOT NULL,
	"usu_username" VARCHAR(50) NOT NULL,
	"usu_password" bytea NOT NULL,
	"usu_estado" BOOLEAN DEFAULT TRUE,
	"id_persona" INTEGER,
	CONSTRAINT usuario_pk PRIMARY KEY("usu_username")

)WITH (OIDS = FALSE);


CREATE TABLE "Roles"(
	"id_rol" serial NOT NULL,
	"rol_nombre" VARCHAR(60) NOT NULL,
	"rol_observaciones" VARCHAR(150),
	"rol_estado" BOOLEAN DEFAULT TRUE,

	CONSTRAINT rol_usuario_pk PRIMARY KEY("id_rol")

) WITH(OIDS = FALSE);

/*
	ESTA ES UNA TABLA INTERMEDIA ENTRE Usuarios y Roles
	EL USUARIO PUEDE CUMPLIR CON MAS DE UN ROL DENTRO DEL SISTEMA
*/
CREATE TABLE "RolesDelUsuario"(
	"id_roles_usuarios" serial NOT NULL,
	"id_rol" INTEGER NOT NULL,
	"usu_username" VARCHAR(200) NOT NULL,

	CONSTRAINT roles_usuarios_pk PRIMARY KEY("id_roles_usuarios")

)WITH(OIDS = FALSE);


CREATE TABLE "PeriodoIngresoNotas"(
	"id_perd_ingr_notas" serial NOT NULL,
	"perd_notas_fecha_inicio" DATE NOT NULL,
	"perd_notas_fecha_cierre" DATE NOT NULL,
	"perd_notas_estado" BOOLEAN DEFAULT TRUE,

	"id_prd_lectivo" INTEGER NOT NULL,
	"id_tipo_nota" INTEGER NOT NULL,

	CONSTRAINT perio_ingreso_notas_pk PRIMARY KEY("id_perd_ingr_notas")
)WITH(OIDS = FALSE);


CREATE TABLE "TipoDeNota"(
	"id_tipo_nota" serial NOT NULL,
	"tipo_nota_nombre" VARCHAR(50) NOT NULL,
	"tipo_nota_valor_minimo" NUMERIC(6,2) NOT NULL,
	"tipo_nota_valor_maximo" NUMERIC(6,2) NOT NULL,
	"tipo_nota_fecha_creacion" DATE DEFAULT CURRENT_DATE,
	"tipo_nota_estado" BOOLEAN DEFAULT TRUE,
	"id_carrera" INTEGER NOT NULL,


	CONSTRAINT tipo_de_nota_pk PRIMARY KEY("id_tipo_nota")
)WITH(OIDS = FALSE);


CREATE TABLE "Accesos"(
	"id_acceso" INTEGER NOT NULL,
	"acc_nombre" VARCHAR(100) NOT NULL,
	"acc_descripcion" VARCHAR(150),

	CONSTRAINT Acceso_pk PRIMARY KEY("id_acceso")
)WITH (OIDS = FALSE);


/*
	ESTA ES UNA TABLA INTERMEDIA ENTRE Roles y Accesos
	POR CADA ROL HAY Accesos o (Permisos) Diferentes
*/

CREATE TABLE "AccesosDelRol"(
	"id_acceso_del_rol" serial NOT NULL,
	"id_rol" INTEGER NOT NULL,
	"id_acceso" INTEGER NOT NULL,

	CONSTRAINT acceso_del_rol_pk PRIMARY KEY("id_acceso_del_rol")
)WITH(OIDS = FALSE);

--Historial de usuarios

CREATE TABLE "HistorialUsuarios"(
	"id_historial_user" serial NOT NULL,
	"usu_username" VARCHAR(50) NOT NULL,
	"historial_fecha" TIMESTAMP DEFAULT now(),
	"historial_tipo_accion" character varying(30) NOT NULL,
	"historial_nombre_tabla" character varying(30) NOT NULL,
	"historial_pk_tabla" integer NOT NULL,
  "historial_observacion" character varying(200),
	CONSTRAINT historial_user_pk PRIMARY KEY ("id_historial_user")
) WITH (OIDS = FALSE);

CREATE TABLE "IngresoNotas"(
    id_ingreso_notas serial NOT NULL PRIMARY KEY,
    nota_primer_inteciclo BOOLEAN DEFAULT FALSE,
    nota_examen_intecilo BOOLEAN DEFAULT FALSE,
    nota_segundo_inteciclo BOOLEAN DEFAULT FALSE,
    nota_examen_final BOOLEAN DEFAULT FALSE,
    nota_examen_de_recuperacion BOOLEAN DEFAULT FALSE,

    id_curso INTEGER NOT NULL
) WITH (OIDS = FALSE);



/* Tablas GRUPO */

CREATE TABLE "MateriaRequisitos" (
	id_requisito   serial NOT NULL,
	id_materia integer NOT NULL,
	id_materia_requisito integer NOT NULL,
	tipo_requisito character varying(5) NOT NULL,

	PRIMARY KEY(id_requisito),

	FOREIGN KEY (id_materia)
        REFERENCES "Materias" (id_materia)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

	FOREIGN KEY (id_materia_requisito)
        REFERENCES "Materias" (id_materia)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE "SesionClase" (

	id_sesion  serial NOT NULL,
	id_curso integer NOT NULL,
	dia_sesion integer NOT NULL,
	hora_inicio_sesion time without time zone NOT NULL,
	hora_fin_sesion time without time zone NOT NULL,

	PRIMARY KEY(id_sesion),

	FOREIGN KEY (id_curso)
        REFERENCES "Cursos" (id_curso)
        ON UPDATE CASCADE
        ON DELETE CASCADE

);

CREATE TABLE "Jornadas" (

	id_jornada  serial NOT NULL,
	nombre_jornada character varying (40) NOT NULL,

	PRIMARY KEY(id_jornada)


);

CREATE TABLE "DetalleJornada" (

	id_detalle_jornada serial NOT NULL,
	id_jornada integer NOT NULL,
	hora_inicio_jornada time without time zone NOT NULL,
	hora_fin_jornada time without time zone NOT NULL,
	dia_inicio_jornada integer NOT NULL,
	dia_fin_jornada integer NOT NULL,

	PRIMARY KEY(id_detalle_jornada),

	FOREIGN KEY (id_jornada)
        REFERENCES "Jornadas" (id_jornada)
        ON UPDATE CASCADE
        ON DELETE CASCADE

);





CREATE TABLE "JornadaDocente" (

	id_jornada_docente serial NOT NULL,
	id_jornada integer NOT NULL,
	id_docente integer NOT NULL,
	id_prd_lectivo integer NOT NULL,

	PRIMARY KEY(id_jornada_docente),
	FOREIGN KEY (id_jornada)
        REFERENCES "Jornadas" (id_jornada)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

	FOREIGN KEY (id_docente)
        REFERENCES "Docentes" (id_docente)
        ON UPDATE CASCADE
        ON DELETE CASCADE,


	FOREIGN KEY (id_prd_lectivo)
        REFERENCES "PeriodoLectivo" (id_prd_lectivo)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE "TipoActividad" (

	id_tipo_actividad serial NOT NULL,
	nombre_tipo_actividad character varying (50) NOT NULL,
	nombre_subtipo_actividad character varying (50) NOT NULL,

	PRIMARY KEY(id_tipo_actividad)

);


CREATE TABLE "Silabo" (

	id_silabo serial NOT NULL,
	id_materia integer NOT NULL,
  id_prd_lectivo integer NOT NULL,
	estado_silabo integer NOT NULL,
	documento_silabo bytea,
	documento_analitico bytea,

	PRIMARY KEY(id_silabo),

	FOREIGN KEY (id_materia)
        REFERENCES "Materias" (id_materia)
        ON UPDATE CASCADE
        ON DELETE CASCADE


);


CREATE TABLE "Referencias" (

	id_referencia serial NOT NULL,
	codigo_referencia character varying (10) NOT NULL,
	descripcion_referencia text NOT NULL,
	tipo_referencia character varying (50) NOT NULL,
	existe_en_biblioteca boolean NOT NULL,

	PRIMARY KEY(id_referencia)


);


CREATE TABLE "ReferenciaSilabo" (

	id_referencia_silabo serial NOT NULL,
	id_referencia integer NOT NULL,
	id_silabo integer NOT NULL,

	PRIMARY KEY(id_referencia_silabo),
	FOREIGN KEY (id_referencia)
        REFERENCES "Referencias" (id_referencia)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

	FOREIGN KEY (id_silabo)
        REFERENCES "Silabo" (id_silabo)
        ON UPDATE CASCADE
        ON DELETE CASCADE

);


CREATE TABLE "UnidadSilabo" (

	id_unidad serial NOT NULL,
	id_silabo integer NOT NULL,
	numero_unidad integer NOT NULL,
	objetivo_especifico_unidad text NOT NULL,
	resultados_aprendizaje_unidad text NOT NULL,
	contenidos_unidad text NOT NULL,
	fecha_inicio_unidad date NOT NULL,
	fecha_fin_unidad date NOT NULL,
	horas_docencia_unidad integer NOT NULL,
	horas_practica_unidad integer NOT NULL,
	horas_autonomo_unidad integer NOT NULL,
  titulo_unidad TEXT NOT NULL,

	PRIMARY KEY(id_unidad),
	FOREIGN KEY (id_silabo)
        REFERENCES "Silabo" (id_silabo)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE "EvaluacionSilabo" (

	id_evaluacion serial NOT NULL,
	id_unidad integer NOT NULL,
  indicador TEXT NOT NULL,
	id_tipo_actividad integer NOT NULL,
  instrumento text NOT NULL,
  valoracion numeric (4, 1) NOT NULL,
	fecha_envio date NOT NULL,
	fecha_presentacion date NOT NULL,

	PRIMARY KEY(id_evaluacion),

	FOREIGN KEY (id_unidad)
        REFERENCES "UnidadSilabo" (id_unidad)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

	FOREIGN KEY (id_tipo_actividad)
        REFERENCES "TipoActividad" (id_tipo_actividad)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE "Silabo" ADD CONSTRAINT "fk_silabo_prd_lectivo"
    FOREIGN KEY ("id_prd_lectivo") REFERENCES "PeriodoLectivo"("id_prd_lectivo")
        ON DELETE CASCADE ON UPDATE CASCADE;

--Actualizaciones 16/4/2019

CREATE SEQUENCE public."PlandeClases_id_plan_clases_seq";

ALTER SEQUENCE public."PlandeClases_id_plan_clases_seq"
    OWNER TO postgres;



CREATE TABLE public."PlandeClases"
(
    id_plan_clases integer NOT NULL DEFAULT nextval('"PlandeClases_id_plan_clases_seq"'::regclass),
    
    id_curso integer NOT NULL,
    id_unidad integer NOT NULL,
    observaciones text COLLATE pg_catalog."default",
    documento_plan_clases bytea,
    fecha_revision date,
    fecha_generacion date,
    fecha_cierre date,
    CONSTRAINT "PlandeClases_pkey" PRIMARY KEY (id_plan_clases),
    CONSTRAINT id_curso FOREIGN KEY (id_curso)
        REFERENCES public."Cursos" (id_curso) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    
    CONSTRAINT id_unidad FOREIGN KEY (id_unidad)
        REFERENCES public."UnidadSilabo" (id_unidad) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."PlandeClases"
    OWNER to postgres;



CREATE TABLE public."TrabajoAutonomo"
(
    id_evaluacion integer NOT NULL,
    id_plan_clases integer NOT NULL,
    CONSTRAINT "Trabajo_autonomo_pkey" PRIMARY KEY (id_evaluacion),
    CONSTRAINT id_evaluacion FOREIGN KEY (id_evaluacion)
        REFERENCES public."EvaluacionSilabo" (id_evaluacion) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_plan_clases FOREIGN KEY (id_plan_clases)
        REFERENCES public."PlandeClases" (id_plan_clases) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."TrabajoAutonomo"
    OWNER to postgres;



CREATE SEQUENCE public."Recursos_id_recurso_seq";

ALTER SEQUENCE public."Recursos_id_recurso_seq"
    OWNER TO postgres;

CREATE TABLE public."Recursos"
(
    id_recurso integer NOT NULL DEFAULT nextval('"Recursos_id_recurso_seq"'::regclass),
    nombre_recursos text COLLATE pg_catalog."default",
    tipo_recurso character(1) COLLATE pg_catalog."default",
    CONSTRAINT "Recursos_pkey" PRIMARY KEY (id_recurso)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Recursos"
    OWNER to postgres;



CREATE SEQUENCE public."RecursosPlanClases_id_recursos_plan_clases_seq";

ALTER SEQUENCE public."RecursosPlanClases_id_recursos_plan_clases_seq"
    OWNER TO postgres;

CREATE TABLE public."RecursosPlanClases"
(
    id_recursos_plan_clases integer NOT NULL DEFAULT nextval('"RecursosPlanClases_id_recursos_plan_clases_seq"'::regclass),
    id_plan_clases integer NOT NULL,
    id_recurso integer NOT NULL,
    CONSTRAINT "RecursosPlanClases_pkey" PRIMARY KEY (id_recursos_plan_clases),
    CONSTRAINT id_plan_clases FOREIGN KEY (id_plan_clases)
        REFERENCES public."PlandeClases" (id_plan_clases) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_recurso FOREIGN KEY (id_recurso)
        REFERENCES public."Recursos" (id_recurso) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."RecursosPlanClases"
    OWNER to postgres;



CREATE SEQUENCE public."EstrategiasMetodologias_id_estrategias_metodologias_seq";

ALTER SEQUENCE public."EstrategiasMetodologias_id_estrategias_metodologias_seq"
    OWNER TO postgres;


CREATE TABLE public."EstrategiasMetodologias"
(
    id_estrategias_metodologias integer NOT NULL DEFAULT nextval('"EstrategiasMetodologias_id_estrategias_metodologias_seq"'::regclass),
    tipo_estrategias_metodologias text COLLATE pg_catalog."default",
    id_plan_de_clases integer NOT NULL,
    id_estrategias_unidad integer NOT NULL,
    CONSTRAINT "EstrategiasMetodologias_pkey" PRIMARY KEY (id_estrategias_metodologias),
    CONSTRAINT id_estrategias_unidad FOREIGN KEY (id_estrategias_metodologias)
        REFERENCES public."EstrategiasUnidad" (id_estrategia_unidad) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT id_plan_de_clases FOREIGN KEY (id_plan_de_clases)
        REFERENCES public."PlandeClases" (id_plan_clases) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."EstrategiasMetodologias"
    OWNER to postgres;

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
FOREIGN KEY ("id_sec_economico") REFERENCES "SectorEconomico"("id_sec_economico")
ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE "Docentes" ADD CONSTRAINT "docentes_fk1"
FOREIGN KEY ("id_persona") REFERENCES "Personas"("id_persona")
ON UPDATE CASCADE ON DELETE CASCADE;



ALTER TABLE "Personas" ADD CONSTRAINT "personas_fk1"
FOREIGN KEY ("id_lugar_natal") REFERENCES "Lugares"("id_lugar")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Personas" ADD CONSTRAINT "personas_fk2"
FOREIGN KEY ("id_lugar_residencia") REFERENCES "Lugares"("id_lugar")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Lugares" ADD CONSTRAINT "lugares_fk1"
FOREIGN KEY ("id_lugar_referencia") REFERENCES "Lugares"("id_lugar")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "MallaAlumno" ADD CONSTRAINT "malla_alumno_fk1"
FOREIGN KEY ("id_materia") REFERENCES "Materias"("id_materia")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "MallaAlumno" ADD CONSTRAINT "malla_alumno_fk2"
FOREIGN KEY ("id_almn_carrera") REFERENCES "AlumnosCarrera"("id_almn_carrera")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Materias" ADD CONSTRAINT "materia_fk1"
FOREIGN KEY ("id_carrera") REFERENCES "Carreras"("id_carrera")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "Materias" ADD CONSTRAINT "materia_fk2"
FOREIGN KEY ("id_eje") REFERENCES "EjesFormacion"("id_eje")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "EjesFormacion" ADD CONSTRAINT "eje_formacion_fk1"
FOREIGN KEY("id_carrera") REFERENCES "Carreras"("id_carrera")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "AlumnosCarrera" ADD CONSTRAINT "alumno_carrera_fk1"
FOREIGN KEY ("id_alumno") REFERENCES "Alumnos"("id_alumno")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "AlumnosCarrera" ADD CONSTRAINT "alumno_carrera_fk2"
FOREIGN KEY ("id_carrera") REFERENCES "Carreras"("id_carrera")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "DocentesMateria" ADD CONSTRAINT "docente_materia_fk1"
FOREIGN KEY ("id_docente") REFERENCES "Docentes"("id_docente")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "DocentesMateria" ADD CONSTRAINT "docente_materia_fk2"
FOREIGN KEY ("id_materia") REFERENCES "Materias"("id_materia")
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "UnidadSilabo" ADD CONSTRAINT "unidad_silabo_pk1"
FOREIGN KEY ("id_silabo") REFERENCES "Silabo"("id_silabo")
ON UPDATE CASCADE ON DELETE CASCADE;


/*
	FK GRUPO 16
*/

ALTER TABLE "HistorialUsuarios" ADD CONSTRAINT "historial_user_fk1"
FOREIGN KEY("usu_username") REFERENCES "Usuarios"("usu_username")
ON UPDATE CASCADE ON DELETE CASCADE;


ALTER TABLE "Usuarios" ADD CONSTRAINT "persona_fk"
	FOREIGN KEY("id_persona") REFERENCES "Personas"("id_persona")
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

ALTER TABLE "AccesosDelRol" ADD CONSTRAINT "roles_pk"
	FOREIGN KEY("id_rol") REFERENCES "Roles"("id_rol")
		ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "RolesDelUsuario" ADD CONSTRAINT "roles_rolesUsuarios_fk"
	FOREIGN KEY("id_rol") REFERENCES "Roles"("id_rol")
		ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "RolesDelUsuario" ADD CONSTRAINT "usuarios_rolesUsuarios_fk"
	FOREIGN KEY("usu_username") REFERENCES "Usuarios"("usu_username")
		ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE "IngresoNotas" ADD CONSTRAINT "fk_cursos_ingreso_notas"
    FOREIGN KEY ("id_curso") REFERENCES "Cursos"("id_curso")
        ON DELETE CASCADE ON UPDATE CASCADE;

--AGREGADA EL 16/Abril/2019
ALTER TABLE "TipoDeNota" ADD CONSTRAINT "carrera_TipoDeNota_fk"
    FOREIGN KEY ("id_carrera") REFERENCES "Carreras" ("id_carrera")
        ON DELETE CASCADE ON UPDATE CASCADE;

--Tablas nuevas de G

CREATE SEQUENCE public."EstrategiasSilabo_id_estrategia_seq";

ALTER SEQUENCE public."EstrategiasSilabo_id_estrategia_seq"
    OWNER TO postgres;

CREATE TABLE public."EstrategiasAprendizaje"
(
    id_estrategia integer NOT NULL DEFAULT nextval('"EstrategiasSilabo_id_estrategia_seq"'::regclass),
    descripcion_estrategia text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "EstrategiasSilabo_pkey" PRIMARY KEY (id_estrategia)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."EstrategiasAprendizaje"
    OWNER to postgres;


CREATE SEQUENCE public."EstrategiasUnidad_id_estrategia_unidad_seq";

ALTER SEQUENCE public."EstrategiasUnidad_id_estrategia_unidad_seq"
    OWNER TO postgres;

CREATE TABLE public."EstrategiasUnidad"
(
    id_estrategia_unidad integer NOT NULL DEFAULT nextval('"EstrategiasUnidad_id_estrategia_unidad_seq"'::regclass),
    id_unidad integer NOT NULL,
    id_estrategia integer NOT NULL,
    CONSTRAINT "EstrategiasUnidad_pkey" PRIMARY KEY (id_estrategia_unidad),
    CONSTRAINT "EstrategiasUnidad_id_estrategia_fkey" FOREIGN KEY (id_estrategia)
        REFERENCES public."EstrategiasAprendizaje" (id_estrategia) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "EstrategiasUnidad_id_unidad_fkey" FOREIGN KEY (id_unidad)
        REFERENCES public."UnidadSilabo" (id_unidad) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."EstrategiasUnidad"
    OWNER to postgres;
	
--CREANDO TABLA SESION_NO_CLASE

CREATE TABLE "SesionNoClase"(
	"id_sesion_no_clase" serial NOT NULL,
	"id_rol_docente" INTEGER NOT NULL,
	"hora_inicio_sesionnoclase" TIMESTAMP DEFAULT now(),
	"hora_fin_sesionnoclase" TIMESTAMP DEFAULT now(),
	"dia_sesionnoclase" DATE NOT NULL,
	CONSTRAINT pk_sesion_no_clase PRIMARY KEY("id_sesion_no_clase"),
	CONSTRAINT fk_sesion_no_clase FOREIGN KEY ("id_rol_docente")
        REFERENCES public."RolesDocente"("id_rol_docente") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)WITH(OIDS = FALSE);

/*VALORES POR DEFECTO EN LA BASE DE DATOS*/

INSERT INTO public."Jornadas"(
	 nombre_jornada)
	VALUES ('MATUTINA'),
	('VESPERTINA'),
	('NOCTURNA'),
	('INTENSIVA');
