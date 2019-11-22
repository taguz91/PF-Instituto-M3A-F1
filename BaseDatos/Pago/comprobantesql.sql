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
)
