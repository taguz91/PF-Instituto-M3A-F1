--Ficha de matriucla para un alumno
SELECT pa.persona_primer_apellido ||' '|| pa.persona_segundo_apellido ||' '||
pa.persona_primer_nombre ||' '|| pa.persona_segundo_nombre as alumno,
pa.persona_identificacion,
pd.persona_primer_apellido ||' '|| pd.persona_segundo_apellido ||' '||
pd.persona_primer_nombre ||' '|| pd.persona_segundo_nombre as docente,
materia_codigo, materia_nombre, carrera_nombre,
curso_ciclo, nombre_jornada, prd_lectivo_nombre,
matricula_tipo, matricula_fecha,
almn_curso_num_matricula
FROM public."Matricula" mt, public."AlumnoCurso" ac,
public."Cursos" c, public."Docentes" d,
public."Materias" m, public."Alumnos" a,
public."Carreras" cr, public."PeriodoLectivo" pl,
public."Personas" pa, public."Personas" pd,
public."Jornadas" j
WHERE pa.persona_identificacion = '0107390270'
AND a.id_persona = pa.id_persona
AND mt.id_alumno = a.id_alumno
AND mt.id_prd_lectivo = 21
AND ac.id_alumno = mt.id_alumno
AND c.id_prd_lectivo = mt.id_prd_lectivo
AND ac.id_curso = c.id_curso
AND pl.id_prd_lectivo = mt.id_prd_lectivo
AND cr.id_carrera = pl.id_carrera
AND d.id_docente = c.id_docente
AND pd.id_persona = d.id_persona
AND j.id_jornada = c.id_jornada
AND m.id_materia = c.id_materia
AND ac.almn_curso_activo = true;


--Actas de compromiso de numero de matricula

SELECT persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_nombre,
materia_nombre,
curso_nombre
FROM public."Carreras" cr, public."Cursos" c,
public."Alumnos" a, public."Personas" p,
public."Materias" m, public."AlumnoCurso" ac,
public."PeriodoLectivo" pl
WHERE a.id_alumno = 471
AND p.id_persona = a.id_persona
AND ac.id_alumno = a.id_alumno
AND c.id_curso = ac.id_curso
AND c.id_prd_lectivo = 21
AND m.id_materia = c.id_materia
AND pl.id_prd_lectivo = c.id_prd_lectivo
AND cr.id_carrera = pl.id_carrera
AND ac.almn_curso_num_matricula = 1
AND ac.almn_curso_activo = true

--Reporte de numero de matricula

SELECT persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_nombre,
	STRING_AGG(
		materia_nombre, ', '
	) Materias
FROM public."Carreras" cr, public."Cursos" c,
public."Alumnos" a, public."Personas" p,
public."Materias" m, public."AlumnoCurso" ac,
public."PeriodoLectivo" pl
WHERE c.id_prd_lectivo = 21
AND p.id_persona = a.id_persona
AND a.id_alumno = ac.id_alumno
AND ac.id_curso = c.id_curso
AND m.id_materia = c.id_materia
AND pl.id_prd_lectivo = c.id_prd_lectivo
AND cr.id_carrera = pl.id_carrera
AND ac.almn_curso_num_matricula = 2
AND ac.almn_curso_activo = true
GROUP BY persona_primer_nombre,
persona_segundo_nombre,
persona_primer_apellido,
persona_segundo_apellido,
persona_identificacion,
carrera_nombre
