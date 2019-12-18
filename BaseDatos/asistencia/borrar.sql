SELECT * FROM public."Asistencia"
WHERE id_almn_curso IN (
  SELECT
  id_almn_curso
  FROM public."Cursos" c
  JOIN public."PeriodoLectivo" pl
  ON pl.id_prd_lectivo = c.id_prd_lectivo
  JOIN public."Docentes" d
  ON d.id_docente = c.id_docente
  JOIN public."Personas" pd
  ON pd.id_persona = d.id_persona
  JOIN public."AlumnoCurso" ac
  ON ac.id_curso = c.id_curso
  WHERE pd.persona_identificacion = '0104553078'
);


DELETE FROM public."Asistencia" 
WHERE id_almn_curso IN (
  SELECT
  id_almn_curso
  FROM public."Cursos" c
  JOIN public."PeriodoLectivo" pl
  ON pl.id_prd_lectivo = c.id_prd_lectivo
  JOIN public."Docentes" d
  ON d.id_docente = c.id_docente
  JOIN public."Personas" pd
  ON pd.id_persona = d.id_persona
  JOIN public."AlumnoCurso" ac
  ON ac.id_curso = c.id_curso
  WHERE pd.persona_identificacion = '0104553078'
);
