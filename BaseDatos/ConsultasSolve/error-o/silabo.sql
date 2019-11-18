SELECT
s.id_silabo,
s.id_materia,
s.id_prd_lectivo,
m.materia_nombre,
pl.prd_lectivo_nombre

FROM public."Silabo" s
JOIN public."Materias" m ON s.id_materia = m.id_materia
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = s.id_prd_lectivo
WHERE m.id_carrera = 2

--

SELECT
s.id_silabo,
s.id_materia,
s.id_prd_lectivo,
m.materia_nombre,
pl.prd_lectivo_nombre

FROM public."Silabo" s
JOIN public."Materias" m ON s.id_materia = m.id_materia
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = s.id_prd_lectivo
WHERE m.id_materia = 50

-- ID OLD

-- Periodo 21 -> 2408
-- Periodo 31 -> 5018


-- Silabos

SELECT
s.id_silabo,
s.id_materia,
s.id_prd_lectivo
FROM public."Silabo" s
WHERE s.silabo IN (
  2408, 5018
);

-- Referencias del primer silabo
SELECT
rs.id_referencia_silabo,
rs.id_referencia,
rs.id_silabo,
codigo_referencia,
descripcion_referencia,
tipo_referencia
FROM public."ReferenciaSilabo" rs
JOIN public."Referencias" r  ON r.id_referencia = rs.id_referencia
WHERE id_silabo = 2408;

-- Referencias del segundo silabo
SELECT
rs.id_referencia_silabo,
rs.id_referencia,
rs.id_silabo,
codigo_referencia,
descripcion_referencia,
tipo_referencia
FROM public."ReferenciaSilabo" rs
JOIN public."Referencias" r  ON r.id_referencia = rs.id_referencia
WHERE id_silabo = 5018
AND id_referencia_silabo NOT IN (
  13755, 13756, 13757, 13758, 13759
);


-- Unidades

-- Nuevo 21

SELECT
us.id_unidad,
us.id_silabo,
us.numero_unidad,
us.objetivo_especifico_unidad,
us.resultados_aprendizaje_unidad,
us.contenidos_unidad,
us.fecha_inicio_unidad,
us.fecha_fin_unidad,
us.horas_docencia_unidad,
us.horas_practica_unidad,
us.horas_autonomo_unidad,
us.titulo_unidad
FROM public."UnidadSilabo" us
WHERE us.id_silabo = 2408;

-- Nuevo 31
SELECT
us.id_unidad,
us.id_silabo,
us.numero_unidad,
us.objetivo_especifico_unidad,
us.resultados_aprendizaje_unidad,
us.contenidos_unidad,
us.fecha_inicio_unidad,
us.fecha_fin_unidad,
us.horas_docencia_unidad,
us.horas_practica_unidad,
us.horas_autonomo_unidad,
us.titulo_unidad
FROM public."UnidadSilabo" us
WHERE us.id_silabo = 5018;

-- Viejo

SELECT
eu.id_estrategia_unidad,
eu.id_unidad,
eu.id_estrategia
FROM public."EstrategiasUnidad" eu
WHERE id_unidad IN (
  SELECT id_unidad
  FROM public."UnidadSilabo"
  WHERE id_silabo = 2408
);

-- Nuevo

SELECT
eu.id_estrategia_unidad,
eu.id_unidad,
eu.id_estrategia
FROM public."EstrategiasUnidad" eu
WHERE id_unidad IN (
  SELECT id_unidad
  FROM public."UnidadSilabo"
  WHERE id_silabo = 5018
);


-- Viejo
SELECT
id_evaluacion,
id_unidad,
indicador,
id_tipo_actividad,
instrumento,
valoracion,
fecha_envio,
fecha_presentacion
FROM public."EvaluacionSilabo" es
WHERE id_unidad IN (
  SELECT id_unidad
  FROM public."UnidadSilabo"
  WHERE id_silabo = 2408
);

--  Nuevo

SELECT
id_evaluacion,
id_unidad,
indicador,
id_tipo_actividad,
instrumento,
valoracion,
fecha_envio,
fecha_presentacion
FROM public."EvaluacionSilabo" es
WHERE id_unidad IN (
  SELECT id_unidad
  FROM public."UnidadSilabo"
  WHERE id_silabo = 5018
);

