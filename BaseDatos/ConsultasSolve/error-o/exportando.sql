Copy "Silabo"(
  id_silabo,
  id_materia,
  id_prd_lectivo
)
From '/tmp/migrandopgsq/silabo.csv'
delimiter AS ',' NULL AS '' CSV HEADER;


Copy "ReferenciaSilabo"(
  id_referencia_silabo,
  id_referencia,
  id_silabo
)
From '/tmp/migrandopgsq/referencia.csv'
delimiter AS ',' NULL AS '' CSV HEADER;


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


Copy "EstrategiasUnidad"(
  id_estrategia_unidad,
  id_unidad,
  id_estrategia
)
From '/tmp/migrandopgsq/estrategia.csv'
delimiter AS ',' NULL AS '' CSV HEADER;


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


Copy "RecursosPlanClases"(
  id_recursos_plan_clases,
  id_plan_clases,
  id_recurso
)
From '/tmp/migrandopgsq/recursosplan.csv'
delimiter AS ',' NULL AS '' CSV HEADER;


Copy "EstrategiasMetodologias"(
  id_estrategias_metodologias,
  tipo_estrategias_metodologias,
  id_plan_de_clases,
  nombre_estrategia
)
From '/tmp/migrandopgsq/estrategiasplan.csv'
delimiter AS ',' NULL AS '' CSV HEADER;
