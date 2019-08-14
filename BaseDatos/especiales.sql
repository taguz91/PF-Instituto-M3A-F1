
/*
    
*/

		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'BDcierre';
		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'BDinsta';

		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE usename = 'JOHNNY';
		select count(*) from pg_stat_activity WHERE datname = 'BDinsta';


		SELECT * FROM pg_stat_activity 		ORDER BY usename;
		   WHERE usename = '0102264827' 

		pg_dump -Fc -v -h 35.193.226.187  -p 5432 -U postgres "BDinsta" > whd_pgdump.backup
		/home/diegocondo1007/IstaServer
		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE usename = '0105003198';
		
		
		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE query= 'SET application_name = ''PostgreSQL JDBC Driver''';

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