--
-- Planes de clase
--

SELECT
id_plan_clases,
id_curso,
id_unidad,
observaciones,
documento_plan_clases,
fecha_revision,
fecha_generacion,
fecha_cierre,
trabajo_autonomo,
estado_plan
FROM public."PlandeClases"
WHERE id_unidad IN (
  SELECT id_unidad
  FROM public."UnidadSilabo"
  WHERE id_silabo = 2408
);

-- Todos los recursos
SELECT
id_recursos_plan_clases,
id_plan_clases,
id_recurso
FROM public."RecursosPlanClases"
WHERE id_plan_clases IN (
  SELECT
  id_plan_clases
  FROM public."PlandeClases"
  WHERE id_unidad IN (
    SELECT id_unidad
    FROM public."UnidadSilabo"
    WHERE id_silabo = 2408
  )
);

-- Todas las estrategias

SELECT
id_estrategias_metodologias,
tipo_estrategias_metodologias,
id_plan_de_clases,
nombre_estrategia
FROM public."EstrategiasMetodologias"
WHERE id_plan_de_clases IN (
  SELECT
  id_plan_clases
  FROM public."PlandeClases"
  WHERE id_unidad IN (
    SELECT id_unidad
    FROM public."UnidadSilabo"
    WHERE id_silabo = 2408
  )
);

--
-- AVANCE DE SILABO
--

SELECT
id_seguimientosilabo,
fecha_entrga_informe,
es_interciclo,
id_curso,
estado_seguimiento
FROM public."SeguimientoSilabo"
WHERE id_curso IN (
  SELECT id_curso
  FROM public."Cursos"
  WHERE id_prd_lectivo = (
    SELECT id_prd_lectivo
    FROM public."Silabo"
    WHERE id_silabo = 2408
  ) AND id_materia = (
    SELECT id_materia
    FROM public."Silabo"
    WHERE id_silabo = 2408
  )
);


SELECT
id_unidadseguimiento,
id_unidad,
id_seguimientosilabo,
cumplimiento_porcentaje,
observaciones
FROM public."Unidad_Seguimiento"
WHERE id_seguimientosilabo IN (
  SELECT
  id_seguimientosilabo
  FROM public."SeguimientoSilabo"
  WHERE id_curso IN (
    SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = (
      SELECT id_prd_lectivo
      FROM public."Silabo"
      WHERE id_silabo = 2408
    ) AND id_materia = (
      SELECT id_materia
      FROM public."Silabo"
      WHERE id_silabo = 2408
    )
  )
);


--- SILABO OLD
AVANCE

SELECT
id_unidadseguimiento,
id_unidad,
id_seguimientosilabo,
cumplimiento_porcentaje,
observaciones
FROM public."Unidad_Seguimiento"
WHERE id_seguimientosilabo IN (
  SELECT
  id_seguimientosilabo
  FROM public."SeguimientoSilabo"
  WHERE id_curso IN (
    808, 821, 720
  )
);


SELECT
id_unidadseguimiento,
us.id_unidad,
id_seguimientosilabo,
cumplimiento_porcentaje,
observaciones,
numero_unidad
FROM public."Unidad_Seguimiento" us
JOIN public."UnidadSilabo" u ON u.id_unidad = us.id_unidad
WHERE id_seguimientosilabo IN (
  SELECT
  id_seguimientosilabo
  FROM public."SeguimientoSilabo"
  WHERE id_curso IN (
    808, 821, 720
  )
)
ORDER BY numero_unidad;

-- Nuevo silabo

SELECT
us.id_unidad,
us.id_silabo,
us.numero_unidad,
us.objetivo_especifico_unidad,
us.resultados_aprendizaje_unidad,
us.contenidos_unidad,
us.fecha_inicio_unidad,
us.fecha_fin_unidad,
us.horas_docencia_unidad,
us.horas_practica_unidad,
us.horas_autonomo_unidad,
us.titulo_unidad
FROM public."UnidadSilabo" us
WHERE us.id_silabo = 2408;
