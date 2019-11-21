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
