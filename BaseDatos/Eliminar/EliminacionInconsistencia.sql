--Eliminamos los docentes que tienen asigna una materia mas de una vez
--Docentes
--Docentes
SELECT id_docente, count(id_materia) FROM public."DocentesMateria"
GROUP BY id_docente, id_materia HAVING count(id_materia) > 1;
--Materias
SELECT id_materia, count(id_docente) FROM public."DocentesMateria"
GROUP BY id_materia, id_docente HAVING count(id_docente) > 1;

SELECT * FROM public."DocentesMateria" WHERE id_docente IN (10,
85,
59,
85,
125,
55,
125) AND id_materia IN(130,
130,
17,
243,
128,
128,
54)ORDER BY id_docente, id_materia;
--263, 623, 624, 157, 574, 571, 503, 504
DELETE FROM public."DocentesMateria"
WHERE id_docente_mat IN (263, 623, 624, 157, 574, 571, 503, 504);
