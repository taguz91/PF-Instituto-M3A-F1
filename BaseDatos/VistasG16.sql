/*

    IMPORTANTE!!!!!
    PRIMERO EJECUTAR LA SCRIPT

    "RolPostgres.sql"

*/


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


CREATE OR REPLACE VIEW "public"."ViewPeriodoIngresoNotas" AS  SELECT "PeriodoLectivo".prd_lectivo_nombre,
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

ALTER TABLE "public"."ViewPeriodoIngresoNotas" OWNER TO "permisos";




SELECT
	"Alumnos".id_alumno,
	p_alu.persona_identificacion,
	p_alu.persona_primer_apellido || ' ' ||p_alu.persona_segundo_apellido as "APELLIDOS",
	p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre AS "NOMBRES",
	"AlumnoCurso".almn_curso_nt_1_parcial,
	"AlumnoCurso".almn_curso_nt_examen_interciclo,
	"AlumnoCurso".almn_curso_nt_2_parcial,
	"AlumnoCurso".almn_curso_nt_examen_final,
	"AlumnoCurso".almn_curso_nt_examen_supletorio,
	"AlumnoCurso".almn_curso_asistencia,
	"AlumnoCurso".almn_curso_nota_final,
	"AlumnoCurso".almn_curso_estado,
	"AlumnoCurso".almn_curso_num_faltas,
	"Materias".materia_nombre, 
	"Jornadas".nombre_jornada,
	"Materias".materia_ciclo,
	"Cursos".curso_paralelo,
	"Carreras".carrera_nombre,
	"PeriodoLectivo".prd_lectivo_nombre,
	"Personas".persona_primer_nombre || ' ' ||"Personas".persona_segundo_nombre as "NOMBRE_PROF",
	"Personas".persona_primer_apellido || ' ' ||"Personas".persona_segundo_apellido as "APELLIDO_PROF",
	("AlumnoCurso".almn_curso_num_faltas * "Materias".materia_total_horas)/100 as "% Faltas"
	
FROM
	"AlumnoCurso"
	INNER JOIN "Cursos" ON "AlumnoCurso".id_curso = "Cursos".id_curso
	INNER JOIN "Jornadas" ON "Cursos".id_jornada = "Jornadas".id_jornada
	INNER JOIN "Materias" ON "Cursos".id_materia = "Materias".id_materia
	INNER JOIN "PeriodoLectivo" ON "Cursos".id_prd_lectivo = "PeriodoLectivo".id_prd_lectivo
	INNER JOIN "Alumnos" ON "AlumnoCurso".id_alumno = "Alumnos".id_alumno
	INNER JOIN "Personas" p_alu ON "Alumnos".id_persona = p_alu.id_persona
	INNER JOIN "Docentes" ON "public"."Cursos".id_docente = "public"."Docentes".id_docente
	INNER JOIN "Carreras" ON "public"."Carreras".id_carrera = "public"."Materias".id_carrera
	INNER JOIN "Personas" ON "public"."Personas".id_persona= "public"."Docentes".id_persona
  INNER JOIN "Docente" doc_coor ON "public".id_docente = public
	

	WHERE
	"PeriodoLectivo".prd_lectivo_estado = FALSE 
	AND "Cursos".id_docente = 55 
	AND "PeriodoLectivo".prd_lectivo_nombre = 'SDS   MAYO/2019   OCTUBRE/2019' 
	AND "Cursos".curso_ciclo = 4 
	AND "Cursos".curso_paralelo = 'A' 
	AND "Jornadas".nombre_jornada = 'MATUTINA' 
ORDER BY
	p_alu.persona_primer_apellido ASC;