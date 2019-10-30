SELECT * FROM public."Unidad_Seguimiento"
WHERE id_unidad IN (
	SELECT id_unidad FROM public."UnidadSilabo"
);


SELECT * FROM public."Unidad_Seguimiento"
WHERE id_seguimientosilabo IN (
	SELECT id_seguimientosilabo FROM public."UnidadSilabo"
);



SELECT * FROM public."SeguimientoSilabo"
WHERE id_curso IN (
  SELECT id_curso FROM public."Silabo"
)


SELECT * FROM public."Unidad_Seguimiento"
WHERE id_unidad IN (
	SELECT id_unidad FROM public."UnidadSilabo"
	WHERE id_silabo IN (
		SELECT id_silabo FROM public."Silabo"
		ORDER BY id_silabo DESC LIMIT 25
	)
) AND id_seguimientosilabo IN (
	SELECT id_seguimientosilabo
	FROM public."SeguimientoSilabo"
	WHERE id_curso IN (
		SELECT id_curso
		FROM public."Cursos"
		WHERE id_materia IN (
			SELECT id_materia FROM public."Silabo"
			ORDER BY id_silabo DESC LIMIT 25
		) AND id_prd_lectivo IN (
			SELECT id_prd_lectivo FROM public."Silabo"
			ORDER BY id_silabo DESC LIMIT 25
		)
	)
);


--- Docentes que actualizaron el silabo ultimamente

SELECT c.id_curso,
persona_identificacion,
materia_nombre,
persona_primer_nombre,
persona_primer_apellido
FROM public."Cursos" c
JOIN public."Docentes" d ON d.id_docente = c.id_docente
JOIN public."Personas" p ON p.id_persona = d.id_persona
JOIN public."Materias" m ON m.id_materia = c.id_materia
WHERE c.id_materia IN (
	SELECT id_materia FROM public."Silabo"
	ORDER BY id_silabo DESC LIMIT 25
) AND id_prd_lectivo IN (
	SELECT id_prd_lectivo FROM public."Silabo"
	ORDER BY id_silabo DESC LIMIT 25
);


-- Consutamos las ultimas modificaciones de los silabos

SELECT * FROM public."HistorialUsuarios" WHERE historial_pk_tabla IN (
	SELECT id_silabo FROM public."Silabo"
	ORDER BY id_silabo DESC LIMIT 15
) AND historial_tipo_accion = 'INSERT'
AND historial_nombre_tabla = 'Silabo'
ORDER BY historial_fecha DESC;
