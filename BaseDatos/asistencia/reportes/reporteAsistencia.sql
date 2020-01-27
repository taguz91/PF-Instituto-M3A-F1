SELECT
SUM("Asistencia".numero_faltas) AS "NUMERO FALTAS",
ROUND(
  (SUM("Asistencia".numero_faltas) * 100)
  /ValidarHorasMateria ("Materias".materia_horas_presencial)
) || '%' AS "% FALTAS",
"Alumnos".id_alumno,
p_alu.persona_identificacion,
p_alu.persona_primer_apellido || ' ' ||
p_alu.persona_segundo_apellido AS "apellidos_al",
p_alu.persona_primer_nombre || ' ' ||
p_alu.persona_segundo_nombre AS "nombres_al",
"Materias".materia_nombre,
"Cursos".curso_nombre,
"Carreras".carrera_nombre,
"PeriodoLectivo".prd_lectivo_nombre,
"Docentes".docente_abreviatura || ' ' ||
"Personas".persona_primer_apellido || ' ' ||
"Personas".persona_segundo_apellido || ' ' ||
"Personas".persona_primer_nombre || ' ' ||
"Personas".persona_segundo_nombre AS "PROFESOR",
doc_coor.docente_abreviatura || ' ' ||
per_coor.persona_primer_apellido || ' ' ||
per_coor.persona_segundo_apellido || ' ' ||
per_coor.persona_primer_nombre || ' ' ||
per_coor.persona_segundo_nombre AS "COORDINADOR",
max("MallaAlumno".malla_almn_num_matricula) AS "N° Matricula",
"upper"("AlumnoCurso".almn_curso_asistencia) AS "Asistencia"

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
	INNER JOIN "Personas" ON "public"."Personas".id_persona = "public"."Docentes".id_persona
	INNER JOIN "Docentes" doc_coor ON doc_coor.id_docente = "public"."Carreras".id_docente_coordinador
  INNER JOIN "Personas" per_coor ON per_coor.id_persona = doc_coor.id_persona
	INNER JOIN "AlumnosCarrera" ON "AlumnosCarrera".id_alumno = "Alumnos".id_alumno
	INNER JOIN "MallaAlumno" on "AlumnosCarrera".id_almn_carrera = "MallaAlumno".id_almn_carrera
	INNER JOIN "Asistencia" ON "Asistencia".id_almn_curso = "AlumnoCurso".id_almn_curso

WHERE "Cursos".id_curso = 1228
AND "PeriodoLectivo".id_prd_lectivo = "Cursos".id_prd_lectivo
AND "MallaAlumno".id_materia = "Materias".id_materia
AND "AlumnoCurso".almn_curso_activo = true

GROUP BY "Alumnos".id_alumno,
p_alu.persona_identificacion,
p_alu.persona_primer_apellido || ' ' ||
p_alu.persona_segundo_apellido,
p_alu.persona_primer_nombre || ' ' ||
p_alu.persona_segundo_nombre,
"Materias".materia_nombre,
"Cursos".curso_nombre,
"Carreras".carrera_nombre,
"PeriodoLectivo".prd_lectivo_nombre,
"Docentes".docente_abreviatura || ' ' ||
"Personas".persona_primer_apellido || ' ' ||
"Personas".persona_segundo_apellido ||' '||
"Personas".persona_primer_nombre || ' ' ||
"Personas".persona_segundo_nombre,
doc_coor.docente_abreviatura || ' ' ||
per_coor.persona_primer_apellido || ' ' ||
per_coor.persona_segundo_apellido || ' ' ||
per_coor.persona_primer_nombre || ' ' ||
per_coor.persona_segundo_nombre,
"upper"("AlumnoCurso".almn_curso_asistencia),
"Materias".materia_horas_presencial
ORDER BY p_alu.persona_primer_apellido || ' ' || p_alu.persona_segundo_apellido;



-- POR DIA


