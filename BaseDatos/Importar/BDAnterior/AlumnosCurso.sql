--Consultamos los cursos en los que se matriculo un estudiante
SELECT *
FROM public.detallematriculas dm, public.materiaperiodos mp,
public.periodomatricula pm, public.alumnocarreras ac
public.profesores pf, public.alumnos al, public.personas pr,
public.materias mt
WHERE dm.detm_materiaperiodos = mp.matp_numero AND
mt.mat_codigo = matp_materia AND

--Sacamos los profesores y las materias de un curso
SELECT pro_id AS docente, matp_periodolectivo AS prd_curso,
matp_jornada AS jrd_curso, matp_paralelo AS prl_curso, mat_id
AS mat_codigo, mat_nombre AS mat_nombre, mat_carrera AS mat_carrera
FROM public.detallematriculas dm, public.materiaperiodos mp,
public.profesores pf, public.materias mt
WHERE dm.detm_materiaperiodos = mp.matp_numero AND
mt.mat_codigo = matp_materia AND
pf.pro_codigo = matp_profesor

--Sacamos el curso con el nombre del periodo y si es el mismo periodo
SELECT pro_id AS docente, matp_jornada AS jrd_curso, matp_paralelo AS prl_curso,
mat_id AS mat_codigo, mat_nombre AS mat_nombre, mat_carrera AS mat_carrera, (
    SELECT ( SELECT car_id
    	FROM public.carreras
    	WHERE car_codigo = per_carrera)
    ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
    ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin)
    FROM public.periodolectivos
    WHERE per_codigo = perm_periodolectivo
), (matp_periodolectivo = perm_periodolectivo) AS mismo_prd
FROM public.detallematriculas dm, public.materiaperiodos mp,
public.profesores pf, public.materias mt, public.periodomatriculas pm
WHERE dm.detm_materiaperiodos = mp.matp_numero AND
mt.mat_codigo = matp_materia AND
pf.pro_codigo = matp_profesor AND
dm.detm_periodomatriculas = pm.perm_codigo;

--Consutalmos los cursos con los estudiantes
SELECT pro_id AS docente, matp_jornada AS jrd_curso, matp_paralelo AS prl_curso,
mat_id AS mat_codigo, mat_nombre AS mat_nombre, mat_carrera AS mat_carrera, (
    SELECT ( SELECT car_id
    	FROM public.carreras
    	WHERE car_codigo = per_carrera)
    ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
    ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin)
    FROM public.periodolectivos
    WHERE per_codigo = perm_periodolectivo
), (matp_periodolectivo = perm_periodolectivo) AS mismo_prd,
alu_id AS almn_curso, detm_fecha AS fecha_registro
FROM public.detallematriculas dm, public.materiaperiodos mp,
public.profesores pf, public.materias mt, public.periodomatriculas pm,
public.alumnocarreras ac, public.alumnos al
WHERE dm.detm_materiaperiodos = mp.matp_numero AND
mt.mat_codigo = matp_materia AND
pf.pro_codigo = matp_profesor AND
dm.detm_periodomatriculas = pm.perm_codigo AND
ac.aluc_codigo = pm.perm_alumnocarreras AND
al.alu_codigo = ac.aluc_alumnos;

--Buscamos con nombres para saber si esta todo bien
SELECT pro_id AS docente, matp_jornada AS jrd_curso, matp_paralelo AS prl_curso,
mat_id AS mat_codigo, mat_nombre AS mat_nombre, mat_carrera AS mat_carrera, (
    SELECT ( SELECT car_id
    	FROM public.carreras
    	WHERE car_codigo = per_carrera)
    ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
    ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin)
    FROM public.periodolectivos
    WHERE per_codigo = perm_periodolectivo
), (matp_periodolectivo = perm_periodolectivo) AS mismo_prd,
alu_id AS almn_curso, per_identificacion, per_primerapellido,
per_primernombre
FROM public.detallematriculas dm, public.materiaperiodos mp,
public.profesores pf, public.materias mt, public.periodomatriculas pm,
public.alumnocarreras ac, public.alumnos al, public.personas pr
WHERE dm.detm_materiaperiodos = mp.matp_numero AND
mt.mat_codigo = matp_materia AND
pf.pro_codigo = matp_profesor AND
dm.detm_periodomatriculas = pm.perm_codigo AND
ac.aluc_codigo = pm.perm_alumnocarreras AND
al.alu_codigo = ac.aluc_alumnos AND
pr.per_codigo = al.alu_codigo AND
matp_paralelo = 'A' AND matp_jornada = 1
AND perm_periodolectivo = 12;

--Consultamos con mas datos y ordenado

