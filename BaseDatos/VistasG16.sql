CREATE VIEW "public"."Untitled" AS SELECT
"public"."Usuarios".id_usuario,
"public"."Usuarios".usu_username,
"public"."Usuarios".usu_password,
"public"."Usuarios".usu_estado,
"public"."Usuarios".id_persona,
"public"."Personas".id_persona,
"public"."Personas".persona_foto,
"public"."Personas".persona_identificacion,
"public"."Personas".persona_primer_apellido,
"public"."Personas".persona_segundo_apellido,
"public"."Personas".persona_primer_nombre,
"public"."Personas".persona_segundo_nombre
FROM
"public"."Usuarios"
JOIN "public"."Personas" ON "public"."Usuarios".id_persona = "public"."Personas".id_persona;