SELECT
"Alumnos".id_alumno,
p_alu.persona_identificacion,
p_alu.persona_primer_apellido || ' ' ||
p_alu.persona_segundo_apellido AS "apellidos_al",
p_alu.persona_primer_nombre || ' ' ||
p_alu.persona_segundo_nombre AS "nombres_al",
"Materias".materia_nombre,
"Cursos".curso_nombre,
"Carreras".carrera_nombre,
"PeriodoLectivo".prd_lectivo_nombre,
"Docentes".docente_abreviatura ||' ' ||
"Personas".persona_primer_apellido || ' ' ||
"Personas".persona_segundo_apellido ||' '||
"Personas".persona_primer_nombre || ' ' ||
"Personas".persona_segundo_nombre AS "PROFESOR",
ROUND(
  ("Asistencia".numero_faltas* 100) / ValidarHorasMateria("Materias".materia_horas_presencial)
) || '%'  AS "% Faltas",
doc_coor.docente_abreviatura ||' '||
per_coor.persona_primer_apellido || ' ' ||
per_coor.persona_segundo_apellido || ' ' ||
per_coor.persona_primer_nombre || ' ' ||
per_coor.persona_segundo_nombre AS "COORDINADOR",
max("MallaAlumno".malla_almn_num_matricula) AS "N� Matricula",
"upper"("AlumnoCurso".almn_curso_asistencia) AS "Asistencia",
SUM("Asistencia".numero_faltas),
"Asistencia".fecha_asistencia

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
	INNER JOIN "Personas" ON "public"."Personas".id_persona = "public"."Docentes".id_persona
	INNER JOIN "Docentes" doc_coor ON doc_coor.id_docente = "public"."Carreras".id_docente_coordinador
  INNER JOIN "Personas" per_coor ON per_coor.id_persona = doc_coor.id_persona
	INNER JOIN "AlumnosCarrera" ON "AlumnosCarrera".id_alumno = "Alumnos".id_alumno
	INNER JOIN "MallaAlumno" on "AlumnosCarrera".id_almn_carrera = "MallaAlumno".id_almn_carrera
	INNER JOIN "Asistencia" on "AlumnoCurso".id_almn_curso = "Asistencia".id_almn_curso

WHERE
"Cursos".id_curso =
AND "PeriodoLectivo".id_prd_lectivo = "Cursos".id_prd_lectivo
AND "MallaAlumno".id_materia = "Materias".id_materia
and "Asistencia".fecha_asistencia =  to_date($P{fecha_asistencia}, 'DD/MM/YYYY')
GROUP BY "Alumnos".id_alumno,
p_alu.persona_identificacion,
p_alu.persona_primer_apellido || ' ' || p_alu.persona_segundo_apellido,
p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre,
"Materias".materia_nombre,
"Cursos".curso_nombre,
"Carreras".carrera_nombre,
"PeriodoLectivo".prd_lectivo_nombre,
"Docentes".docente_abreviatura ||' ' ||"Personas".persona_primer_apellido || ' ' || "Personas".persona_segundo_apellido ||' '|| "Personas".persona_primer_nombre || ' ' || "Personas".persona_segundo_nombre,
ROUND(("AlumnoCurso".almn_curso_num_faltas * 100)/ValidarHorasMateria("Materias".materia_horas_presencial))|| '%',
doc_coor.docente_abreviatura||' '|| per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido || ' ' ||
per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre,
"upper"("AlumnoCurso".almn_curso_asistencia),
"Asistencia".fecha_asistencia,
"Asistencia".numero_faltas,
"Materias".materia_horas_presencial

ORDER BY p_alu.persona_primer_apellido || ' ' || p_alu.persona_segundo_apellido;


--UBE