--Consutalmos los cursos con los estudiantes
SELECT pro_id AS docente, matp_jornada AS jrd_curso, matp_paralelo AS prl_curso,
mat_id AS mat_codigo, mat_nombre AS mat_nombre, mat_carrera AS mat_carrera, (
    SELECT ( SELECT car_id
    	FROM public.carreras
    	WHERE car_codigo = per_carrera)
    ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
    ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin)
    FROM public.periodolectivos
    WHERE per_codigo = perm_periodolectivo
), (matp_periodolectivo = perm_periodolectivo) AS mismo_prd,
alu_id AS almn_curso, detm_fecha AS fecha_registro, detm_ciclo AS ciclo_curso
FROM public.detallematriculas dm, public.materiaperiodos mp,
public.profesores pf, public.materias mt, public.periodomatriculas pm,
public.alumnocarreras ac, public.alumnos al
WHERE dm.detm_materiaperiodos = mp.matp_numero AND
mt.mat_codigo = matp_materia AND
pf.pro_codigo = matp_profesor AND
dm.detm_periodomatriculas = pm.perm_codigo AND
ac.aluc_codigo = pm.perm_alumnocarreras AND
al.alu_codigo = ac.aluc_alumnos
ORDER BY detm_fecha;

--Asi funcionan las calificaciones
SELECT cal_numero, cal_nombre, cal_orden, cal_tipocalificacion
FROM public.calificaciones
WHERE cal_calificacioncarrera = 9 ORDER BY cal_orden

SELECT cal_numero
FROM public.calificaciones
WHERE cal_calificacioncarrera = 9 AND
cal_orden = 1;

--Ahora lo consultamos con las notas - Hacer esto esta complicado debido a que
--algunas carreras no tienen definido si nota
--No me devuelve datos en las notas algo pasaaaa MI NIÑOOOO
SELECT pro_id AS docente, matp_jornada AS jrd_curso, matp_paralelo AS prl_curso,
mat_id AS mat_codigo, mat_nombre AS mat_nombre, mat_carrera AS mat_carrera, (
    SELECT ( SELECT car_id
    	FROM public.carreras
    	WHERE car_codigo = per_carrera)
    ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
    ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin)
    FROM public.periodolectivos
    WHERE per_codigo = perm_periodolectivo
), (matp_periodolectivo = perm_periodolectivo) AS mismo_prd,
alu_id AS almn_curso, detm_fecha AS fecha_registro, detm_ciclo AS ciclo_curso, (
  SELECT caldm_puntuacion
  FROM public.calificaciondetallematriculas
  WHERE caldm_periodomatricula = detm_periodomatriculas AND caldm_calificacion = (
    SELECT cal_numero
    FROM public.calificaciones
    WHERE cal_calificacioncarrera = mat_carrera AND
    cal_orden = 1
  )
  AND caldm_materiaperiodo = detm_materiaperiodos
) AS primer_aporte,(
  SELECT caldm_puntuacion
  FROM public.calificaciondetallematriculas
  WHERE caldm_periodomatricula = detm_periodomatriculas AND caldm_calificacion = (
    SELECT cal_numero
    FROM public.calificaciones
    WHERE cal_calificacioncarrera = mat_carrera AND
    cal_orden = 2
  )
  AND caldm_materiaperiodo = detm_materiaperiodos
) AS examen_interciclo, (
  SELECT caldm_puntuacion
  FROM public.calificaciondetallematriculas
  WHERE caldm_periodomatricula = detm_periodomatriculas AND caldm_calificacion = (
    SELECT cal_numero
    FROM public.calificaciones
    WHERE cal_calificacioncarrera = mat_carrera AND
    cal_orden = 4
  )
  AND caldm_materiaperiodo = detm_materiaperiodos
) AS segundo_aporte, (
  SELECT caldm_puntuacion
  FROM public.calificaciondetallematriculas
  WHERE caldm_periodomatricula = detm_periodomatriculas AND caldm_calificacion = (
    SELECT cal_numero
    FROM public.calificaciones
    WHERE cal_calificacioncarrera = mat_carrera AND
    cal_orden = 5
  )
  AND caldm_materiaperiodo = detm_materiaperiodos
) AS examen_final, (
  SELECT caldm_puntuacion
  FROM public.calificaciondetallematriculas
  WHERE caldm_periodomatricula = detm_periodomatriculas AND caldm_calificacion = (
    SELECT cal_numero
    FROM public.calificaciones
    WHERE cal_calificacioncarrera = mat_carrera AND
    cal_orden = 6
  )
  AND caldm_materiaperiodo = detm_materiaperiodos
) AS examen_recuperacion,  (
  SELECT caldm_puntuacion
  FROM public.calificaciondetallematriculas
  WHERE caldm_periodomatricula = detm_periodomatriculas AND caldm_calificacion = (
    SELECT cal_numero
    FROM public.calificaciones
    WHERE cal_calificacioncarrera = mat_carrera AND
    cal_orden = 7
  )
  AND caldm_materiaperiodo = detm_materiaperiodos
) AS nota_final, (
  SELECT caldm_puntuacion
  FROM public.calificaciondetallematriculas
  WHERE caldm_periodomatricula = detm_periodomatriculas AND caldm_calificacion = (
    SELECT cal_numero
    FROM public.calificaciones
    WHERE cal_calificacioncarrera = mat_carrera AND
    cal_orden = 8
  )
  AND caldm_materiaperiodo = detm_materiaperiodos
) AS num_faltas

