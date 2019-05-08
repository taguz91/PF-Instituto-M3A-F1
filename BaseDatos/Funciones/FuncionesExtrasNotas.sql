CREATE or REPLACE FUNCTION ValidarHorasMateria(HORAS int)
RETURNS int as $$
BEGIN
		IF (HORAS <=0 OR HORAS is null) THEN RETURN 1;
				ELSE RETURN HORAS;
		END IF;

END;
$ValidarHorasMateria$ LANGUAGE plpgsql;