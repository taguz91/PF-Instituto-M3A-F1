--
-- SILABO
--
copy (
  SELECT
  s.id_silabo,
  s.id_materia,
  s.id_prd_lectivo
  FROM public."Silabo" s
  WHERE s.id_silabo IN (
    2408, 5018
  )
)
To '/tmp/migrandopgsq/silabo.csv' with CSV HEADER;

-- Referencias

copy (
  SELECT
  rs.id_referencia_silabo,
  rs.id_referencia,
  rs.id_silabo
  FROM public."ReferenciaSilabo" rs
  WHERE id_silabo IN (
    2408, 5018
  ) AND id_referencia_silabo NOT IN (
    13755, 13756, 13757, 13758, 13759
  )
)
To '/tmp/migrandopgsq/referencia.csv' with CSV HEADER;

-- Unidades

copy (
  SELECT
  id_unidad,
  id_silabo,
  numero_unidad,
  objetivo_especifico_unidad,
  resultados_aprendizaje_unidad,
  contenidos_unidad,
  fecha_inicio_unidad,
  fecha_fin_unidad,
  horas_docencia_unidad,
  horas_practica_unidad,
  horas_autonomo_unidad,
  titulo_unidad
  FROM public."UnidadSilabo" us
  WHERE id_silabo IN (
    2408, 5018
  )
)
To '/tmp/migrandopgsq/unidad.csv' with CSV HEADER;


Copy "UnidadSilabo"(
  id_unidad,
  id_silabo,
  numero_unidad,
  objetivo_especifico_unidad,
  resultados_aprendizaje_unidad,
  contenidos_unidad,
  fecha_inicio_unidad,
  fecha_fin_unidad,
  horas_docencia_unidad,
  horas_practica_unidad,
  horas_autonomo_unidad,
  titulo_unidad
)
From '/tmp/migrandopgsq/unidad.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

-- Estrategia Unidad

copy (
  SELECT
  id_estrategia_unidad,
  id_unidad,
  id_estrategia
  FROM public."EstrategiasUnidad" eu
  WHERE id_unidad IN (
    SELECT id_unidad
    FROM public."UnidadSilabo"
    WHERE id_silabo IN (2408, 5018)
  )
)
To '/tmp/migrandopgsq/estrategia.csv' with CSV HEADER;

Copy "EstrategiasUnidad"(
  id_estrategia_unidad,
  id_unidad,
  id_estrategia
)
From '/tmp/migrandopgsq/estrategia.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

-- Evaluacion

copy (
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
    WHERE id_silabo IN (2408, 5018)
  )
)
To '/tmp/migrandopgsq/evaluacion.csv' with CSV HEADER;


Copy "EvaluacionSilabo"(
  id_evaluacion,
  id_unidad,
  indicador,
  id_tipo_actividad,
  instrumento,
  valoracion,
  fecha_envio,
  fecha_presentacion
)
From '/tmp/migrandopgsq/evaluacion.csv'
delimiter AS ',' NULL AS '' CSV HEADER;


-- Planes de clase

copy (
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
  )
)
To '/tmp/migrandopgsq/planclase.csv' with CSV HEADER;


Copy "PlandeClases"(
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
)
From '/tmp/migrandopgsq/planclase.csv'
delimiter AS ',' NULL AS '' CSV HEADER;


-- Recursos de plan de clases


copy (
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
  )
)
To '/tmp/migrandopgsq/recursosplan.csv' with CSV HEADER;


Copy "RecursosPlanClases"(
  id_recursos_plan_clases,
  id_plan_clases,
  id_recurso
)
From '/tmp/migrandopgsq/recursosplan.csv'
delimiter AS ',' NULL AS '' CSV HEADER;


-- Estrategias

copy (
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
  )
)
To '/tmp/migrandopgsq/estrategiasplan.csv' with CSV HEADER;

Copy "EstrategiasMetodologias"(
  id_estrategias_metodologias,
  tipo_estrategias_metodologias,
  id_plan_de_clases,
  nombre_estrategia
)
From '/tmp/migrandopgsq/estrategiasplan.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

--
