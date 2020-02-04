SELECT DISTINCT
p.persona_identificacion,
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
"CALCULAR_PROMEDIO_NOT_SIMILAR_TO" (
  ac.id_almn_carrera, '%(PTI|FASE PRÁ|INGLÉS)%'
) AS fase_teorica,
"CALCULAR_PROMEDIO_SIMILAR_TO" (
  ac.id_almn_carrera, '%(PTI)%'
) AS pti,
"CALCULAR_PROMEDIO_SIMILAR_TO" (
  ac.id_almn_carrera, '%(FASE PR)%'
) AS fase_practica
FROM
alumno."Egresados" eg
JOIN public."AlumnosCarrera" ac ON eg.id_almn_carrera
JOIN public."Alumnos" a ON ac.id_alumno = a.id_alumno
JOIN public."Personas" p ON a.id_persona = p.id_persona
WHERE eg.id_prd_lectivo  = ;

--

SELECT DISTINCT
p.persona_identificacion,
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
"CALCULAR_PROMEDIO_NOT_SIMILAR_TO" (
  ac.id_almn_carrera, '%(PTI|FASE PRÁ)%'
) AS promedio_final
FROM alumno."Egresados" eg
JOIN public."AlumnosCarrera" ac ON ac.id_almn_carrera = eg.id_almn_carrera
JOIN public."Alumnos" a ON ac.id_alumno = a.id_alumno
JOIN public."Personas" p ON a.id_persona = p.id_persona
WHERE eg.id_prd_lectivo  = ;

-- Solo el promedio del egresado

SELECT
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo,
COUNT(ma.malla_almn_ciclo) AS num_materias,
promedio_malla_ciclo_not_similar_to(
  ma.id_almn_carrera, malla_almn_ciclo, '%(PTI|FASE PRÁ|INGLÉS)%'
) AS fase_teorica,
promedio_malla_ciclo_similar_to(
  ma.id_almn_carrera, malla_almn_ciclo, '%(FASE PR)%'
) AS fase_practica,
promedio_malla_ciclo_similar_to(
  ma.id_almn_carrera, malla_almn_ciclo, '%(PTI)%'
) AS pti
FROM alumno."Egresados" eg
JOIN public."AlumnosCarrera" ac
ON ac.id_almn_carrera = eg.id_almn_carrera
JOIN public."MallaAlumno" ma
ON ma.id_almn_carrera = ac.id_almn_carrera
JOIN public."Alumnos" a
ON a.id_alumno = ac.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE ma.malla_almn_estado = 'C'
AND eg.id_egresado =
GROUP BY
ma.id_almn_carrera,
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo
ORDER BY
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
ma.malla_almn_ciclo;


--


SELECT
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo,
COUNT(ma.malla_almn_ciclo) AS num_materias,
promedio_malla_ciclo_not_similar_to(
  ma.id_almn_carrera, malla_almn_ciclo, '%(PTI|FASE PRÁ)%'
) AS promedio_final
FROM alumno."Egresados" eg
JOIN public."AlumnosCarrera" ac
ON ac.id_almn_carrera = eg.id_almn_carrera
JOIN public."MallaAlumno" ma
ON ma.id_almn_carrera = ac.id_almn_carrera
JOIN public."Alumnos" a
ON a.id_alumno = ac.id_alumno
JOIN public."Personas" p
ON p.id_persona = a.id_persona
WHERE ma.malla_almn_estado = 'C'
AND eg.id_egresado =
GROUP BY
ma.id_almn_carrera,
p.persona_identificacion,
p.persona_primer_nombre,
p.persona_segundo_nombre,
p.persona_primer_apellido,
p.persona_segundo_apellido,
ma.malla_almn_ciclo
ORDER BY
p.persona_primer_apellido,
p.persona_segundo_apellido,
p.persona_primer_nombre,
p.persona_segundo_nombre,
ma.malla_almn_ciclo;
