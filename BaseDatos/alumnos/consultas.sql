SELECT
id_prd_lectivo,
prd_lectivo_nombre
FROM public."PeriodoLectivo" pl
WHERE pl.id_carrera IN (
  SELECT id_carrera
  FROM public."AlumnosCarrera"
  WHERE id_almn_carrera = ?
) ORDER BY prd_lectivo_fecha_fin;


SELECT
r.id_retiro,
fecha_retiro,
ac.id_almn_carrera,
ac.id_carrera,
almn_carrera_fecha_registro,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_codigo,
prd_lectivo_nombre,
r.id_prd_lectivo
FROM public."AlumnosCarrera" ac
JOIN public."Alumnos" a ON a.id_alumno = ac.id_alumno
JOIN public."Personas" p ON p.id_persona = a.id_persona
JOIN public."Carreras" c ON c.id_carrera = ac.id_carrera
JOIN alumno."Retirados" r ON r.id_almn_carrera = ac.id_almn_carrera
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = ac.id_prd_lectivo
WHERE carrera_activo = true
AND almn_carrera_activo = true


SELECT
id_retiro,
id_almn_carrera,
id_prd_lectivo,
fecha_retiro,
motivo_retiro
FROM alumno."Retirados"
WHERE id_retiro = ?;

-- Seleccionado el ultimo periodo en el que se graduo
SELECT
id_prd_lectivo,
prd_lectivo_nombre
FROM public."PeriodoLectivo" pl
JOIN public."Matricula" m ON pl.id_prd_lectivo = m.id_prd_lectivo
WHERE pl.id_carrera IN (
  SELECT id_carrera
  FROM public."AlumnosCarrera"
  WHERE id_almn_carrera = ?
) AND m.id_alumno = (
  SELECT id_alumno
  FROM public."AlumnosCarrera"
  WHERE id_almn_carrera = ?
) ORDER BY prd_lectivo_fecha_fin;


--- Insert de los egresados

INSERT INTO alumno."Egresados"(
id_almn_carrera,
id_prd_lectivo,
fecha_egreso,
graduado,
fecha_graduacion )
VALUES (?, ?, ?, ?, ?);



INSERT INTO alumno."Egresados"(
id_almn_carrera,
id_prd_lectivo,
fecha_egreso)
VALUES (?, ?, ?);

-- Updates

UPDATE alumno."Egresados" SET
id_almn_carrera=?,
id_prd_lectivo=?,
fecha_egreso=?,
graduado=?,
fecha_graduacion=?
 WHERE id_egresado=?;

 UPDATE alumno."Egresados" SET
 id_prd_lectivo=?,
 fecha_egreso=?
  WHERE id_egresado=?;

-- Alumnos

-- Johnnatan Ribera

-- Egresados
SELECT
id_egresado,
e.id_almn_carrera,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_codigo,
prd_lectivo_nombre,
fecha_egreso,
graduado
FROM alumno."Egresados" e
JOIN public."AlumnosCarrera" ac
ON e.id_almn_carrera = ac.id_almn_carrera
JOIN public."Carreras" c
ON c.id_carrera = ac.id_carrera
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = e.id_prd_lectivo
JOIN public."Alumnos" a
ON ac.id_alumno = a.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE graduado = false
ORDER BY fecha_egreso DESC;

-- Graduados

SELECT
id_egresado,
e.id_almn_carrera,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_codigo,
prd_lectivo_nombre,
fecha_graduacion
FROM alumno."Egresados" e
JOIN public."AlumnosCarrera" ac
ON e.id_almn_carrera = ac.id_almn_carrera
JOIN public."Carreras" c
ON c.id_carrera = ac.id_carrera
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = e.id_prd_lectivo
JOIN public."Alumnos" a
ON ac.id_alumno = a.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE graduado = true
ORDER BY fecha_graduacion DESC;
