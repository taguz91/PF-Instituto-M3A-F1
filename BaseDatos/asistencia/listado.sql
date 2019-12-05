SELECT
id_asistencia,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
numero_faltas

FROM public."Asistencia" aa
JOIN public."AlumnoCurso" ac
ON ac.id_almn_curso = aa.id_almn_curso
JOIN public."Alumnos" a
ON ac.id_alumno = a.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE ac.id_curso = 1218
AND fecha_asistencia = '04/12/2019';

--- Iniciamos las faltas

INSERT INTO public."Asistencia"(
id_almn_curso,
fecha_asistencia,
numero_faltas )
SELECT id_almn_curso,
'04/12/2019',
0
FROM public."AlumnoCurso"
WHERE id_curso = 1218;

--- Actualizamos

UPDATE public."Asistencia"
SET numero_faltas = :numFaltas
WHERE id_asistencia = :idAsistencia;


-- Alumnos por docentes


SELECT
c.id_curso,
id_almn_curso,
pa.persona_primer_nombre,
pa.persona_segundo_nombre,
pa.persona_primer_apellido,
pa.persona_segundo_apellido

FROM public."Cursos" c
JOIN public."PeriodoLectivo" pl
ON pl.id_prd_lectivo = c.id_prd_lectivo
JOIN public."Docentes" d
ON d.id_docente = c.id_docente
JOIN public."Personas" pd
ON pd.id_persona = d.id_persona
JOIN public."AlumnoCurso" ac
ON ac.id_curso = c.id_curso
JOIN public."Alumnos" a
ON ac.id_alumno = a.id_alumno
JOIN public."Personas" pa
ON pa.id_persona = a.id_persona
WHERE pd.persona_identificacion = '0104553078'
ORDER BY c.id_curso;