SELECT
Sum("public"."Asistencia".numero_faltas) AS "NUMERO FALTAS",
ROUND(
  (SUM("Asistencia".numero_faltas) * 100) / ValidarHorasMateria("Materias".materia_horas_presencial)
)|| '%' AS "% FALTAS",
"public"."Alumnos".id_alumno,
p_alu.persona_identificacion,
p_alu.persona_primer_apellido || ' ' ||
p_alu.persona_segundo_apellido AS apellidos_al,
p_alu.persona_primer_nombre || ' ' ||
p_alu.persona_segundo_nombre AS nombres_al,
"public"."Materias".materia_nombre,
"public"."Cursos".curso_nombre,
"public"."Carreras".carrera_nombre,
"public"."PeriodoLectivo".prd_lectivo_nombre,
"Docentes".docente_abreviatura ||' ' ||
"Personas".persona_primer_apellido || ' ' ||
"Personas".persona_segundo_apellido ||' '||
"Personas".persona_primer_nombre || ' ' ||
"Personas".persona_segundo_nombre AS "PROFESOR",
doc_coor.docente_abreviatura ||' '||
per_coor.persona_primer_apellido || ' ' ||
per_coor.persona_segundo_apellido || ' ' ||
per_coor.persona_primer_nombre || ' ' ||
per_coor.persona_segundo_nombre AS "COORDINADOR",
Max("public"."MallaAlumno".malla_almn_num_matricula) AS "N° Matricula",
"upper"("AlumnoCurso".almn_curso_asistencia) AS "Asistencia",
p_alu.persona_telefono,
p_alu.persona_celular,
p_alu.persona_correo
FROM
"public"."AlumnoCurso"
INNER JOIN "public"."Cursos" ON "public"."AlumnoCurso".id_curso = "public"."Cursos".id_curso
INNER JOIN "public"."Jornadas" ON "public"."Cursos".id_jornada = "public"."Jornadas".id_jornada
INNER JOIN "public"."Materias" ON "public"."Cursos".id_materia = "public"."Materias".id_materia
INNER JOIN "public"."PeriodoLectivo" ON "public"."Cursos".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
INNER JOIN "public"."Alumnos" ON "public"."AlumnoCurso".id_alumno = "public"."Alumnos".id_alumno
INNER JOIN "public"."Personas" AS p_alu ON "public"."Alumnos".id_persona = p_alu.id_persona
INNER JOIN "public"."Docentes" ON "public"."Cursos".id_docente = "public"."Docentes".id_docente
INNER JOIN "public"."Carreras" ON "public"."Carreras".id_carrera = "public"."Materias".id_carrera
INNER JOIN "public"."Personas" ON "public"."Personas".id_persona = "public"."Docentes".id_persona
INNER JOIN "public"."Docentes" AS doc_coor ON doc_coor.id_docente = "public"."Carreras".id_docente_coordinador
INNER JOIN "public"."Personas" AS per_coor ON per_coor.id_persona = doc_coor.id_persona
INNER JOIN "public"."AlumnosCarrera" ON "public"."AlumnosCarrera".id_alumno = "public"."Alumnos".id_alumno
INNER JOIN "public"."MallaAlumno" ON "public"."AlumnosCarrera".id_almn_carrera = "public"."MallaAlumno".id_almn_carrera
INNER JOIN "public"."Asistencia" ON "public"."Asistencia".id_almn_curso = "public"."AlumnoCurso".id_almn_curso
WHERE "public"."Cursos".id_curso =
AND public."PeriodoLectivo".id_prd_lectivo = "Cursos".id_prd_lectivo AND
public."MallaAlumno".id_materia = "public"."Materias".id_materia
AND "AlumnoCurso".almn_curso_activo = true
GROUP BY
"public"."Alumnos".id_alumno,
p_alu.persona_identificacion,
p_alu.persona_primer_apellido || ' ' || p_alu.persona_segundo_apellido,
p_alu.persona_primer_nombre || ' ' || p_alu.persona_segundo_nombre,
"public"."Materias".materia_nombre,
"public"."Cursos".curso_nombre,
"public"."Carreras".carrera_nombre,
"public"."PeriodoLectivo".prd_lectivo_nombre,
"Docentes".docente_abreviatura ||' ' ||"Personas".persona_primer_apellido || ' ' || "Personas".persona_segundo_apellido ||' '|| "Personas".persona_primer_nombre || ' ' || "Personas".persona_segundo_nombre,
doc_coor.docente_abreviatura||' '|| per_coor.persona_primer_apellido || ' ' || per_coor.persona_segundo_apellido || ' ' ||
  per_coor.persona_primer_nombre || ' ' ||per_coor.persona_segundo_nombre,
"upper"("AlumnoCurso".almn_curso_asistencia),
"public"."Materias".materia_horas_presencial,
p_alu.persona_telefono,
p_alu.persona_celular,
p_alu.persona_correo
ORDER BY
p_alu.persona_primer_apellido || ' ' || p_alu.persona_segundo_apellido ASC
