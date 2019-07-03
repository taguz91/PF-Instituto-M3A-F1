CREATE 
	OR REPLACE FUNCTION "public"."actualizarPermisosFaltantes" ( IN id_rol INTEGER ) RETURNS VARCHAR AS $BODY$ BEGIN-- Routine body goes here...
		INSERT INTO "AccesosDelRol" ( id_rol, id_acceso ) SELECT
		id_rol,
		"public"."Accesos".id_acceso 
	FROM
		"public"."Accesos" 
	WHERE
		"public"."Accesos".id_acceso NOT IN ( SELECT DISTINCT "public"."AccesosDelRol".id_acceso FROM "AccesosDelRol" );
	RETURN 'EJECUTADO PARA: ' || id_rol;
	
END $BODY$ LANGUAGE'plpgsql' VOLATILE;




--EJECUTAR FUNCION
SELECT DISTINCT
	"public"."AccesosDelRol".id_rol,
	"public"."actualizarPermisosFaltantes" ( "public"."AccesosDelRol".id_rol ) 
FROM
	"public"."AccesosDelRol";

--FUNCION CREAR Roles

CREATE 
	OR REPLACE FUNCTION "public"."registrarPermisosFnc" () RETURNS TRIGGER AS $BODY$ BEGIN
		INSERT INTO "AccesosDelRol" ( id_rol, id_acceso ) SELECT NEW
		.id_rol AS id_rol,
		"public"."Accesos".id_acceso 
	FROM
		"public"."Accesos";
	RETURN NEW;
	
END $BODY$ LANGUAGE'plpgsql' VOLATILE;
CREATE TRIGGER "registrarPermisosTg" AFTER INSERT ON "public"."Roles" FOR EACH ROW
EXECUTE PROCEDURE "public"."registrarPermisosFnc" ();
