-- Consultando los periodos en el que tengo 2 o matriculas extraordinarias o especiales
SELECT
id_prd_lectivo,
prd_lectivo_nombre
FROM public."PeriodoLectivo" pl
WHERE id_prd_lectivo IN (
  SELECT id_prd_lectivo
  FROM public."AlumnoCurso"
  WHERE almn_curso_num_matricula > 1
  id_alumno = ?
) AND id_prd_lectivo IN (
  SELECT id_prd_lectivo
  FROM public."Matricula"
  WHERE matricula_tipo <> 'ORDINARIA'
  id_alumno = ?
) AND id_prd_lectivo NOT IN (
  SELECT id_prd_lectivo
  FROM pago."Comprobante"
  WHERE id_alumno = ?
) ORDER BY prd_lectivo_fecha_fin DESC;

-- Consultamos las materias en las que nos faltan pagos

SELECT
id_malla_alumno,
materia_nombre,
materia_ciclo
FROM public."MallaAlumno" ma
JOIN public."Materias" m
ON ma.id_materia = m.id_materia
WHERE id_malla_alumno NOT IN (
  SELECT id_malla_alumno
  FROM pago."Pago" p
  JOIN public."Comprobante" c
  ON c.id_comprobante = p.id_comprobante
  WHERE pago_numero_matricula = 2
  AND id_alumno = ?
) AND malla_almn_num_matricula = 2
AND id_almn_carrera = ?

UNION

SELECT
id_malla_alumno,
materia_nombre,
materia_ciclo
FROM public."MallaAlumno" ma
JOIN public."Materias" m
ON ma.id_materia = m.id_materia
WHERE id_malla_alumno NOT IN (
  SELECT id_malla_alumno
  FROM pago."Pago" p
  JOIN public."Comprobante" c
  ON c.id_comprobante = p.id_comprobante
  WHERE pago_numero_matricula = 3
  AND id_alumno = ?
) AND malla_almn_num_matricula = 3
AND id_almn_carrera = ?

UNION

SELECT
id_malla_alumno,
materia_nombre,
materia_ciclo
FROM public."MallaAlumno" ma
JOIN public."Materias" m
ON ma.id_materia = m.id_materia
WHERE id_malla_alumno NOT IN (
  SELECT id_malla_alumno
  FROM pago."Pago" p
  JOIN public."Comprobante" c
  ON c.id_comprobante = p.id_comprobante
  WHERE pago_numero_matricula = 2
  AND id_alumno = ?
) AND malla_almn_num_matricula = 1
AND malla_almn_estado = 'R'
AND id_almn_carrera = ?

UNION

SELECT
id_malla_alumno,
materia_nombre,
materia_ciclo
FROM public."MallaAlumno" ma
JOIN public."Materias" m
ON ma.id_materia = m.id_materia
WHERE id_malla_alumno NOT IN (
  SELECT id_malla_alumno
  FROM pago."Pago" p
  JOIN public."Comprobante" c
  ON c.id_comprobante = p.id_comprobante
  WHERE pago_numero_matricula = 3
  AND id_alumno = ?
) AND malla_almn_num_matricula = 2
AND malla_almn_estado = 'R'
AND id_almn_carrera = ?

ORDER BY materia_ciclo;



-- Consultamos los comprobantes por alumno

SELECT
id_comprobante,
comprobante,
comprobante_total,
comprobante_codigo,
comprobante_fecha_pago,
comprobante_usuario_ingreso
FROM pago."Comprobante" c
AND id_alumno = ?;

-- Consultamos los pagos por alumno

SELECT
id_pago,
id_comprobante,
pago_observaciones,
pago,
pago_numero_matricula
FROM pago."Pago" p
JOIN public."MallaAlumno" ma
ON p.id_malla_alumno = ma.id_malla_alumno
JOIN public."Materias" m
ON m.id_materia = ma.id_materia
WHERE p.id_comprobante IN (
  SELECT id_comprobante
  FROM public."Comprobante"
  WHERE id_alumno = ?
);