FROM public.detallematriculas dm, public.materiaperiodos mp,
public.profesores pf, public.materias mt, public.periodomatriculas pm,
public.alumnocarreras ac, public.alumnos al
WHERE dm.detm_materiaperiodos = mp.matp_numero AND
mt.mat_codigo = matp_materia AND
pf.pro_codigo = matp_profesor AND
dm.detm_periodomatriculas = pm.perm_codigo AND
ac.aluc_codigo = pm.perm_alumnocarreras AND
al.alu_codigo = ac.aluc_alumnos
ORDER BY detm_fecha;

--Exportamos
Copy (
  SELECT pro_id AS docente, matp_jornada AS jrd_curso, matp_paralelo AS prl_curso,
  mat_id AS mat_codigo, mat_nombre AS mat_nombre, mat_carrera AS mat_carrera, (
      SELECT ( SELECT car_id
        FROM public.carreras
        WHERE car_codigo = per_carrera)
      ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
      ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin)
      FROM public.periodolectivos
      WHERE per_codigo = perm_periodolectivo
  ) AS prd_nombre, (matp_periodolectivo = perm_periodolectivo) AS mismo_prd,
  alu_id AS almn_curso, detm_fecha AS fecha_registro, detm_ciclo AS ciclo_curso
  FROM public.detallematriculas dm, public.materiaperiodos mp,
  public.profesores pf, public.materias mt, public.periodomatriculas pm,
  public.alumnocarreras ac, public.alumnos al
  WHERE dm.detm_materiaperiodos = mp.matp_numero AND
  mt.mat_codigo = matp_materia AND
  pf.pro_codigo = matp_profesor AND
  dm.detm_periodomatriculas = pm.perm_codigo AND
  ac.aluc_codigo = pm.perm_alumnocarreras AND
  al.alu_codigo = ac.aluc_alumnos
  ORDER BY detm_fecha
)To 'C:\Backups Postgresql\alumnosCursoP1.csv'
with CSV HEADER;

--Columnas
--docente,jrd_curso,prl_curso,mat_codigo,mat_nombre,
--mat_carrera,prd_nombre,mismo_prd,almn_curso,fecha_registro,ciclo_curso

CREATE TABLE almscurso(
  docente character varying(10),
  jrd_curso integer,
  prl_curso character varying(5),
  mat_codigo character varying(15),
  mat_nombre character varying(150),
  mat_carrera integer,
  prd_nombre character varying(200),
  mismo_prd boolean,
  almn_curso character varying(20),
  fecha_registro date,
  ciclo_curso integer
) WITH (OIDS = FALSE);

--Importamos
Copy almscurso(docente,jrd_curso,prl_curso,mat_codigo,mat_nombre,
mat_carrera,prd_nombre,mismo_prd,almn_curso,fecha_registro,ciclo_curso)
From 'C:\Backups Postgresql\alumnosCursoP1.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

--Se actualiza la carrera 11 por 10
UPDATE public.almscurso
  SET mat_carrera = 10
  WHERE mat_carrera = 11

--Consutamos con los datos neuvos
SELECT mismo_prd,fecha_registro,ciclo_curso, (
	SELECT id_alumno FROM public."Alumnos" a,
	public."Personas" p
	WHERE p.persona_identificacion = almn_curso AND
	a.id_persona = p.id_persona
) AS id_almn, (
	SELECT id_curso
	FROM public."Cursos" c, public."Materias" m,
  public."PeriodoLectivo" pl, public."Docentes" d
	WHERE m.id_materia = c.id_materia AND
  pl.id_prd_lectivo = c.id_prd_lectivo AND
  d.id_docente = c.id_docente AND
  m.materia_nombre ILIKE mat_nombre AND
  m.id_carrera = mat_carrera AND
  d.docente_codigo = docente AND
  pl.prd_lectivo_nombre = prd_nombre AND
  id_jornada = jrd_curso AND
  curso_paralelo = prl_curso
) AS id_cur
FROM public.almscurso

