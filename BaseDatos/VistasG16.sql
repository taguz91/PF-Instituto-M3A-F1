CREATE OR REPLACE VIEW "public"."Usuarios_Persona" AS  SELECT "Usuarios".id_usuario,
    "Usuarios".usu_username,
    "Usuarios".usu_password,
    "Usuarios".usu_estado,
    "Usuarios".id_persona,
    "Personas".persona_identificacion,
    "Personas".persona_primer_apellido,
    "Personas".persona_segundo_apellido,
    "Personas".persona_primer_nombre,
    "Personas".persona_segundo_nombre,
    "Personas".persona_foto
   FROM ("Usuarios"
     JOIN "Personas" ON (("Usuarios".id_persona = "Personas".id_persona)));

ALTER TABLE "public"."Usuarios_Persona" OWNER TO "permisos";

CREATE OR REPLACE VIEW "public"."ViewAlumnoCurso" AS  SELECT "AlumnoCurso".id_almn_curso,
    "AlumnoCurso".id_alumno,
    "AlumnoCurso".id_curso,
    "AlumnoCurso".almn_curso_nt_1_parcial,
    "AlumnoCurso".almn_curso_nt_examen_interciclo,
    "AlumnoCurso".almn_curso_nt_2_parcial,
    "AlumnoCurso".almn_curso_nt_examen_final,
    "AlumnoCurso".almn_curso_nt_examen_supletorio,
    "AlumnoCurso".almn_curso_asistencia,
    "AlumnoCurso".almn_curso_nota_final,
    "AlumnoCurso".almn_curso_estado,
    "AlumnoCurso".almn_curso_num_faltas,
    "Personas".persona_identificacion,
    "Personas".persona_primer_apellido,
    "Personas".persona_segundo_apellido,
    "Personas".persona_primer_nombre,
    "Personas".persona_segundo_nombre,
    "Personas".id_persona,
    "Alumnos".alumno_codigo
   FROM (("AlumnoCurso"
     JOIN "Alumnos" ON (("AlumnoCurso".id_alumno = "Alumnos".id_alumno)))
     JOIN "Personas" ON (("Alumnos".id_persona = "Personas".id_persona)));

ALTER TABLE "public"."ViewAlumnoCurso" OWNER TO "permisos";