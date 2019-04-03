--Consultamos los periodos lectivos para un combo
SELECT id_prd_lectivo, id_carrera, prd_lectivo_nombre
FROM public."PeriodoLectivo"
WHERE prd_lectivo_activo = true
ORDER BY prd_lectivo_fecha_inicio;

--Cargamos los alumonos de un curso por periodo lectivo
SELECT DISTINCT c.curso_nombre,
persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido,
persona_segundo_apellido, persona_identificacion
FROM public."AlumnoCurso" ac, public."Alumnos" a, public."Personas" p,
public."Cursos" c
WHERE a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
c.id_prd_lectivo = 19  AND
ac.id_curso = c.id_curso;

--Cargamos los alumnos de un curso
SELECT DISTINCT c.curso_nombre,
persona_primer_nombre, persona_segundo_nombre, persona_primer_apellido,
persona_segundo_apellido, persona_identificacion
FROM public."AlumnoCurso" ac, public."Alumnos" a, public."Personas" p,
public."Cursos" c
WHERE a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
c.curso_nombre = 'M4A'  AND
ac.id_curso = c.id_curso;


--Filtra el numero de Alumnos matriculados segun la ID de la carrera mandada
SELECT COUNT(*) FROM (public."Carreras" c JOIN public."AlumnosCarrera" a
					  USING(id_carrera)) JOIN public."MallaAlumno" m USING(id_almn_carrera)
							WHERE c.id_carrera = 2 AND m.malla_almn_estado LIKE 'M';

--Consultamos todos los alumnos de una malla por carrera 
SELECT id_malla_alumno, id_materia, ma.id_almn_carrera,
malla_almn_ciclo, malla_almn_num_matricula,
malla_almn_nota1, malla_almn_nota2,
malla_almn_nota3, malla_almn_estado
FROM public."MallaAlumno" ma, public."AlumnosCarrera" ac
WHERE ac.id_carrera = 2
AND ma.id_almn_carrera = ac.id_almn_carrera
AND malla_almn_estado = 'M';
