DELETE FROM public."AlumnoCurso"
WHERE id_almn_curso IN (
  SELECT id_almn_curso
  FROM public."AlumnoCursoRetirados"
  WHERE retiro_fecha > to_timestamp('01-11-2019 15:36:38', 'dd-mm-yyyy hh24:mi:ss')
);
