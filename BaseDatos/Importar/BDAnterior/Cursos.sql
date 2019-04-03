--Consultamos todos los cursos con el nombre de jornada
SELECT matp_numero, matp_periodolectivo, matp_profesor, matp_materia, matp_jornada, matp_paralelo,
 matp_capacidad, jor_descripcion
FROM public.materiaperiodos, public.jornadas
WHERE jor_codigo = matp_jornada
ORDER BY matp_periodolectivo, matp_jornada;

--Consultamos todos los cursos con los datos que necesitamos

SELECT matp_numero, (
	SELECT pro_id
	FROM public.profesores
	WHERE pro_codigo = matp_profesor
), (
	SELECT mat_nombre
	FROM public.materias
	WHERE mat_codigo = matp_materia
),(
	SELECT mat_carrera
	FROM public.materias
	WHERE mat_codigo = matp_materia
), matp_jornada, matp_paralelo,
matp_capacidad, jor_descripcion, (
	SELECT car_id || ' ' || EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
	||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin) AS PeriodoLectivo
	FROM public.periodolectivos, public.carreras
	WHERE per_codigo = matp_periodolectivo
	AND car_codigo = per_carrera
), SUBSTRING(jor_descripcion FROM 1 FOR 1) || (
	SELECT mat_ciclo
	FROM public.materias
	WHERE mat_codigo = matp_materia
)||matp_paralelo AS nombreParalelo
FROM public.materiaperiodos, public.jornadas
WHERE jor_codigo = matp_jornada
ORDER BY matp_periodolectivo, matp_jornada;

--Importamos todos  los cursos
Copy (SELECT matp_numero, (
	SELECT pro_id
	FROM public.profesores
	WHERE pro_codigo = matp_profesor
), (
	SELECT mat_nombre
	FROM public.materias
	WHERE mat_codigo = matp_materia
),(
	SELECT mat_carrera
	FROM public.materias
	WHERE mat_codigo = matp_materia
), matp_jornada, matp_paralelo,
matp_capacidad, jor_descripcion, (
	SELECT car_id || ' ' || EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
	||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin) AS PeriodoLectivo
	FROM public.periodolectivos, public.carreras
	WHERE per_codigo = matp_periodolectivo
	AND car_codigo = per_carrera
), SUBSTRING(jor_descripcion FROM 1 FOR 1) || (
	SELECT mat_ciclo
	FROM public.materias
	WHERE mat_codigo = matp_materia
)||matp_paralelo AS nombreParalelo
FROM public.materiaperiodos, public.jornadas
WHERE jor_codigo = matp_jornada
ORDER BY matp_periodolectivo, matp_jornada)
To 'C:\Backups Postgresql\cursosP1.csv'
with CSV HEADER;

CREATE TABLE materiasperiodos(
	matp_numero integer,
	pro_id character varying(20),
	mat_nombre character varying(200),
	mat_carrera integer,
	matp_jornada integer,
	matp_paralelo character varying(1),
	matp_capacidad integer,
	jor_descripcion character varying(30),
	periodolectivo character varying(200),
	nombreparalelo character varying(10)
)WITH(OIDS = FALSE);

ALTER TABLE public."materiasperiodos" ADD UNIQUE(pro_id, mat_nombre,
	mat_carrera, matp_jornada, matp_paralelo, periodolectivo);

--Borrar con el numero
/*
*148
*7
*273
*20
*659
*/

Copy materiasperiodos(matp_numero, pro_id, mat_nombre, mat_carrera,
	matp_jornada, matp_paralelo, matp_capacidad,
	jor_descripcion, periodolectivo, nombreparalelo)
From 'C:\Backups Postgresql\cursosP1.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

--Consutamos todos los datos con la base de datos actual

SELECT (
	SELECT id_materia
	FROM public."Materias"
	WHERE materia_nombre = mat_nombre
	AND id_carrera = mat_carrera
),(
	SELECT id_prd_lectivo
	FROM public."PeriodoLectivo"
	WHERE prd_lectivo_nombre = periodolectivo
), (
	SELECT id_docente
	FROM public."Docentes"
	WHERE docente_codigo = pro_id
), matp_jornada, nombreparalelo, matp_capacidad,(
	SELECT materia_ciclo
	FROM public."Materias"
	WHERE materia_nombre = mat_nombre
	AND id_carrera = mat_carrera
),matp_paralelo
FROM public.materiasperiodos
ORDER BY id_prd_lectivo;

--Antes de ingresar pasamos estos alters que son muy importantes
ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_docente,
	id_jornada, curso_ciclo, curso_paralelo);
ALTER TABLE public."Cursos" ADD UNIQUE(id_materia, id_prd_lectivo, id_jornada, curso_ciclo, curso_paralelo);

--De la tabla importada debemos borrar
--matp_numero 275 - 333 - 297

DELETE FROM public.materiasperiodos
	WHERE matp_numero = 275;

	DELETE FROM public.materiasperiodos
	WHERE matp_numero = 333;

DELETE FROM public.materiasperiodos
	WHERE matp_numero = 297;

--Se debe ingresar estos nuevos triggers
CREATE OR REPLACE FUNCTION iniciar_ingreso_notas()
RETURNS TRIGGER AS $iniciar_ingreso_notas$
BEGIN
INSERT INTO public."IngresoNotas"(id_curso)
VALUES (new.id_curso);
  RETURN NEW;
END;
$iniciar_ingreso_notas$ LANGUAGE plpgsql;

CREATE TRIGGER inicia_ingreso_notas
AFTER INSERT
ON public."Cursos" FOR EACH ROW
EXECUTE PROCEDURE iniciar_ingreso_notas();

--Se insertan los datos
INSERT INTO public."Cursos"(
	id_materia, id_prd_lectivo, id_docente,
	id_jornada, curso_nombre, curso_capacidad,
	curso_ciclo, curso_paralelo)
SELECT (
	SELECT id_materia
	FROM public."Materias"
	WHERE materia_nombre = mat_nombre
	AND id_carrera = mat_carrera
),(
	SELECT id_prd_lectivo
	FROM public."PeriodoLectivo"
	WHERE prd_lectivo_nombre = periodolectivo
), (
	SELECT id_docente
	FROM public."Docentes"
	WHERE docente_codigo = pro_id
), matp_jornada, nombreparalelo, matp_capacidad,(
	SELECT materia_ciclo
	FROM public."Materias"
	WHERE materia_nombre = mat_nombre
	AND id_carrera = mat_carrera
),matp_paralelo
FROM public.materiasperiodos
ORDER BY id_prd_lectivo;

DROP TABLE public.materiasperiodos;
