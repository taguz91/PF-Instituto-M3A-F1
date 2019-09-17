SELECT
persona_identificacion,
persona_primer_nombre,
curso_nombre,
materia_nombre
FROM public."Cursos" c
JOIN public."AlumnoCurso" ac ON
c.id_curso = ac.id_curso
JOIN public."Alumnos" a ON
a.id_alumno = ac.id_alumno
JOIN public."Personas" p ON
p.id_persona = a.id_persona
JOIN public."Materias" m ON
m.id_materia = c.id_materia

WHERE c.id_materia = 49 AND
c.id_prd_lectivo = 21 AND
c.curso_paralelo = 'A' AND
id_jornada = 1;
