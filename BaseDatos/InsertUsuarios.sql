--Antes de ejecutar el SCRIP debemos ejecutar el scrip de grupo diego

--USUARIOS

INSERT INTO "Accesos" VALUES(1,'USUARIOS-Consultar');
INSERT INTO "Accesos" VALUES(2,'USUARIOS-Agregar');
INSERT INTO "Accesos" VALUES(3,'USUARIOS-Editar');
INSERT INTO "Accesos" VALUES(4,'USUARIOS-Eliminar');

--ROLES-DEL-USUARIO

INSERT INTO "Accesos" VALUES(5,'ROLES-USUARIO-Consultar');
INSERT INTO "Accesos" VALUES(6,'ROLES-USUARIO-Agregar');
INSERT INTO "Accesos" VALUES(7,'ROLES-USUARIO-Editar');
INSERT INTO "Accesos" VALUES(8,'ROLES-USUARIO-Eliminar');


--ROLES

INSERT INTO "Accesos" VALUES(9,'ROLES-Consultar');
INSERT INTO "Accesos" VALUES(10,'ROLES-Agregar');
INSERT INTO "Accesos" VALUES(11,'ROLES-Editar');
INSERT INTO "Accesos" VALUES(12,'ROLES-Eliminar');

--ACCESOS-DEL-ROL

INSERT INTO "Accesos" VALUES(13,'ACCESOS-ROLES-Consultar');
INSERT INTO "Accesos" VALUES(14,'ACCESOS-ROLES-Agregar');
INSERT INTO "Accesos" VALUES(15,'ACCESOS-ROLES-Editar');
INSERT INTO "Accesos" VALUES(16,'ACCESOS-ROLES-Eliminar');

--ACCESOS

INSERT INTO "Accesos" VALUES(17,'ACCESOS-Consultar');
INSERT INTO "Accesos" VALUES(18,'ACCESOS-Agregar');
INSERT INTO "Accesos" VALUES(19,'ACCESOS-Editar');
INSERT INTO "Accesos" VALUES(20,'ACCESOS-Eliminar');

--PERSONAS

INSERT INTO "Accesos" VALUES(21,'PERSONAS-Consultar');
INSERT INTO "Accesos" VALUES(22,'PERSONAS-Agregar');
INSERT INTO "Accesos" VALUES(23,'PERSONAS-Editar');
INSERT INTO "Accesos" VALUES(24,'PERSONAS-Eliminar');


--TIPO-NOTA

INSERT INTO "Accesos" VALUES(25,'TIPO-NOTAS-Consultar');
INSERT INTO "Accesos" VALUES(26,'TIPO-NOTAS-Agregar');
INSERT INTO "Accesos" VALUES(27,'TIPO-NOTAS-Editar');
INSERT INTO "Accesos" VALUES(28,'TIPO-NOTAS-Eliminar');


--PERIODO-INGRESO-NOTAS

INSERT INTO "Accesos" VALUES(29,'PERIODO-INGRESO-NOTAS-Consultar');
INSERT INTO "Accesos" VALUES(30,'PERIODO-INGRESO-NOTAS-Agregar');
INSERT INTO "Accesos" VALUES(31,'PERIODO-INGRESO-NOTAS-Editar');
INSERT INTO "Accesos" VALUES(32,'PERIODO-INGRESO-NOTAS-Eliminar');


--Rol predefinido en la base de datos
INSERT INTO "Roles"("rol_nombre") VALUES('ROOT');


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

--Se crea el usuario por defecto de postgres
--No se le pasa id de persona porque este usuario no esta registrado
INSERT INTO "Usuarios"("usu_username","usu_password", "id_persona")
VALUES('postgres', set_byte( MD5('Holapostgres')::bytea, 4,64));

--SCRIPT USUARIO ROOT
INSERT INTO "Usuarios"("usu_username","usu_password", "id_persona")
VALUES('ROOT', set_byte( MD5('ROOT')::bytea, 4,64), /*Ingresar id de la persona con todos los accesos*/);

INSERT INTO "RolesDelUsuario"("id_rol", "usu_username") VALUES(1,'ROOT');
