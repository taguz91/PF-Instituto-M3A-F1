
/*
    
*/

		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'BDcierre';
		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'BDinsta';

		select count(*) from pg_stat_activity WHERE datname = 'BDinsta'


--UPDATE PARA CONTRASEÑA DE LOS DOCENTES

UPDATE "Usuarios" 
SET usu_password = set_byte( MD5( 'BLOCK' ) :: bytea, 4, 64 ) 
WHERE
	usu_username IN (
	SELECT
		rol_1.usu_username 
	FROM
		"public"."Roles"
		INNER JOIN "public"."RolesDelUsuario" rol_1 ON rol_1.id_rol = "public"."Roles".id_rol 
	WHERE
	"Roles".rol_nombre = 'DOCENTE' 
	AND 1 = ( SELECT "count" ( * ) FROM "RolesDelUsuario" rol_2 WHERE rol_2.usu_username = rol_1.usu_username ));