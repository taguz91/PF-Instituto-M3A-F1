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