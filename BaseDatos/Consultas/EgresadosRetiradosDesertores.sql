SELECT
carrera_codigo,
carrera_nombre, (
  SELECT COUNT(id_matricula)
  FROM public."Matricula"
  WHERE id_prd_lectivo >= m.id_prd_lectivo
  AND matricula_tipo = 'ORDINARIA'
) AS "Ordinarias", (
  SELECT COUNT(id_matricula)
  FROM public."Matricula"
  WHERE id_prd_lectivo >= m.id_prd_lectivo
  AND matricula_tipo = 'EXTRAORDINARIA'
) AS "Extraordinarias", (
  SELECT COUNT(id_matricula)
  FROM public."Matricula"
  WHERE id_prd_lectivo >= m.id_prd_lectivo
  AND matricula_tipo = 'ESPECIAL'
) AS "Especiales"
FROM public."Matricula" m
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = m.id_prd_lectivo
JOIN public."Carreras" c
ON c.id_carrera = pl.id_carrera
WHERE m.id_prd_lectivo >= 30
GROUP BY
carrera_codigo,
carrera_nombre,
m.id_prd_lectivo
ORDER BY carrera_nombre;

-- Consultamos los egresados
SELECT
'' AS "CÓDIGO DEL IST",
'ISTA' AS "NOMBRE DEL INSTITUTO",
'AZUAY' AS "PROVINCIA",
carrera_codigo AS "CÓDIGO DE LA CARRERA",
carrera_nombre AS "CARRERA",
carrera_modalidad AS "MODALIDAD DE ESTUDIOS",
'' AS "TIPO DE IDENTIFICACIÓN",
p.persona_identificacion AS "NRO. DE IDENTIFICACIÓN",
p.persona_primer_apellido || ' ' ||
p.persona_segundo_apellido || ' ' ||
p.persona_primer_nombre || ' ' ||
p.persona_segundo_nombre AS "APELLIDOS Y NOMBRES",
consultar_pais(p.id_lugar_natal) AS "NACIONALIDAD",
'' AS "TRABAJO DE TITULACIÓN FINALIZADO S/N"

FROM alumno."Egresados" e
JOIN public."AlumnosCarrera" ac
ON ac.id_almn_carrera = e.id_almn_carrera
JOIN public."Carreras" c
ON ac.id_carrera = c.id_carrera
JOIN public."Alumnos" a
ON a.id_alumno = ac.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE id_prd_lectivo BETWEEN 20 AND 30;

-- Retirados

SELECT
'' AS "CÓDIGO DEL IST",
'ISTA' AS "NOMBRE DEL INSTITUTO",
'AZUAY' AS "PROVINCIA",
carrera_codigo AS "CÓDIGO DE LA CARRERA",
carrera_nombre AS "CARRERA",
carrera_modalidad AS "MODALIDAD DE ESTUDIOS",
'' AS "TIPO DE IDENTIFICACIÓN",
p.persona_identificacion AS "NRO. DE IDENTIFICACIÓN",
p.persona_primer_apellido || ' ' ||
p.persona_segundo_apellido || ' ' ||
p.persona_primer_nombre || ' ' ||
p.persona_segundo_nombre AS "APELLIDOS Y NOMBRES",
consultar_pais(p.id_lugar_natal) AS "NACIONALIDAD",
r.fecha_retiro AS "FECHA DEL DOCUMENTO HABILITANTE PRESENTADO"

FROM alumno."Retirados" r
JOIN public."AlumnosCarrera" ac
ON ac.id_almn_carrera = r.id_almn_carrera
JOIN public."Carreras" c
ON ac.id_carrera = c.id_carrera
JOIN public."Alumnos" a
ON a.id_alumno = ac.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE id_prd_lectivo BETWEEN 20 AND 30;


-- Desertores


SELECT
'' AS "CÓDIGO DEL IST",
'ISTA' AS "NOMBRE DEL INSTITUTO",
'AZUAY' AS "PROVINCIA",
carrera_codigo AS "CÓDIGO DE LA CARRERA",
carrera_nombre AS "CARRERA",
carrera_modalidad AS "MODALIDAD DE ESTUDIOS",
'' AS "TIPO DE IDENTIFICACIÓN",
p.persona_identificacion AS "NRO. DE IDENTIFICACIÓN",
p.persona_primer_apellido || ' ' ||
p.persona_segundo_apellido || ' ' ||
p.persona_primer_nombre || ' ' ||
p.persona_segundo_nombre AS "APELLIDOS Y NOMBRES",
consultar_pais(p.id_lugar_natal) AS "NACIONALIDAD", (
  SELECT MAX(fecha_asistencia)
  FROM public."Asistencia"
  WHERE id_almn_curso IN (
    SELECT id_almn_curso
    FROM public."AlumnoCurso" ac
    WHERE ac.id_alumno = m.id_alumno
    AND id_curso IN (
      SELECT id_curso FROM public."Cursos"
      WHERE id_prd_lectivo = m.id_prd_lectivo
    )
  ) AND numero_faltas = 0
) AS "FECHA DE LA ULTIMA ASISTENCIA A CLASES"

FROM public."Matricula" m
JOIN public."Alumnos" a
ON a.id_alumno = m.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = m.id_prd_lectivo
JOIN public."Carreras" c
ON c.id_carrera = pl.id_carrera
WHERE m.id_prd_lectivo BETWEEN 20 AND 30
AND m.id_alumno IN (
  SELECT id_alumno
  FROM public."AlumnoCurso"
  WHERE almn_curso_asistencia ILIKE '%Desertor%'
  OR almn_curso_asistencia ILIKE '%Retirado%'
)

ORDER BY carrera_nombre,
carrera_codigo;



-- Todos los estudiantes
SELECT
'' AS "CÓDIGO DEL IST",
'ISTA' AS "NOMBRE DEL INSTITUTO",
'AZUAY' AS "PROVINCIA",
carrera_codigo AS "CÓDIGO DE LA CARRERA",
carrera_nombre AS "CARRERA",
carrera_modalidad AS "MODALIDAD DE ESTUDIOS",
'' AS "TIPO DE IDENTIFICACIÓN",
p.persona_identificacion AS "NRO. DE IDENTIFICACIÓN",
p.persona_primer_apellido || ' ' ||
p.persona_segundo_apellido || ' ' ||
p.persona_primer_nombre || ' ' ||
p.persona_segundo_nombre AS "APELLIDOS Y NOMBRES",
consultar_pais(p.id_lugar_natal) AS "NACIONALIDAD", (
  SELECT MAX(fecha_asistencia)
  FROM public."Asistencia"
  WHERE id_almn_curso IN (
    SELECT id_almn_curso
    FROM public."AlumnoCurso" ac
    WHERE ac.id_alumno = m.id_alumno
    AND id_curso IN (
      SELECT id_curso FROM public."Cursos"
      WHERE id_prd_lectivo = m.id_prd_lectivo
    )
  )
) AS "FECHA DE LA ULTIMA ASISTENCIA A CLASES"

FROM public."Matricula" m
JOIN public."Alumnos" a
ON a.id_alumno = m.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = m.id_prd_lectivo
JOIN public."Carreras" c
ON c.id_carrera = pl.id_carrera
WHERE m.id_prd_lectivo BETWEEN 20 AND 30

ORDER BY carrera_nombre,
carrera_codigo
