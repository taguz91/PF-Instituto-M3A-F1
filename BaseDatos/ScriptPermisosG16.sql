--USUARIOS

INSERT INTO "Accesos" VALUES(1,'USUARIOS-Consultar');
INSERT INTO "Accesos" VALUES(2,'USUARIOS-Agregar');
INSERT INTO "Accesos" VALUES(3,'USUARIOS-Editar');
INSERT INTO "Accesos" VALUES(4,'USUARIOS-Eliminar');
INSERT INTO "Accesos" VALUES(5,'USUARIOS-AsignarRoles');

--ROLES-DEL-USUARIO

INSERT INTO "Accesos" VALUES(6,'ROLES-USUARIO-Consultar');
INSERT INTO "Accesos" VALUES(7,'ROLES-USUARIO-Agregar');
INSERT INTO "Accesos" VALUES(8,'ROLES-USUARIO-Editar');
INSERT INTO "Accesos" VALUES(9,'ROLES-USUARIO-Eliminar');


--ROLES

INSERT INTO "Accesos" VALUES(10,'ROLES-Consultar');
INSERT INTO "Accesos" VALUES(11,'ROLES-Agregar');
INSERT INTO "Accesos" VALUES(12,'ROLES-Editar');
INSERT INTO "Accesos" VALUES(13,'ROLES-Eliminar');

--ACCESOS-DEL-ROL

INSERT INTO "Accesos" VALUES(14,'ACCESOS-ROLES-Consultar');
INSERT INTO "Accesos" VALUES(15,'ACCESOS-ROLES-Agregar');
INSERT INTO "Accesos" VALUES(16,'ACCESOS-ROLES-Editar');
INSERT INTO "Accesos" VALUES(17,'ACCESOS-ROLES-Eliminar');

--ACCESOS

INSERT INTO "Accesos" VALUES(18,'ACCESOS-Consultar');
INSERT INTO "Accesos" VALUES(19,'ACCESOS-Agregar');
INSERT INTO "Accesos" VALUES(20,'ACCESOS-Editar');
INSERT INTO "Accesos" VALUES(21,'ACCESOS-Eliminar');

--PERSONAS

INSERT INTO "Accesos" VALUES(22,'PERSONAS-Consultar');
INSERT INTO "Accesos" VALUES(23,'PERSONAS-Agregar');
INSERT INTO "Accesos" VALUES(24,'PERSONAS-Editar');
INSERT INTO "Accesos" VALUES(25,'PERSONAS-Eliminar');


--TIPO-NOTA

INSERT INTO "Accesos" VALUES(26,'TIPO-NOTAS-Consultar');
INSERT INTO "Accesos" VALUES(27,'TIPO-NOTAS-Agregar');
INSERT INTO "Accesos" VALUES(28,'TIPO-NOTAS-Editar');
INSERT INTO "Accesos" VALUES(29,'TIPO-NOTAS-Eliminar');


--PERIODO-INGRESO-NOTAS

INSERT INTO "Accesos" VALUES(30,'PERIODO-INGRESO-NOTAS-Consultar');
INSERT INTO "Accesos" VALUES(31,'PERIODO-INGRESO-NOTAS-Agregar');
INSERT INTO "Accesos" VALUES(32,'PERIODO-INGRESO-NOTAS-Editar');
INSERT INTO "Accesos" VALUES(33,'PERIODO-INGRESO-NOTAS-Eliminar');





INSERT INTO "Accesos" VALUES(34,'USUARIOS-VerRoles');
INSERT INTO "Accesos" VALUES(35,'ROLES-Ver-Permisos');
INSERT INTO "Accesos" VALUES(36,'ROLES-Editar-Permisos');


--SCRIPT USUARIO ROOT


INSERT INTO "Roles"("rol_nombre") VALUES('ROOT');
INSERT INTO "Usuarios"("usu_username","usu_password", "id_persona" ) VALUES('ROOT', set_byte( MD5('ROOT')::bytea, 4,64),908);

INSERT INTO "RolesDelUsuario"("id_rol", "usu_username") VALUES(1,'ROOT'); 


--CREAR ROL DE USUARIO EN POSTGRESQL 
CREATE ROLE "ROOT" SUPERUSER CREATEDB CREATEROLE LOGIN REPLICATION BYPASSRLS PASSWORD 'ROOT';

--CAMBIAR CONTRASEÑA DE UN ROL DE POSTGRESQL
--ALTER ROLE "ROOT" PASSWORD 'NEW PASSWORD';


/*

    SCRIPT PARA UN USUARIO NORMAL

CREATE ROLE "DIEGO" NOINHERIT LOGIN ENCRYPTED PASSWORD '3113';

GRANT Connect ON DATABASE "Proyecto-Academico-Insta" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Accesos" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."AccesosDelRol" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."AlumnoCurso" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Alumnos" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."AlumnosCarrera" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Carreras" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Cursos" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."DetalleJornada" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Docentes" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."DocentesMateria" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."EjesFormacion" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."EvaluacionSilabo" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."HistorialUsuarios" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."JornadaDocente" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Jornadas" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Lugares" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."MallaAlumno" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."MateriaRequisitos" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Materias" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."PeriodoIngresoNotas" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."PeriodoLectivo" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Personas" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Referencias" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."ReferenciaSilabo" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Roles" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."RolesDelUsuario" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."SectorEconomico" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."SesionClase" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Silabo" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."TipoActividad" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."TipoDeNota" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."UnidadSilabo" TO "DIEGO";

GRANT Delete, Insert, References, Select, Trigger, Update ON TABLE "public"."Usuarios" TO "DIEGO";
*/




INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 1);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 2);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 3);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 4);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 5);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 6);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 7);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 8);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 9);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 10);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 11);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 12);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 13);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 14);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 15);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 16);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 17);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 18);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 19);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 20);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 21);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 22);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 23);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 24);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 25);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 26);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 27);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 28);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 29);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 30);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 31);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 32);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 33);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 34);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 35);
INSERT INTO "AccesosDelRol" (id_rol, id_acceso) VALUES(1, 36);



















"Docentes".id_docente,
"Docentes".id_persona,
"Docentes".docente_codigo,
"Docentes".docente_otro_trabajo,
"Docentes".docente_categoria,
"Docentes".docente_fecha_contrato,
"Docentes".docente_fecha_fin,
"Docentes".docente_tipo_tiempo,
"Docentes".docente_activo,
"Docentes".docente_observacion,
"Docentes".docente_capacitador
















