SELECT
id_alumno,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido
FROM public."Alumnos" a
JOIN public."Personas" p
ON a.id_persona = p.id_persona
WHERE id_alumno IN (
  SELECT id_alumno
  FROM public."AlumnosCarrera"
  WHERE id_carrera IN (
    SELECT id_carrera
    FROM public."PeriodoLectivo"
    WHERE id_prd_lectivo = ?
  )
) AND (
  persona_primer_nombre ILIKE ?
  OR persona_segundo_nombre ILIKE ?
  OR persona_primer_apellido ILIKE ?
  OR persona_segundo_apellido ILIKE ?
  OR persona_identificacion ILIKE ?
  OR persona_primer_nombre || ' ' ||
  persona_primer_apellido ILIKE ?
) ORDER BY persona_primer_apellido;


-- Consultamos el comprobante por alumno y periodo
SELECT
id_comprobante_pago,
comprobante,
comprobante_total,
comprobante_codigo,
comprobante_fecha_pago,
comprobante_observaciones
FROM pago."ComprobantePago"
WHERE id_prd_lectivo = ?
AND id_alumno ?;

-- Consultamos los pagos por comprobante

SELECT
id_pago_materia,
pm.id_malla_alumno,
pago_materia,
pago_numero_matricula,
materia_nombre
FROM pago."PagoMateria" pm
JOIN public."MallaAlumno" ma
ON ma.id_malla_alumno = pm.id_malla_alumno
JOIN public."Materias" m
ON m.id_materia = ma.id_materia
WHERE id_comprobante = ?;


-- Consultamos las materias que tenemos pagos pendientes

SELECT
id_malla_alumno,
malla_almn_num_matricula,
materia_nombre,
materia_codigo
FROM public."MallaAlumno" ma
JOIN public."Materias" m
ON ma.id_materia = m.id_materia
WHERE id_almn_carrera IN (
  SELECT id_almn_carrera
  FROM public."AlumnosCarrera"
  WHERE id_alumno = ?
) AND (
  malla_almn_num_matricula > 1 OR (
    malla_almn_estado = 'R'
    AND malla_almn_num_matricula = 1
  )
) AND malla_almn_pago_pendiente = true
ORDER BY materia_ciclo;

-- Consultamos las materias que tengamos pendiente el pago
SELECT
id_malla_alumno,
malla_almn_num_matricula,
materia_nombre,
materia_ciclo
FROM public."MallaAlumno" ma
JOIN public."Materias" m
ON ma.id_materia = m.id_materia
WHERE id_almn_carrera IN (
  SELECT id_almn_carrera
  FROM public."AlumnosCarrera"
  WHERE id_alumno = ?
) AND malla_almn_pago_pendiente = true
ORDER BY materia_ciclo;
