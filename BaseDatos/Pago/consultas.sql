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

-- Consultamos las materias que tenemos pagos pendientes

SELECT
id_malla_alumno,
materia_nombre,
materia_codigo
FROM publc."MallaAlumno" ma
JOIN public."Materias" m
ON ma.id_materia = m.id_materia
WHERE malla_almn_pago_pendiente = true
AND id_almn_carrera IN (
  SELECT id_almn_carrera
  FROM public."AlumnosCarrera"
  WHERE id_alumno = ?
) AND malla_almn_pago_pendiente = true  
ORDER BY materia_ciclo;
