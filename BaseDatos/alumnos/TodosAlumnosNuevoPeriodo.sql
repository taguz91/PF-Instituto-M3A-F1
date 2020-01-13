SELECT
pl.prd_lectivo_nombre,
p.persona_identificacion,
p.persona_primer_nombre || ' ' ||
p.persona_segundo_nombre AS nombres,
p.persona_primer_apellido || ' ' ||
p.persona_segundo_apellido AS apellidos,
p.persona_correo,
p.persona_celular,
p.persona_sexo, (
  SELECT curso_nombre
  FROM public."AlumnoCurso" ac
  JOIN public."Cursos" c USING(id_curso)
  WHERE ac.id_alumno = a.id_alumno
  AND c.id_prd_lectivo = m.id_prd_lectivo
  ORDER BY curso_nombre
  LIMIT 1
) AS curso,

a.alumno_anio_graduacion AS anio_graduacion_colegio,
a.alumno_tipo_colegio AS tipo_colegio,
a.alumno_tipo_bachillerato AS tipo_bachillerato,
age(p.persona_fecha_nacimiento ) AS edad,
EXTRACT(YEAR FROM age(p.persona_fecha_nacimiento )) AS "year",
EXTRACT(MONTH FROM age(p.persona_fecha_nacimiento )) AS "meses",
EXTRACT(DAYS FROM age(p.persona_fecha_nacimiento )) AS "dias",

consultar_provincia(p.id_lugar_natal) AS "Provincia Nacimiento",
consultar_provincia(p.id_lugar_residencia) AS "Provincia Residencia"

FROM public."Matricula" m
JOIN public."Alumnos" a USING(id_alumno)
JOIN public."Personas" p USING(id_persona)
JOIN public."PeriodoLectivo" pl USING(id_prd_lectivo)
WHERE a.alumno_activo = 'true'
AND p.persona_activa = 'true'
AND m.id_prd_lectivo IN (
  31, 32,
  33, 34,
  35, 36,
  37
)
ORDER BY prd_lectivo_nombre, curso, apellidos
