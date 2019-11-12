SELECT DISTINCT
STRING_AGG(
  descripcion_estrategia, E'\n'
)
FROM "EstrategiasAprendizaje" j, "EstrategiasUnidad" e
WHERE j.id_estrategia = e.id_estrategia
AND id_unidad = 15308
