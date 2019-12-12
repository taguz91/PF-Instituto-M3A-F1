SELECT
p.id_persona,
p.persona_identificacion,
p.persona_primer_nombre || ' ' ||
p.persona_segundo_nombre AS nombres,
p.persona_primer_apellido || ' ' ||
p.persona_segundo_apellido AS apellidos,
p.persona_correo,
p.persona_celular, (
  SELECT carrera_nombre
  FROM public."AlumnosCarrera" ac
  JOIN public."Carreras" c USING(id_carrera)
  WHERE ac.id_alumno = a.id_alumno
  ORDER BY id_carrera DESC
  LIMIT 1
) AS carrera, (
  SELECT curso_nombre
  FROM public."AlumnoCurso" ac
  JOIN public."Cursos" c USING(id_curso)
  WHERE ac.id_alumno = a.id_alumno
  ORDER BY curso_nombre DESC
  LIMIT 1
) AS curso
FROM public."Personas" p
JOIN public."Alumnos" a USING(id_persona)
WHERE a.alumno_activo = 'true'
AND p.persona_activa = 'true'


ORDER BY p.persona_primer_apellido,
p.persona_segundo_apellido;
