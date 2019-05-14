SELECT materia_nombre, 
persona_primer_nombre,
persona_primer_apellido, 
persona_identificacion,
carrera_nombre 
FROM public."Materias" m, public."Cursos" c, 
public."AlumnosCarrera" ac, public."Alumnos" a, 
public."Personas" p, public."Carreras" cr 
WHERE m.id_materia = c.id_materia 
AND c.id_materia IN (SELECT id_materia 
	FROM public."MallaAlumno"
	WHERE id_almn_carrera = 211
	AND malla_almn_ciclo = 3
	AND malla_almn_num_matricula = 1
	AND malla_almn_estado = 'R')
AND c.curso_nombre = 'M3A'
AND c.id_prd_lectivo = 22
AND ac.id_almn_carrera = 211
AND cr.id_carrera = ac.id_carrera 
AND a.id_alumno = ac.id_alumno 
AND p.id_persona = a.id_persona;