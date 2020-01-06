Copy (
  SELECT DISTINCT
  p.persona_identificacion,
  p.persona_primer_nombre || ' ' ||
  p.persona_segundo_nombre AS nombres,
  p.persona_primer_apellido || ' ' ||
  p.persona_segundo_apellido AS apellidos,
  p.persona_correo,
  p.persona_celular, (
    SELECT curso_nombre
    FROM public."AlumnoCurso" ac
    JOIN public."Cursos" c USING(id_curso)
    WHERE ac.id_alumno = a.id_alumno
    ORDER BY curso_nombre
    LIMIT 1
  ) AS curso,

  -- Informacion alumnos
  a.alumno_anio_graduacion AS ani_graduacion,

  a.alumno_tipo_colegio AS tipo_colegio,
  a.alumno_tipo_bachillerato AS tipo_bachillerato,
  age(p.persona_fecha_nacimiento )AS edad
  FROM public."AlumnoCurso" ac
  JOIN public."Alumnos" a USING(id_alumno)
  JOIN public."Personas" p USING(id_persona)

  WHERE a.alumno_activo = 'true'
  AND p.persona_activa = 'true'
  AND ac.id_almn_curso IN (
    SELECT DISTINCT id_almn_curso
    FROM public."AlumnoCurso" acr
    JOIN public."Cursos" cr USING(id_curso)
    WHERE id_prd_lectivo = 31
    AND cr.curso_ciclo = 1
  )
  ORDER BY curso, apellidos
)
To '/tmp/alumnos.csv' with CSV HEADER;
