-- Agregamos una columna en la base de datos
-- para saber si tiene un pago pendiente el alumno

ALTER TABLE public."MallaAlumno" ADD COLUMN malla_almn_pago_pendiente BOOLEAN DEFAULT 'false';

-- Actualizamos todas las mallas que tienen pagos pendientes
UPDATE public."MallaAlumno"
SET malla_almn_pago_pendiente = true
WHERE malla_almn_num_matricula > 1;
