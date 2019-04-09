/*

    IMPORTANTE!!!!!
    PRIMERO EJECUTAR LA SCRIPT

    "RolPostgres.sql"

*/
CREATE MATERIALIZED VIEW "public"."Usuarios_Persona"
AS
SELECT "Usuarios".id_usuario,
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

ALTER MATERIALIZED VIEW "public"."Usuarios_Persona" OWNER TO "permisos";

CREATE UNIQUE INDEX "usuariospersona" ON "public"."Usuarios_Persona" USING btree (
  "id_usuario" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_persona" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "persona_identificacion" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "persona_primer_apellido" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "persona_primer_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE OR REPLACE FUNCTION actualizar_vista_usuarios_persona()
RETURNS TRIGGER AS $actualizar_vista_usuarios_persona$
BEGIN
 REFRESH MATERIALIZED VIEW "Usuarios_Persona";
 RETURN NEW;
END;
$actualizar_vista_usuarios_persona$ LANGUAGE plpgsql;


CREATE TRIGGER actualizar_on_personas
AFTER INSERT OR UPDATE
ON public."Personas" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vista_usuarios_persona();
 
 CREATE TRIGGER actualizar_on_usuarios
AFTER INSERT OR UPDATE
ON public."Usuarios" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vista_usuarios_persona();
 


 
 
 
 
 
 
 
 
 
 
/*

  ALUMNO CURSO

*/
CREATE MATERIALIZED VIEW "public"."ViewAlumnoCurso"
AS
SELECT "AlumnoCurso".id_almn_curso,
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
    "Alumnos".alumno_codigo,
    "AlumnoCurso".almn_curso_fecha_registro,
    "Cursos".id_materia,
    "Cursos".id_prd_lectivo,
    "Cursos".id_docente,
    "Cursos".id_jornada,
    "Cursos".curso_nombre,
    "Cursos".curso_capacidad,
    "Cursos".curso_ciclo,
    "Cursos".curso_paralelo,
    "PeriodoLectivo".id_carrera,
    "PeriodoLectivo".prd_lectivo_nombre,
    "PeriodoLectivo".prd_lectivo_estado,
    "PeriodoLectivo".prd_lectivo_activo
   FROM (((("AlumnoCurso"
     JOIN "Alumnos" ON (("AlumnoCurso".id_alumno = "Alumnos".id_alumno)))
     JOIN "Personas" ON (("Alumnos".id_persona = "Personas".id_persona)))
     JOIN "Cursos" ON (("AlumnoCurso".id_curso = "Cursos".id_curso)))
     JOIN "PeriodoLectivo" ON (("Cursos".id_prd_lectivo = "PeriodoLectivo".id_prd_lectivo)));

ALTER MATERIALIZED VIEW "public"."ViewAlumnoCurso" OWNER TO "permisos";

CREATE UNIQUE INDEX "viewalumnocurso" ON "public"."ViewAlumnoCurso" USING btree (
  "id_almn_curso" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_alumno" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_curso" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "persona_identificacion" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "persona_primer_apellido" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "persona_primer_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "id_persona" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "alumno_codigo" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE OR REPLACE FUNCTION actualizar_vista_AlumnoCurso()
RETURNS TRIGGER AS $actualizar_vista_AlumnoCurso$
BEGIN
 REFRESH MATERIALIZED VIEW "ViewAlumnoCurso";
 RETURN NEW;
END;
$actualizar_vista_AlumnoCurso$ LANGUAGE plpgsql;


CREATE TRIGGER actualizar_vistas_AlumnoCurso
AFTER INSERT OR UPDATE
ON public."AlumnoCurso" FOR EACH ROW
 EXECUTE PROCEDURE actualizar_vista_AlumnoCurso();







/*
  PERMISOS DE IMGRESO DE NOTAS EN UN CURSO
*/CREATE MATERIALIZED VIEW "public"."ViewCursosPermisosNotas"
AS
SELECT "IngresoNotas".nota_primer_inteciclo,
    "IngresoNotas".nota_examen_intecilo,
    "IngresoNotas".nota_segundo_inteciclo,
    "IngresoNotas".nota_examen_final,
    "IngresoNotas".nota_examen_de_recuperacion,
    "IngresoNotas".id_ingreso_notas,
    "IngresoNotas".id_curso,
    "Cursos".id_materia,
    "Cursos".id_prd_lectivo,
    "Cursos".id_docente,
    "Cursos".curso_nombre,
    "Materias".materia_nombre,
    "Materias".materia_codigo,
    "PeriodoLectivo".prd_lectivo_nombre,
    "Personas".persona_identificacion,
    "Personas".persona_primer_apellido,
    "Personas".persona_segundo_apellido,
    "Personas".persona_primer_nombre,
    "Personas".persona_segundo_nombre,
    "PeriodoLectivo".prd_lectivo_activo,
    "PeriodoLectivo".prd_lectivo_estado
   FROM ((((("IngresoNotas"
     JOIN "Cursos" ON (("IngresoNotas".id_curso = "Cursos".id_curso)))
     JOIN "Materias" ON (("Cursos".id_materia = "Materias".id_materia)))
     JOIN "PeriodoLectivo" ON (("Cursos".id_prd_lectivo = "PeriodoLectivo".id_prd_lectivo)))
     JOIN "Docentes" ON (("Cursos".id_docente = "Docentes".id_docente)))
     JOIN "Personas" ON (("Docentes".id_persona = "Personas".id_persona)));

ALTER MATERIALIZED VIEW "public"."ViewCursosPermisosNotas" OWNER TO "permisos";

CREATE UNIQUE INDEX "cursospermisosnotas" ON "public"."ViewCursosPermisosNotas" USING btree (
  "id_ingreso_notas" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_curso" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_materia" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_prd_lectivo" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_docente" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "curso_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "materia_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "prd_lectivo_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "persona_identificacion" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "persona_primer_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "persona_primer_apellido" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);


/*
  PERIODO INGRESO NOTAS
*/
CREATE MATERIALIZED VIEW "public"."ViewPeriodoIngresoNotas"
AS
SELECT "PeriodoLectivo".prd_lectivo_nombre,
    "PeriodoIngresoNotas".perd_notas_fecha_inicio,
    "PeriodoIngresoNotas".perd_notas_fecha_cierre,
    "TipoDeNota".tipo_nota_nombre,
    "PeriodoIngresoNotas".perd_notas_estado,
    "TipoDeNota".tipo_nota_estado,
    "PeriodoIngresoNotas".id_prd_lectivo,
    "PeriodoIngresoNotas".id_tipo_nota,
    "PeriodoIngresoNotas".id_perd_ingr_notas
   FROM (("PeriodoLectivo"
     JOIN "PeriodoIngresoNotas" ON (("PeriodoIngresoNotas".id_prd_lectivo = "PeriodoLectivo".id_prd_lectivo)))
     JOIN "TipoDeNota" ON (("PeriodoIngresoNotas".id_tipo_nota = "TipoDeNota".id_tipo_nota)));

ALTER MATERIALIZED VIEW "public"."ViewPeriodoIngresoNotas" OWNER TO "permisos";

CREATE UNIQUE INDEX "viewperiodoingresonotas" ON "public"."ViewPeriodoIngresoNotas" USING btree (
  "prd_lectivo_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tipo_nota_nombre" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "id_prd_lectivo" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_tipo_nota" "pg_catalog"."int4_ops" ASC NULLS LAST,
  "id_perd_ingr_notas" "pg_catalog"."int4_ops" ASC NULLS LAST
);


