-- Cerrado el periodo TAF
UPDATE public."PeriodoLectivo"
SET prd_lectivo_estado = false
WHERE id_prd_lectivo = 23;

-- Cerrar periodo SP
UPDATE public."PeriodoLectivo"
SET prd_lectivo_estado = false
WHERE id_prd_lectivo = 27;
