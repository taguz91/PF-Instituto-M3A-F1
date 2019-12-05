WITH mi_plan AS (
	SELECT
		"Cursos".id_prd_lectivo,
		"Cursos".id_docente,
		"Cursos".id_materia 
	FROM
		"PlandeClases"
		INNER JOIN "Cursos" ON "Cursos".id_curso = "PlandeClases".id_curso 
	WHERE
		"PlandeClases".id_plan_clases = 1721 
	),
	mis_cursos AS (
	SELECT
		"Cursos".id_prd_lectivo,
		"Cursos".id_docente,
		"Cursos".id_materia,
		"Cursos".curso_nombre,
		"Cursos".id_curso 
	FROM
		"Cursos"
		INNER JOIN mi_plan ON mi_plan.id_prd_lectivo = "Cursos".id_prd_lectivo 
		AND mi_plan.id_materia = "Cursos".id_materia 
		AND mi_plan.id_docente = "Cursos".id_docente 
	),
	mis_planes AS ( 
    SELECT 
        mis_cursos.* 
    FROM 
        "PlandeClases" 
        INNER JOIN mis_cursos ON mis_cursos.id_curso = "PlandeClases".id_curso 
    ) 
SELECT
    mis_cursos.id_curso,
    mis_cursos.curso_nombre
FROM
	mis_cursos 
WHERE
	mis_cursos.id_curso NOT IN ( SELECT id_curso FROM mis_planes )









1721 plan para copiar
1198 curso a copiar


CREATE OR REPLACE PROCEDURE "copiar_plan_de_clases"(id_plan_param INTEGER, id_curso_a_copiar INTEGER)
 AS $BODY$
 DECLARE
 id_plan_nuevo INTEGER := 0;
 
 BEGIN
	-- Routine body goes here...
	
	INSERT INTO "PlandeClases" ( id_unidad, observaciones, trabajo_autonomo, id_curso ) 
	SELECT
		id_unidad,
		observaciones,
		trabajo_autonomo,
		id_curso_a_copiar AS id_curso 
	FROM
		"PlandeClases" 
	WHERE
		id_plan_clases = id_plan_param RETURNING id_plan_clases INTO id_plan_nuevo;
		
		
		
	-- INSERT DE LAS ESTRATEGIAS
	
	
	INSERT INTO "EstrategiasAprendizaje" ( tipo_estrategias_metodologias, id_plan_de_clases, nombre_estrategia )
	SELECT
		"EstrategiasMetodologias".tipo_estrategias_metodologias,
		id_plan_nuevo AS id_plan_de_clases,
		"EstrategiasMetodologias".nombre_estrategia 
	FROM
		"EstrategiasMetodologias" 
	WHERE
		id_plan_de_clases = id_plan_param;
		
	
	--INSER DE LOS RECURSOS
	
	INSERT INTO "RecursosPlanClases" ( id_plan_clases, id_recurso ) 
	SELECT
		id_plan_nuevo AS id_plan_clases,
		"RecursosPlanClases".id_recurso 
	FROM
		"RecursosPlanClases" 
	WHERE
		id_plan_clases = id_plan_param;
	
	
	
	
END$BODY$
	LANGUAGE plpgsql