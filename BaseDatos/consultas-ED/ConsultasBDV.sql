
SELECT
profesores.pro_codigo,
personas.per_identificacion,   personas.per_primerapellido,   personas.per_segundoapellido,   personas.per_primernombre,   personas.per_segundonombre,
personas.per_celular,
personas.per_correo,
per_fechanacimiento
 FROM   public.profesores,   public.personas WHERE
  profesores.pro_persona = personas.per_codigo ORDER BY  personas.per_primerapellido ASC;



SELECT
periodolectivos.per_codigo,
periodolectivos.per_fechainicio,
periodolectivos.per_fechafin,
carreras.car_codigo,
carreras.car_descripcion,

alumnomaterias.alum_materia,
alumnomaterias.alum_estado,

materias.mat_ciclo,
materias.mat_nombre,

materiaperiodos.matp_numero,
materiaperiodos.matp_paralelo,

jornadas.jor_codigo,
jornadas.jor_descripcion,

personas.per_identificacion,
personas.per_primerapellido,
personas.per_segundoapellido,
personas.per_primernombre,
personas.per_segundonombre,
personas.per_correo,
personas.per_celular,

materiaperiodos.matp_profesor
FROM
  public.periodolectivos,   public.materiaperiodos,   public.periodomatriculas,  public.detallematriculas,   public.materias,   public.carreras,   public.alumnocarreras,
  public.alumnos,   public.personas,   public.alumnomaterias,   public.jornadas
WHERE
  periodolectivos.per_codigo = materiaperiodos.matp_periodolectivo AND
  materiaperiodos.matp_periodolectivo = periodomatriculas.perm_periodolectivo AND
  periodomatriculas.perm_codigo = detallematriculas.detm_periodomatriculas AND
  detallematriculas.detm_materiaperiodos = materiaperiodos.matp_numero AND
  materias.mat_codigo = materiaperiodos.matp_materia AND
  carreras.car_codigo = periodolectivos.per_carrera AND
  alumnocarreras.aluc_codigo = periodomatriculas.perm_alumnocarreras AND
  alumnos.alu_codigo = alumnocarreras.aluc_alumnos AND
  personas.per_codigo = alumnos.alu_persona AND
  alumnomaterias.alum_materia = materias.mat_codigo AND
  alumnomaterias.alum_alumnocarrera = alumnocarreras.aluc_codigo AND
  jornadas.jor_codigo = materiaperiodos.matp_jornada AND detallematriculas.detm_estado = 'M'  AND
  materiaperiodos.matp_profesor='null'and periodolectivos.per_codigo BETWEEN 12 AND 21  ORDER BY carreras.car_descripcion ASC,
jornadas.jor_descripcion ASC,	materias.mat_nombre ASC, materias.mat_ciclo ASC, materiaperiodos.matp_paralelo ASC, per_primerapellido ASC


SELECT
periodolectivos.per_codigo,
periodolectivos.per_fechainicio,
periodolectivos.per_fechafin,
carreras.car_codigo,
carreras.car_descripcion,
alumnomaterias.alum_materia,
alumnomaterias.alum_estado,
materias.mat_ciclo,
materias.mat_nombre,
materiaperiodos.matp_numero,
materiaperiodos.matp_paralelo,
jornadas.jor_codigo,
jornadas.jor_descripcion,
personas.per_identificacion,
personas.per_primerapellido,
personas.per_segundoapellido,
personas.per_primernombre,
personas.per_segundonombre,
personas.per_correo,
personas.per_celular,
materiaperiodos.matp_profesor
FROM
  public.periodolectivos,   public.materiaperiodos,   public.periodomatriculas,
  public.detallematriculas,   public.materias,   public.carreras,
  public.alumnocarreras,   public.alumnos,   public.personas,   public.alumnomaterias,
  public.jornadas
WHERE
  periodolectivos.per_codigo = materiaperiodos.matp_periodolectivo AND
  materiaperiodos.matp_periodolectivo = periodomatriculas.perm_periodolectivo AND
  periodomatriculas.perm_codigo = detallematriculas.detm_periodomatriculas AND
  detallematriculas.detm_materiaperiodos = materiaperiodos.matp_numero AND
  materias.mat_codigo = materiaperiodos.matp_materia AND
  carreras.car_codigo = periodolectivos.per_carrera AND
  alumnocarreras.aluc_codigo = periodomatriculas.perm_alumnocarreras AND
  alumnos.alu_codigo = alumnocarreras.aluc_alumnos AND
  personas.per_codigo = alumnos.alu_persona AND
  alumnomaterias.alum_materia = materias.mat_codigo AND
  alumnomaterias.alum_alumnocarrera = alumnocarreras.aluc_codigo AND
  jornadas.jor_codigo = materiaperiodos.matp_jornada AND detallematriculas.detm_estado = 'M'  AND
  materiaperiodos.matp_profesor='4'and periodolectivos.per_codigo BETWEEN 12 AND 21  ORDER BY carreras.car_descripcion ASC,
jornadas.jor_descripcion ASC,	materias.mat_nombre ASC, materias.mat_ciclo ASC, materiaperiodos.matp_paralelo ASC, per_primerapellido ASC


