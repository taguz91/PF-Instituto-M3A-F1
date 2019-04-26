
--Consultamos los matriculados en un un curso de un periodo
SELECT count(*) public."AlumnoCurso"
WHERE id_curso IN (
  SELECT id_curso
  FROM public."Cursos"
  WHERE id_prd_lectivo = 8
  curso_nombre = 'M3A'
)

--Consultamos las matriculas hechas en el periodo
SELECT count(*) public."AlumnoCurso"
WHERE id_curso IN(
  SELECT id_curso
  FROM public."Cursos"
  WHERE id_prd_lectivo = 8
)


--NOtas aun no ingresadas
SELECT * FROM public."AlumnoCurso"
WHERE id_curso IN(
  SELECT id_curso
  FROM public."Cursos"
  WHERE id_prd_lectivo = 4
)AND almn_curso_nota_final 0;



SELECT pa.persona_primer_nombre || ' ' || pa.persona_primer_apellido AS alumnos,
almn_curso_nota_final, curso_nombre,
pd.persona_primer_nombre || ' ' || pd.persona_primer_apellido AS docentes,
(SELECT prd_lectivo_nombre
	FROM public."PeriodoLectivo"
	WHERE id_prd_lectivo = c.id_prd_lectivo)
FROM public."Alumnos" a, public."Personas" pa, public."Docentes" d,
public."Personas" pd, public."Cursos" c, public."AlumnoCurso" ac
WHERE ac.id_curso = c.id_curso AND
c.id_prd_lectivo IN (4, 8) AND
a.id_alumno = ac.id_alumno AND
pa.id_persona = a.id_persona AND
d.id_docente = c.id_docente AND
pd.id_persona = d.id_persona AND
almn_curso_nota_final = 0 ORDER BY curso_nombre;


SELECT ac.id_curso, count(*)
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE c.id_prd_lectivo IN (4, 8) AND
ac.id_curso = c.id_curso
GROUP BY ac.id_curso;


SELECT ac.id_curso, count(*)
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE c.id_prd_lectivo IN (4, 8) AND
ac.id_curso = c.id_curso AND almn_curso_nota_final = 0
GROUP BY ac.id_curso HAVING count(*) > 6;

--Solo el id

SELECT pa.persona_primer_nombre || ' ' || pa.persona_primer_apellido AS alumnos,
almn_curso_nota_final, curso_nombre,
pd.persona_primer_nombre || ' ' || pd.persona_primer_apellido AS docentes,
(SELECT prd_lectivo_nombre
	FROM public."PeriodoLectivo"
	WHERE id_prd_lectivo = c.id_prd_lectivo)
FROM public."Alumnos" a, public."Personas" pa, public."Docentes" d,
public."Personas" pd, public."Cursos" c, public."AlumnoCurso" ac
WHERE ac.id_curso IN (
	SELECT ac.id_curso
	FROM public."AlumnoCurso" ac, public."Cursos" c
	WHERE c.id_prd_lectivo IN (4, 8) AND
	ac.id_curso = c.id_curso AND almn_curso_nota_final = 0
	GROUP BY ac.id_curso HAVING count(*) > 6) AND
c.id_prd_lectivo IN (4, 8) AND
a.id_alumno = ac.id_alumno AND
pa.id_persona = a.id_persona AND
d.id_docente = c.id_docente AND
pd.id_persona = d.id_persona
ORDER BY curso_nombre;

--La misma
SELECT pa.persona_primer_nombre || ' ' || pa.persona_primer_apellido AS alumnos,
almn_curso_nota_final, curso_nombre,
pd.persona_primer_nombre || ' ' || pd.persona_primer_apellido AS docentes,
(SELECT prd_lectivo_nombre
	FROM public."PeriodoLectivo"
	WHERE id_prd_lectivo = c.id_prd_lectivo)
FROM public."Alumnos" a, public."Personas" pa, public."Docentes" d,
public."Personas" pd, public."Cursos" c, public."AlumnoCurso" ac
WHERE ac.id_curso IN (
184,
173,
357,
181,
142,
312,
135,
147,
174,
140) AND
c.id_prd_lectivo IN (4, 8) AND
a.id_alumno = ac.id_alumno AND
pa.id_persona = a.id_persona AND
d.id_docente = c.id_docente AND
pd.id_persona = d.id_persona
ORDER BY curso_nombre;

SELECT count(*)
FROM public."AlumnoCurso"
WHERE almn_curso_nota_final = 0;

--Cantidad de alumnos que apruebas en estos periodos
SELECT count(*)
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE almn_curso_nota_final > 70 AND
ac.id_curso = c.id_curso AND
c.id_prd_lectivo IN(4, 8);

--Numerod e cursados en el record
SELECT count(*)
FROM public."MallaAlumno"
WHERE id_materia IN(
	SELECT id_materia
	FROM public."Materias"
	WHERE id_carrera = 2) AND
	malla_almn_estado = 'C';
