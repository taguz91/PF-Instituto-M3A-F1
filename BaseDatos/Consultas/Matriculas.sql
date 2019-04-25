
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

SELECT pa.persona_primer_nombre || '' || pa.persona_primer_apellido AS alumnos,
almn_curso_nota_final, curso_nombre,
pd.persona_primer_nombre || '' || pd.persona_primer_apellido AS docentes
FROM public."Alumnos" a, public."Personas" pa, public."Docentes" d,
public."Personas" pd, public."Cursos" c, public."AlumnosCurso" ac
WHERE ac.id_curso = c.id_curso AND
c.id_prd_lectivo IN (4, 8) AND
a.id_alum = ac.id_alumno AND
pa.id_persona = a.id_persona AND
d.id_docente = c.id_docente AND
pd.id_persona = d.id_persona;