SELECT
periodolectivos.per_codigo,
periodolectivos.per_fechainicio,
periodolectivos.per_fechafin,
carreras.car_codigo,
carreras.car_descripcion,
alumnomaterias.alum_materia,
alumnomaterias.alum_estado,
materias.mat_ciclo,
materias.mat_nombre,
materiaperiodos.matp_numero,
materiaperiodos.matp_paralelo,
jornadas.jor_codigo,
jornadas.jor_descripcion,
personas.per_identificacion,
personas.per_primerapellido,
personas.per_segundoapellido,
personas.per_primernombre,
personas.per_segundonombre,
personas.per_correo,
personas.per_celular,
materiaperiodos.matp_profesor
FROM
  public.periodolectivos,   public.materiaperiodos,   public.periodomatriculas,   public.detallematriculas,   public.materias,   public.carreras,   public.alumnocarreras,
  public.alumnos,   public.personas,   public.alumnomaterias,   public.jornadas
WHERE
  periodolectivos.per_codigo = materiaperiodos.matp_periodolectivo AND
  materiaperiodos.matp_periodolectivo = periodomatriculas.perm_periodolectivo AND
  periodomatriculas.perm_codigo = detallematriculas.detm_periodomatriculas AND
  detallematriculas.detm_materiaperiodos = materiaperiodos.matp_numero AND
  materias.mat_codigo = materiaperiodos.matp_materia AND
  carreras.car_codigo = periodolectivos.per_carrera AND
  alumnocarreras.aluc_codigo = periodomatriculas.perm_alumnocarreras AND
  alumnos.alu_codigo = alumnocarreras.aluc_alumnos AND
  personas.per_codigo = alumnos.alu_persona AND
  alumnomaterias.alum_materia = materias.mat_codigo AND
  alumnomaterias.alum_alumnocarrera = alumnocarreras.aluc_codigo AND
  jornadas.jor_codigo = materiaperiodos.matp_jornada AND detallematriculas.detm_estado = 'M'  AND
  materiaperiodos.matp_profesor='16'and periodolectivos.per_codigo BETWEEN 12 AND 21  ORDER BY carreras.car_descripcion ASC,
jornadas.jor_descripcion ASC,	materias.mat_nombre ASC, materias.mat_ciclo ASC, materiaperiodos.matp_paralelo ASC, per_primerapellido ASC


SELECT
profesores.pro_codigo,  personas.per_identificacion,   personas.per_primerapellido,   personas.per_segundoapellido,   personas.per_primernombre,   personas.per_segundonombre,  personas.per_celular,
personas.per_correo,
per_fechanacimiento
 FROM   public.profesores,   public.personas WHERE
  profesores.pro_persona = personas.per_codigo ORDER BY  personas.per_primerapellido ASC;

SELECT    profesores.pro_codigo,  personas.per_identificacion,   personas.per_primerapellido,   personas.per_segundoapellido,   personas.per_primernombre,   personas.per_segundonombre,  personas.per_celular,
personas.per_correo,
per_fechanacimiento
FROM
public.profesores,   public.personas
WHERE
profesores.pro_persona = personas.per_codigo ORDER BY  personas.per_primerapellido ASC;


SELECT
periodolectivos.per_codigo,
periodolectivos.per_fechainicio,
periodolectivos.per_fechafin,
carreras.car_codigo,
carreras.car_descripcion,
alumnomaterias.alum_materia,
alumnomaterias.alum_estado,
materias.mat_ciclo,
materias.mat_nombre,
materiaperiodos.matp_numero,
materiaperiodos.matp_paralelo,
jornadas.jor_codigo,
jornadas.jor_descripcion,
personas.per_identificacion,
personas.per_primerapellido,
personas.per_segundoapellido,
personas.per_primernombre,
personas.per_segundonombre,
personas.per_correo,
personas.per_celular,
materiaperiodos.matp_profesor
FROM
  public.periodolectivos,   public.materiaperiodos,   public.periodomatriculas,   public.detallematriculas,   public.materias,   public.carreras,
  public.alumnocarreras,   public.alumnos,   public.personas,   public.alumnomaterias,
  public.jornadas
WHERE
  periodolectivos.per_codigo = materiaperiodos.matp_periodolectivo AND
  materiaperiodos.matp_periodolectivo = periodomatriculas.perm_periodolectivo AND
  periodomatriculas.perm_codigo = detallematriculas.detm_periodomatriculas AND
  detallematriculas.detm_materiaperiodos = materiaperiodos.matp_numero AND
  materias.mat_codigo = materiaperiodos.matp_materia AND
  carreras.car_codigo = periodolectivos.per_carrera AND
  alumnocarreras.aluc_codigo = periodomatriculas.perm_alumnocarreras AND
  alumnos.alu_codigo = alumnocarreras.aluc_alumnos AND
  personas.per_codigo = alumnos.alu_persona AND
  alumnomaterias.alum_materia = materias.mat_codigo AND
  alumnomaterias.alum_alumnocarrera = alumnocarreras.aluc_codigo AND
  jornadas.jor_codigo = materiaperiodos.matp_jornada AND  mat_creditos>0 AND detallematriculas.detm_estado = 'M'  AND  periodolectivos.per_codigo Between 12 and 21 AND personas.per_identificacion = '0107049736'



  SELECT  per_codigo,per_fechainicio,per_fechafin, car_descripcion,car_codigo,car_id from periodolectivos, carreras where   car_codigo=per_carrera and  per_codigo between 12 and 22


  Select DISTINCT (pro_codigo) as matp_profesor, per_primerapellido, per_segundoapellido, per_primernombre, per_segundonombre,  '0'as matp_periodolectivo,pro_codigo as matp_profesor, per_identificacion from personas, profesores,materias,carreras
where personas.per_codigo= profesores.pro_persona
and carreras.car_codigo= materias.mat_carrera order by per_primerapellido
