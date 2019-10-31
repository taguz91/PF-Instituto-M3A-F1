-- EstrategiasUnidad en el mismo query 

SELECT * ,(
	SELECT STRING_AGG( DISTINCT descripcion_estrategia, E'\n')
	FROM public."EstrategiasAprendizaje" j,
	public."EstrategiasUnidad" e
	WHERE j.id_estrategia = e.id_estrategia
	AND id_unidad = us.id_unidad
) AS estrategiasdes
FROM public."UnidadSilabo" us
WHERE
id_silabo = 4195
ORDER BY numero_unidad ASC;
