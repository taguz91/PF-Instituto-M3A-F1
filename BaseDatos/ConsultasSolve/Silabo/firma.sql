SELECT CONCAT(
  d.docente_abreviatura,
  ' ',
  p.persona_primer_apellido,
  ' ',
  p.persona_primer_nombre
) as nombreCoordinador
FROM public."Silabo" s
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = s.id_prd_lectivo
JOIN public."Docentes" d ON d.id_docente = pl.prd_lectivo_coordinador
JOIN public."Personas" p ON p.id_persona = d.id_persona
WHERE s.id_silabo =
ORDER BY prd_lectivo_fecha_fin


-- Coordinador encargado
SELECT CONCAT (
a.docente_abreviatura,
' ',
p.persona_primer_apellido,
' ',
p.persona_primer_nombre
) as nombreCoordinador ,

CASE WHEN p.persona_identificacion = '0302298138' THEN
'COORDINADOR/A (E) DE LA CARRERA'
ELSE 'COORDINADOR/A DE LA CARRERA'
END AS coordinador_carrera

FROM "Carreras" c , "Docentes" a, "Personas" p
WHERE c.id_docente_coordinador = a.id_docente
AND a.id_persona = p.id_persona
AND C.id_carrera = $P{ID_CARR};
