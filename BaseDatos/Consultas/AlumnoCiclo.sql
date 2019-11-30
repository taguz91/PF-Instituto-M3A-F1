SELECT DISTINCT
c.curso_ciclo,
persona_primer_apellido ||' '||
persona_segundo_apellido AS NOMBRES,
persona_primer_nombre ||' '||
persona_segundo_nombre AS APELLIDOS,
persona_identificacion,
p.persona_correo,
p.persona_celular,
pr.prd_lectivo_nombre
FROM public."AlumnoCurso" ac, public."Alumnos" a, public."Personas" p,
public."Cursos" c, public."PeriodoLectivo" pr
WHERE a.id_alumno = ac.id_alumno AND
p.id_persona = a.id_persona AND
pr.id_prd_lectivo = c.id_prd_lectivo AND
c.curso_ciclo = 1 AND
pr.id_prd_lectivo = 21 AND
ac.id_curso = c.id_curso
AND almn_curso_activo=true
ORDER BY APELLIDOS;
