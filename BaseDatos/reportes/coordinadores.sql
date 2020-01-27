SELECT
carrera_codigo,
carrera_nombre,
persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_telefono,
persona_celular,
persona_correo

FROM public."Carreras" c
JOIN public."Docentes" d ON c.id_docente_coordinador = d.id_docente
JOIN public."Personas" p ON p.id_persona = d.id_persona
