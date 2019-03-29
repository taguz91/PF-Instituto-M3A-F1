--Consultamos los periodos lectivos para un combo
SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre
FROM public."PeriodoLectivo"
WHERE prd_lectivo_activo = true
ORDER BY prd_lectivo_fecha_inicio;

--
