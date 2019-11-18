SELECT
STRING_AGG(anticipacion,'') AS anticipacion,
STRING_AGG(construccion,'') AS construccion,
STRING_AGG(consolidacion,'') AS consolidacion FROM (
  SELECT
  nombre_estrategia AS anticipacion,
  '' AS construccion,
  '' AS consolidacion
  FROM "EstrategiASMetodologiAS"
  WHERE tipo_estrategiAS_metodologiAS = 'Anticipacion'
  AND id_plan_de_clASes = $P!{id_plan_clase}
  UNION
  SELECT
  '' AS anticipacion,
  nombre_estrategia AS construccion,
  '' AS consolidacion
  FROM "EstrategiASMetodologiAS"
  WHERE tipo_estrategiAS_metodologiAS = 'Construccion'
  AND id_plan_de_clASes = $P!{id_plan_clase}
  UNION
  SELECT
  '' AS anticipacion,
  '' AS construccion,
  nombre_estrategia AS consolidacion
  FROM "EstrategiASMetodologiAS"
  WHERE tipo_estrategiAS_metodologiAS = 'Consolidacion'
  AND id_plan_de_clASes = $P!{id_plan_clase}
) x


(
  SELECT
  STRING_AGG(nombre_estrategia, E'\n')
  FROM "EstrategiasMetodologias"
  WHERE tipo_estrategiAS_metodologiAS = 'Anticipacion'
  AND id_plan_de_clASes = $P!{id_plan_clase}
) AS anticipacion_arr, (
  SELECT
  STRING_AGG(nombre_estrategia, E'\n')
  FROM "EstrategiasMetodologias"
  WHERE tipo_estrategiAS_metodologiAS = 'Construccion'
  AND id_plan_de_clASes = $P!{id_plan_clase}
) AS construccion_arr, (
  SELECT
  STRING_AGG(nombre_estrategia, E'\n')
  FROM "EstrategiasMetodologias"
  WHERE tipo_estrategiAS_metodologiAS = 'Consolidacion'
  AND id_plan_de_clASes = $P!{id_plan_clase}
) AS consolidacion_arr
