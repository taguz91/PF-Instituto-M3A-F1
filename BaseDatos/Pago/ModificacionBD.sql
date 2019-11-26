-- Agregamos una columna en la base de datos
-- para saber si tiene un pago pendiente el alumno

ALTER TABLE public."MallaAlumno" ADD COLUMN malla_almn_pago_pendiente BOOLEAN DEFAULT 'false';
