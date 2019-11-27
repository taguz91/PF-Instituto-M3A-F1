DELETE FROM public."PersonaFicha"
WHERE id_persona_ficha IN (369, 375);


SELECT id_persona_ficha,
persona_ficha_fecha_ingreso,
persona_ficha_fecha_modificacion,
persona_primer_apellido || ' ' ||
persona_primer_nombre,
prd_lectivo_nombre
FROM public."PersonaFicha" pf
JOIN public."Personas" p
ON p.id_persona = pf.id_persona
JOIN public."PermisoIngresoFichas" pif
ON pif.id_permiso_ingreso_ficha = pf.id_permiso_ingreso_ficha
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = pif.id_prd_lectivo
WHERE persona_ficha_activa = false;

-- Eliminamos a los que no se matricularon este ciclo de las fichas

DELETE FROM public."PersonaFicha"
WHERE id_persona_ficha IN (
  SELECT id_persona_ficha
  FROM public."PersonaFicha" pf
  WHERE pf.id_persona IN (
    SELECT id_persona
    FROM public."Alumnos"
    WHERE id_alumno NOT IN (
      SELECT id_alumno
      FROM public."AlumnoCurso"
      WHERE id_curso IN (
        SELECT id_curso
        FROM public."Cursos"
        WHERE id_prd_lectivo >= 30
      )
    )
  )
);

--- ALumnos a los que envie la ficha y no se matricularon este ciclo

SELECT id_persona_ficha,
persona_primer_apellido || ' ' ||
persona_primer_nombre,
prd_lectivo_nombre
FROM public."PersonaFicha" pf
JOIN public."Personas" p
ON p.id_persona = pf.id_persona
JOIN public."PermisoIngresoFichas" pif
ON pif.id_permiso_ingreso_ficha = pf.id_permiso_ingreso_ficha
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = pif.id_prd_lectivo
WHERE pf.id_persona IN (
  SELECT id_persona
  FROM public."Alumnos"
  WHERE id_alumno NOT IN (
    SELECT id_alumno
    FROM public."AlumnoCurso"
    WHERE id_curso IN (
      SELECT id_curso
      FROM public."Cursos"
      WHERE id_prd_lectivo >= 30
    )
  )
);

-- Reporte de las personas que no llenan la ficha --- 

SELECT
persona_identificacion AS "IDENTIFICACIÓN",
persona_primer_apellido || ' ' ||
persona_segundo_apellido || ' ' ||
persona_primer_nombre || ' ' ||
persona_segundo_nombre AS "ALUMNO",
prd_lectivo_nombre AS "PERIODO",
persona_ficha_fecha_ingreso AS "FECHA INGRESO",
persona_ficha_fecha_modificacion AS "FECHA ULTIMA MODIFICACION"

FROM public."PersonaFicha" pf
JOIN public."Personas" p
ON p.id_persona = pf.id_persona
JOIN public."PermisoIngresoFichas" pif
ON pif.id_permiso_ingreso_ficha = pf.id_permiso_ingreso_ficha
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = pif.id_prd_lectivo
WHERE persona_ficha_finalizada = false
AND pl.id_prd_lectivo >= 30

UNION

SELECT
persona_identificacion AS "IDENTIFICACIÓN",
persona_primer_apellido || ' ' ||
persona_segundo_apellido || ' ' ||
persona_primer_nombre || ' ' ||
persona_segundo_nombre AS "ALUMNO",
prd_lectivo_nombre AS "PERIODO",
NULL AS "FECHA INGRESO",
NULL AS "FECHA ULTIMA MODIFICACION"


FROM public."Matricula" m
JOIN public."Alumnos" a
ON a.id_alumno = m.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = m.id_prd_lectivo
WHERE m.id_prd_lectivo >= 30
AND p.id_persona NOT IN (
  SELECT id_persona
  FROM public."PersonaFicha" pf
  JOIN public."PermisoIngresoFichas" pi
  ON pf.id_permiso_ingreso_ficha = pi.id_permiso_ingreso_ficha
  WHERE pi.id_prd_lectivo >= 30
)

ORDER BY "PERIODO";
