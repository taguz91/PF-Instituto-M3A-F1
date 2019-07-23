--Todos los docentes del instituto, con informacion basica
SELECT
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

--Todos los alumnos matriculados entre periodos
SELECT
pl.id_prd_lectivo,
pl.prd_lectivo_fecha_inicio,
pl.prd_lectivo_fecha_fin,
cr.id_carrera,
cr.carrera_nombre,
m.id_materia,
m.materia_nombre,
m.materia_ciclo,
c.id_curso,
c.curso_paralelo,
c.id_docente,
j.id_jornada,
j.nombre_jornada,
ac.id_almn_curso,
ac.almn_curso_estado,
p.persona_identificacion,
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_correo,
p.persona_celular
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
m.materia_activa = true AND
/*Aqui reemplazar por los periodos de los que se quiera sacar la informacion*/
pl.id_prd_lectivo BETWEEN 21 AND 29
ORDER BY
cr.carrera_nombre,
j.nombre_jornada,
m.materia_nombre,
m.materia_ciclo,
c.curso_paralelo,
p.persona_primer_apellido,
p.persona_segundo_apellido


--Todos los alumnos matriculas en un periodo de un docente en especifico
SELECT
pl.id_prd_lectivo,
pl.prd_lectivo_fecha_inicio,
pl.prd_lectivo_fecha_fin,
cr.id_carrera,
cr.carrera_nombre,
m.id_materia,
m.materia_nombre,
m.materia_ciclo,
c.id_curso,
c.curso_paralelo,
c.id_docente,
j.id_jornada,
j.nombre_jornada,
ac.id_almn_curso,
ac.almn_curso_estado,
p.persona_identificacion,
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_correo,
p.persona_celular

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
m.materia_activa = true AND
/*Esepecifique el docente*/
c.id_docente = 11 AND
/*Periodo*/
pl.id_prd_lectivo BETWEEN 21 AND 29
ORDER BY
cr.carrera_nombre,
j.nombre_jornada,
m.materia_nombre,
m.materia_ciclo,
c.curso_paralelo,
p.persona_primer_apellido,
p.persona_segundo_apellido

--Todas las materias en las que esta inscrito un estudiante entre periodos

SELECT
pl.id_prd_lectivo,
pl.prd_lectivo_fecha_inicio,
pl.prd_lectivo_fecha_fin,
cr.id_carrera,
cr.carrera_nombre,
m.id_materia,
m.materia_nombre,
m.materia_ciclo,
c.id_curso,
c.curso_paralelo,
c.id_docente,
j.id_jornada,
j.nombre_jornada,
ac.id_almn_curso,
ac.almn_curso_estado,
p.persona_identificacion,
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_correo,
p.persona_celular

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
m.materia_activa = true AND
/*Cedula del estudiante*/
p.persona_identificacion = '0107049736' AND
/*Periodo*/
pl.id_prd_lectivo BETWEEN 21 AND 29
ORDER BY
cr.carrera_nombre,
j.nombre_jornada,
m.materia_nombre,
m.materia_ciclo,
c.curso_paralelo,
p.persona_primer_apellido,
p.persona_segundo_apellido


--Consultamos todos los periodos entre ids que esepecificamos
SELECT
pl.id_prd_lectivo,
pl.prd_lectivo_fecha_inicio,
pl.prd_lectivo_fecha_fin,
cr.id_carrera,
cr.carrera_nombre,
cr.carrera_codigo
FROM
public."PeriodoLectivo" pl,
public."Carreras" cr
WHERE
cr.id_carrera = pl.id_carrera AND
pl.prd_lectivo_activo = true AND
cr.carrera_activo = true AND
pl.id_prd_lectivo BETWEEN 21 AND 29
