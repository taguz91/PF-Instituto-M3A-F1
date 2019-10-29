SELECT
persona_correo AS correos
FROM public."Matricula" m
JOIN public."Alumnos" a ON a.id_alumno = m.id_alumno
JOIN public."Personas" p ON p.id_persona = a.id_persona
WHERE m.id_prd_lectivo = 21;


-- Periodos (TDS 21, TAS 22, TAF 23, TSMI 24, TSE 25, EDTS 26, SP 27, TSPM 28, DII 29)
