--COnsutlamos los reprobados de desarrollo de software
SELECT persona_identificacion, persona_primer_nombre, persona_primer_apellido,
materia_nombre, malla_almn_num_matricula
FROM public."AlumnoCurso" ac, public."Cursos" c,
public."Materias" m, public."MallaAlumno" ma,
public."AlumnosCarrera" ar
WHERE c.id_curso = ac.id_curo
AND c.id_prd_lectivo = 4
AND m.id_materia = c.id_materia
AND ma.id_materia = c.id_materia
AND ar.id_alumno = ac.id_alumno
AND ma.id_almn_carrera = ar.id_almn_carrera
AND ac.almn_curso_nota_final < 70;
