SELECT
r.id_carrera,
r.carrera_nombre,
M.materia_nombre,
M.materia_codigo,
C.curso_nombre,
CURRENT_DATE, CONCAT(
  d.docente_abreviatura,
  P.persona_primer_nombre, ' ',
  P.persona_primer_apellido
) AS nombre_doc, (
  SELECT CONCAT (
    dcc.docente_abreviatura,
    pcc.persona_primer_apellido,
    ' ',
    pcc.persona_primer_nombre
  ) AS "COOR"
FROM
  public."PeriodoLectivo" plc
  JOIN public."Docentes" dcc ON plc.prd_lectivo_coordinador = dcc.id_docente
  JOIN public."Personas" pcc ON dcc.id_persona = pcc.id_persona
WHERE
	plc.id_prd_lectivo = c.id_prd_lectivo
) AS COORDINADOR, (
  SELECT numero_unidad
  FROM "UnidadSilabo"
  WHERE "UnidadSilabo".id_unidad = $P!{id_unidad}
)  AS "UNIDAD_SILABO", (
  SELECT fecha_generacion
  FROM "PlandeClases"
  WHERE id_plan_clases =$P!{id_plan_clase}
) AS fecha_generacion,

CASE WHEN p.persona_identificacion = '0302298138' THEN
'COORDINADOR/A (E) DE LA CARRERA'
ELSE 'COORDINADOR/A DE LA CARRERA'
END AS coordinador_carrera

FROM
"Docentes" d
JOIN "Personas" P ON d.id_persona = P.id_persona
JOIN "Cursos" C ON C.id_docente = d.id_docente
JOIN "Materias" M ON C.id_materia = M.id_materia
JOIN "Carreras" r ON M.id_carrera = r.id_carrera
WHERE
C.id_curso = $P!{id_curso}
