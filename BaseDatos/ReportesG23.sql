--Consultamos los datos de un docente en un curso
SELECT  carrera_nombre, curso_nombre, materia_nombre, materia_ciclo,
persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' || persona_segundo_apellido AS Nombre
FROM public."Cursos" c, public."Docentes" d, public."Personas" p, public."Materias" m,
public."PeriodoLectivo" pl, public."Carreras" cr
WHERE d.id_docente = c.id_docente
AND p.id_persona = d.id_persona
AND m.id_materia = c.id_materia
AND pl.id_prd_lectivo = c.id_prd_lectivo
AND cr.id_carrera = pl.id_carrera;

--Consultamos todos los alumnos en un curso
SELECT persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' || persona_segundo_apellido AS Nombre
FROM public."AlumnoCurso" ac, public."Alumnos" a, public."Personas" p
WHERE ac.id_alumno = a.id_alumno
AND p.id_persona = a.id_persona
AND id_curso = 634; --Se le pasaria el curso como parametro