--Creamos una nueva tabla para borrar los datos nulos
CREATE TABLE almscursonuevo(
  id serial,
  mis_prd boolean,
  fecha_registro date,
  ciclo_curso integer,
  id_almn integer,
  id_cur integer
) WITH(OIDS = FALSE);


--Llenamos la tabla
INSERT INTO public.almscursonuevo(
	mis_prd, fecha_registro, ciclo_curso, id_almn, id_cur)
SELECT mismo_prd,fecha_registro,ciclo_curso, (
	SELECT id_alumno FROM public."Alumnos" a,
	public."Personas" p
	WHERE p.persona_identificacion = almn_curso AND
	a.id_persona = p.id_persona
) AS id_almn, (
	SELECT id_curso
	FROM public."Cursos" c, public."Materias" m,
	public."PeriodoLectivo" pl, public."Docentes" d
	WHERE m.id_materia = c.id_materia AND
	pl.id_prd_lectivo = c.id_prd_lectivo AND
	d.id_docente = c.id_docente AND
	m.materia_nombre ILIKE mat_nombre AND
	m.id_carrera = mat_carrera AND
	d.docente_codigo = docente AND
	pl.prd_lectivo_nombre = prd_nombre AND
	id_jornada = jrd_curso AND
	curso_paralelo = prl_curso
) AS id_cur
FROM public.almscurso;

TRUNCATE TABLE public.almscursonuevo;

--Insertamos los datos nuevos pero igualando con la cedula en alumno
INSERT INTO public.almscursonuevo(
	mis_prd, fecha_registro, ciclo_curso, id_almn, id_cur)
SELECT mismo_prd,fecha_registro,ciclo_curso, (
	SELECT id_alumno FROM public."Alumnos"
	WHERE alumno_codigo = almn_curso
) AS id_almn, (
	SELECT id_curso
	FROM public."Cursos" c, public."Materias" m,
	public."PeriodoLectivo" pl, public."Docentes" d
	WHERE m.id_materia = c.id_materia AND
	pl.id_prd_lectivo = c.id_prd_lectivo AND
	d.id_docente = c.id_docente AND
	m.materia_nombre ILIKE mat_nombre AND
	m.id_carrera = mat_carrera AND
	d.docente_codigo = docente AND
	pl.prd_lectivo_nombre = prd_nombre AND
	id_jornada = jrd_curso AND
	curso_paralelo = prl_curso
) AS id_cur
FROM public.almscurso;

--Buscamos los que no tienen curso
SELECT * FROM public.almscursonuevo
WHERE id_cur IS NULL
--Nos retorno 39 que no tienen curso en la nueva base de datos
--Consultamos los que no tienen id en el nuevo curso
SELECT * FROM public.almscursonuevo
WHERE id_almn IS NULL
--Nos devuelve 197
--Consultamos donde los periodos lectivos no coinciden
SELECT * FROM public.almscursonuevo
WHERE mis_prd = false
--Nos devuelve 24

DELETE FROM public.almscursonuevo
WHERE id_cur IS NULL; --Se borraron 39

DELETE FROM public.almscursonuevo
WHERE id_almn IS NULL; --Se borraron 193

DELETE FROM public.almscursonuevo
WHERE mis_prd = false; --Se borraron 0

--Consultamos los cursos que se repiten
SELECT id_almn, ciclo_curso, id_cur, fecha_registro, count(*)
FROM public.almscursonuevo
GROUP BY id_almn, ciclo_curso, id_cur, fecha_registro
HAVING count(*) > 1
--Estos son los id que se repiten

SELECT * FROM public.almscursonuevo
WHERE id_almn IN (1278,
1256,
477,
1241,
1255,
1257,
1281,
1280,
794,
1246,
1279,
780,
1254)
ORDER BY id_almn, id_cur

--El curso que se repite mas veces es el 110 y otro
DELETE FROM public.almscursonuevo
WHERE id IN (2141, 2136, 8621, 368, 8508, 8411,
  8590, 8574, 8582, 12259, 8551, 8477, 8617, 8598) ;
--Se borraron 14

ALTER TABLE public."AlumnoCurso" ADD COLUMN "almn_curso_fecha_registro" DATE default now();

--Insertamos en la tabla que es
INSERT INTO public."AlumnoCurso"(
id_alumno, id_curso, almn_curso_fecha_registro)
SELECT id_almn, id_cur, fecha_registro
FROM public.almscursonuevo
ORDER BY fecha_registro

DROP TABLE public.almscurso;
DROP TABLE public.almscursonuevo;
