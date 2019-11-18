/*
	ESTA FUNCION BUSCA SACA LOS PROMEDIO DE LA FASE TEORICA
	(SACA EL PROMEDIO DE LAS MATERIAS QUE NO SEAN IGUALES AL VARCHAR("materias_filtrar"))
*/
CREATE OR REPLACE FUNCTION "CALCULAR_PROMEDIO_NOT_SIMILAR_TO"("id_almn_carr" int4, "materias_filtrar" varchar)
  RETURNS "pg_catalog"."numeric" AS $BODY$

  DECLARE
	malla_cursor CURSOR FOR SELECT
		"MallaAlumno".malla_almn_nota1,
		"MallaAlumno".malla_almn_nota2,
		"MallaAlumno".malla_almn_nota3
	FROM
		"MallaAlumno"
		INNER JOIN "Materias" ON "MallaAlumno".id_materia = "Materias".id_materia
	WHERE
		"MallaAlumno".id_almn_carrera = id_almn_carr
		AND "Materias".materia_nombre NOT SIMILAR TO materias_filtrar;


	registro RECORD;
	promedio_total NUMERIC := 0;
	total_materias INTEGER := 0;

BEGIN-- Routine body goes here...

	OPEN malla_cursor;

	FETCH malla_cursor INTO registro;

	WHILE ( FOUND ) LOOP


		IF registro.malla_almn_nota3 <> 0 THEN
				promedio_total = promedio_total + registro.malla_almn_nota3;
		ELSE

			IF registro.malla_almn_nota2 <> 0 THEN
				promedio_total = promedio_total + registro.malla_almn_nota2;
			ELSE
				promedio_total = promedio_total + registro.malla_almn_nota1;
			END IF;
		END IF;


		--RAISE NOTICE 'Materia: %',registro.materia_nombre;
		total_materias = total_materias + 1;


		FETCH malla_cursor INTO registro;


	END LOOP;

	promedio_total = ROUND((promedio_total/total_materias),2);
	--RAISE NOTICE 'TOTAL: %',promedio_total;

	CLOSE malla_cursor;

RETURN promedio_total;
END $BODY$ LANGUAGE plpgsql VOLATILE COST 100;



/*
	ESTA FUNCION BUSCA SACA LOS PROMEDIO DE PTI Y FASE PRACTICA
	(SACA EL PROMEDIO DE LAS MATERIAS QUE SEAN IGUALES AL VARCHAR("materias_filtrar"))
*/
CREATE OR REPLACE FUNCTION "CALCULAR_PROMEDIO_SIMILAR_TO"("id_almn_carr" int4, "materias_filtrar" varchar)
  RETURNS "pg_catalog"."numeric" AS $BODY$

  DECLARE
	malla_cursor CURSOR FOR SELECT
		"MallaAlumno".malla_almn_nota1,
		"MallaAlumno".malla_almn_nota2,
		"MallaAlumno".malla_almn_nota3
	FROM
		"MallaAlumno"
		INNER JOIN "Materias" ON "MallaAlumno".id_materia = "Materias".id_materia
	WHERE
		"MallaAlumno".id_almn_carrera = id_almn_carr
		AND "Materias".materia_nombre SIMILAR TO materias_filtrar;


	registro RECORD;
	promedio_total NUMERIC := 0;
	total_materias INTEGER := 0;

BEGIN-- Routine body goes here...

	OPEN malla_cursor;

	FETCH malla_cursor INTO registro;

	WHILE ( FOUND ) LOOP


		IF registro.malla_almn_nota3 <> 0 THEN
				promedio_total = promedio_total + registro.malla_almn_nota3;
		ELSE

			IF registro.malla_almn_nota2 <> 0 THEN
				promedio_total = promedio_total + registro.malla_almn_nota2;
			ELSE
				promedio_total = promedio_total + registro.malla_almn_nota1;
			END IF;
		END IF;


		--RAISE NOTICE 'Materia: %',registro.materia_nombre;
		total_materias = total_materias + 1;


		FETCH malla_cursor INTO registro;


	END LOOP;

	promedio_total = ROUND((promedio_total/total_materias),2);
	--RAISE NOTICE 'TOTAL: %',promedio_total;

	CLOSE malla_cursor;

RETURN promedio_total;
END $BODY$ LANGUAGE plpgsql VOLATILE COST 100;

/*
	ESTA CONSULTA EJECUTA TODO EL PROCESO
*/
SELECT DISTINCT
	"CALCULAR_PROMEDIO_NOT_SIMILAR_TO" ( "MallaAlumno".id_almn_carrera, '%(PTI|FASE PRÁ|INGLÉS)%' ) AS fase_teorica,
	"CALCULAR_PROMEDIO_SIMILAR_TO" ( "MallaAlumno".id_almn_carrera, '%(PTI)%' ) AS pti,
	"CALCULAR_PROMEDIO_SIMILAR_TO" ( "MallaAlumno".id_almn_carrera, '%(FASE PR)%' ) AS fase_practica,
	"Personas".persona_identificacion,
	"Personas".persona_primer_apellido,
	"Personas".persona_segundo_apellido,
	"Personas".persona_primer_nombre,
	"Personas".persona_segundo_nombre 
FROM
	"MallaAlumno"
	INNER JOIN "AlumnosCarrera" ON "MallaAlumno".id_almn_carrera = "AlumnosCarrera".id_almn_carrera
	INNER JOIN "Alumnos" ON "AlumnosCarrera".id_alumno = "Alumnos".id_alumno
	INNER JOIN "Personas" ON "Alumnos".id_persona = "Personas".id_persona
WHERE
	"AlumnosCarrera".id_carrera = 3
	AND "MallaAlumno".malla_almn_ciclo = 5
	AND "MallaAlumno".malla_almn_estado = 'C'
