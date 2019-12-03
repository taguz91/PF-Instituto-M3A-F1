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
WHERE m.id_prd_lectivo = 21
AND m.id_alumno IN (

  SELECT id_alumno FROM (
    SELECT id_alumno,  COUNT(id_alumno) = (
      SELECT COUNT(*)
      FROM public."AlumnoCurso"
      WHERE id_alumno = acd.id_alumno
      AND id_curso IN (
        SELECT id_curso
        FROM public."Cursos"
        WHERE id_prd_lectivo = 21
      )
      AND almn_curso_activo = true
    ) AS "reprobo"
    FROM public."AlumnoCurso" acd
    WHERE (almn_curso_asistencia ILIKE '%Desertor%'
    OR almn_curso_asistencia ILIKE '%Retirado%' )
    AND almn_curso_activo = true
    GROUP BY id_alumno
  ) AS "SU" WHERE reprobo = true
) AND a.id_alumno NOT IN (
  SELECT id_alumno
  FROM public."Matricula"
  WHERE id_prd_lectivo = 31
)

ORDER BY carrera_nombre,
carrera_codigo;



-----

SELECT * FROM (
  SELECT id_alumno,  COUNT(id_alumno) = (
    SELECT COUNT(*)
    FROM public."AlumnoCurso"
    WHERE id_alumno = acd.id_alumno
    AND id_curso IN (
      SELECT id_curso
      FROM public."Cursos"
      WHERE id_prd_lectivo = 21
    )
  ) AS "reprobo"
  FROM public."AlumnoCurso" acd
  WHERE (almn_curso_asistencia ILIKE '%Desertor%'
  OR almn_curso_asistencia ILIKE '%Retirado%' )
  GROUP BY id_alumno
)  WHERE reprobo = true
