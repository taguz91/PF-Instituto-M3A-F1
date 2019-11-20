SELECT
persona_identificacion,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
matricula_fecha,
matricula_tipo
WHERE
public."Matricula" m
JOIN public."Alumnos" a ON m.id_alumno = a.id_alumno
JOIN public."Personas" p ON p.id_persona = a.id_persona
WHERE matricula_tipo ILIKE '%%'
AND id_prd_lectivo IN (
  31, 32
);
