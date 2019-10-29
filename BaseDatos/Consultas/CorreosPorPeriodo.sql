SELECT
persona_correo
FROM public."Matriculas" m
JOIN public."Alumnos" a JOIN a.id_alumno = m.id_alumno
JOIN public."Personas" p JOIN p.id_persona = a.id_persona
WHERE m.id_prd_lectivo = 21;
