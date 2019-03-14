DROP TABLE "Usuarios" CASCADE;

CREATE TABLE "Usuarios"(
	"usu_username" VARCHAR(50) NOT NULL,
	"usu_password" bytea NOT NULL,
	"usu_estado" BOOLEAN DEFAULT TRUE,
	"id_persona" INTEGER,
	CONSTRAINT usuario_pk PRIMARY KEY("usu_username")

)WITH (OIDS = FALSE);

DROP TABLE "RolesUsuarios" CASCADE;
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

DROP TABLE "PeriodoIngresoNotas" CASCADE;
CREATE TABLE "PeriodoIngresoNotas"(
	"id_perd_ingr_notas" serial NOT NULL,
	"perd_notas_fecha_inicio" DATE NOT NULL,
	"perd_notas_fecha_cierre" DATE NOT NULL,
	"perd_notas_estado" BOOLEAN DEFAULT TRUE,

	"id_prd_lectivo" INTEGER NOT NULL,
	"id_tipo_nota" INTEGER NOT NULL,

	CONSTRAINT perio_ingreso_notas_pk PRIMARY KEY("id_perd_ingr_notas")
)WITH(OIDS = FALSE);

DROP TABLE "TipoDeNota" CASCADE;
CREATE TABLE "TipoDeNota"(
	"id_tipo_nota" serial NOT NULL,
	"tipo_nota_nombre" VARCHAR(50) NOT NULL,
	"tipo_nota_valor_minimo" NUMERIC(6,2) NOT NULL,
	"tipo_nota_valor_maximo" NUMERIC(6,2) NOT NULL,
	"tipo_nota_fecha_creacion" DATE DEFAULT CURRENT_DATE,
	"tipo_nota_estado" BOOLEAN DEFAULT TRUE,


	CONSTRAINT tipo_de_nota_pk PRIMARY KEY("id_tipo_nota")
)WITH(OIDS = FALSE);

DROP TABLE "Accesos" CASCADE;
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
DROP TABLE "AccesosDelRol" CASCADE;
CREATE TABLE "AccesosDelRol"(
	"id_acceso_del_rol" serial NOT NULL,
	"id_rol" INTEGER NOT NULL,
	"id_acceso" INTEGER NOT NULL,

	CONSTRAINT acceso_del_rol_pk PRIMARY KEY("id_acceso_del_rol")
)WITH(OIDS = FALSE);

--Historial de usuarios
DROP TABLE "HistorialUsuarios" CASCADE;
CREATE TABLE "HistorialUsuarios"(
	"id_historial_user" serial NOT NULL,
	"usu_username" VARCHAR(50) NOT NULL,
	"historial_fecha" TIMESTAMP NOT NULL,
	"historial_tipo_accion" character varying(30) NOT NULL,
	"historial_nombre_tabla" character varying(30) NOT NULL,
	"historial_pk_tabla" integer NOT NULL,
	"historial_observacion" character varying(200),
	CONSTRAINT historial_user_pk PRIMARY KEY ("id_historial_user")
) WITH (OIDS = FALSE);

--Foraneas

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
