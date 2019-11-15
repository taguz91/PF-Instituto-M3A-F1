CREATE OR REPLACE FUNCTION "public"."CALCULAR_PROMEDIO" ( id_almn_carr INTEGER, materias_filtrar VARCHAR ) RETURNS NUMERIC ( 6, 2 ) AS $BODY$ DECLARE
	malla_cursor CURSOR FOR SELECT
	"MallaAlumno".malla_almn_nota1,
	"MallaAlumno".malla_almn_nota2,
	"MallaAlumno".malla_almn_nota3,
	"MallaAlumno".malla_almn_num_matricula,
	"Materias".materia_nombre 
FROM
	"MallaAlumno"
	INNER JOIN "public"."Materias" ON "public"."MallaAlumno".id_materia = "public"."Materias".id_materia 
WHERE
	"MallaAlumno".id_almn_carrera = id_almn_carr 
	AND "Materias".materia_nombre NOT SIMILAR TO materias_filtrar;
registro RECORD;

BEGIN-- Routine body goes here...
	
	OPEN malla_cursor;
	
	FETCH malla_cursor INTO registro;
	
	WHILE ( FOUND ) LOOP
		FETCH malla_cursor INTO registro;
	
		RAISE NOTICE'prueba: %', registro.materia_nombre;
	
	END LOOP;

	CLOSE malla_cursor;

RETURN 0;

END $BODY$ LANGUAGE plpgsql





'%(PTI|FASE PRÁ)%'