--Todos los docentes
CREATE VIEW vdocentes_ed
AS SELECT
id_docente as pro_codigo,
p.persona_identificacion as per_identificacion,
p.persona_primer_apellido as per_primerapellido,
p.persona_segundo_apellido as per_segundoapellido,
p.persona_primer_nombre as per_primernombre,
p.persona_segundo_nombre as per_segundonombre,
p.persona_celular as per_celular,
p.persona_correo as per_correo,
p.persona_fecha_nacimiento as per_fechanacimiento
FROM
public."Personas" p,
public."Docentes" d
WHERE
p.id_persona = d.id_persona AND
persona_activa = true AND
docente_activo = true
ORDER BY
persona_primer_apellido,
persona_segundo_apellido;

--Toda la info
CREATE VIEW  valumnos_ed
AS SELECT
pl.id_prd_lectivo as per_codigo,
pl.prd_lectivo_fecha_inicio as per_fechainicio,
pl.prd_lectivo_fecha_fin as per_fechafin,
cr.id_carrera as car_codigo,
cr.carrera_nombre as car_descripcion,
m.id_materia as mat_codigo,
m.materia_nombre as mat_nombre,
m.materia_ciclo as mat_ciclo,
c.id_curso as matp_numero,
c.curso_paralelo as matp_paralelo,
c.id_docente as matp_profesor,
j.id_jornada as jor_codigo,
j.nombre_jornada as jor_descripcion,
ac.id_almn_curso as alum_materia,
ac.almn_curso_estado as alum_estado,
p.persona_identificacion as per_identificacion,
p.persona_primer_apellido as per_primerapellido,
p.persona_segundo_apellido as per_segundoapellido,
p.persona_primer_nombre as per_primernombre,
p.persona_segundo_nombre as per_segundonombre,
p.persona_correo as per_correo,
p.persona_celular as per_celular

FROM
public."PeriodoLectivo" pl,
public."Cursos" c,
public."Carreras" cr,
public."Alumnos" a,
public."Personas" p,
public."Jornadas" j,
public."AlumnoCurso" ac,
public."Materias" m
WHERE
pl.id_prd_lectivo = c.id_prd_lectivo AND
cr.id_carrera = pl.id_carrera AND
j.id_jornada = c.id_jornada AND
m.id_materia = c.id_materia AND
ac.id_curso = c.id_curso AND
a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
pl.prd_lectivo_activo = true AND
c.curso_activo = true AND
cr.carrera_activo = true AND
a.alumno_activo = true AND
p.persona_activa = true AND
ac.almn_curso_activo = true AND
m.materia_activa = true
ORDER BY
cr.carrera_nombre,
j.nombre_jornada,
m.materia_nombre,
m.materia_ciclo,
c.curso_paralelo,
p.persona_primer_apellido,
p.persona_segundo_apellido;

--Todos los periodos
CREATE VIEW vperiodos_ed
AS SELECT
pl.id_prd_lectivo as per_codigo,
pl.prd_lectivo_fecha_inicio as per_fechainicio,
pl.prd_lectivo_fecha_fin as per_fechafin,
cr.id_carrera as car_codigo,
cr.carrera_nombre as car_descripcion,
cr.carrera_codigo as car_id
FROM
public."PeriodoLectivo" pl,
public."Carreras" cr
WHERE
cr.id_carrera = pl.id_carrera AND
pl.prd_lectivo_activo = true AND
cr.carrera_activo = true;
