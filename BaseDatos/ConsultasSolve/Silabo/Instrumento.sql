SELECT STRING_AGG(e.instrumento, E'\n')
FROM "EvaluacionSilabo" e, "UnidadSilabo" u
WHERE e.id_unidad = u.id_unidad
AND e.id_unidad= $P!{id_unidad}
