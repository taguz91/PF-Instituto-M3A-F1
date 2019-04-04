--Consultams el docente y que materia en que carrera da la clases
SELECT id_sesion, sc.id_curso, dia_sesion, hora_inicio_sesion,
hora_fin_sesion, c.id_docente, docente_codigo, materia_nombre,
id_carrera, id_prd_lectivo
FROM public."SesionClase" sc, public."Cursos" c,
public."Docentes" d, public."Personas" p, public."Materias" m
WHERE c.id_curso = sc.id_curso AND
c.id_docente = d.id_docente AND
p.id_persona = d.id_persona AND
m.id_materia = c.id_materia;

--Con esto consultamos los datos en cualquier base de datos
Copy (SELECT id_sesion AS sesion, sc.id_curso AS curso,
  curso_nombre AS paralelo,
  dia_sesion AS dia, hora_inicio_sesion AS hora_inicio,
  hora_fin_sesion AS hora_fin, c.id_docente AS docente,
  docente_codigo AS codigo, materia_nombre AS nombre,
  id_carrera AS carrera, id_prd_lectivo AS periodo
FROM public."SesionClase" sc, public."Cursos" c,
public."Docentes" d, public."Personas" p, public."Materias" m
WHERE c.id_curso = sc.id_curso AND
c.id_docente = d.id_docente AND
p.id_persona = d.id_persona AND
m.id_materia = c.id_materia)
To 'C:\Backups Postgresql\BDActual\sesionClaseP1.csv'
WITH csv HEADER

--Datos de la tabla
--id_sesion,id_curso,dia_sesion,hora_inicio_sesion,hora_fin_sesion,
--id_docente,docente_codigo,materia_nombre,id_carrera
CREATE TABLE sesionclase(
  sesion integer,
  curso integer,
  dia integer,
  hora_inicio time without time zone,
  hora_fin time without time zone,
  docente integer,
  codigo character varying(20),
  nombre character varying(450),
  carrera integer,
  periodo integer,
  paralelo character varying(20)
)WITH(OIDS = FALSE);

Copy public.sesionclase(sesion,curso,paralelo,dia,
  hora_inicio,hora_fin,docente,codigo,nombre,carrera,periodo)
From 'C:\Backups Postgresql\BDActual\sesionClaseP1.csv'
delimiter AS ',' NULL AS '' CSV HEADER;
--El period esta mal por eso no me servia
--Importante revisarlo
--No me consulta bien
SELECT sesion, dia, hora_inicio,
hora_fin, (
	SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = periodo AND id_materia =(
   		SELECT id_materia FROM public."Materias"
		WHERE materia_nombre ILIKE nombre
		AND id_carrera = carrera )
	AND id_docente = (
		SELECT id_docente FROM public."Docentes"
		WHERE docente_codigo = codigo))
FROM public.sesionclase;

--Con el nombre del curso

SELECT sesion, dia, hora_inicio,
hora_fin, (
	SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = periodo AND id_materia =(
   		SELECT id_materia FROM public."Materias"
		WHERE materia_nombre ILIKE nombre
		AND id_carrera = carrera )
	AND id_docente = (
		SELECT id_docente FROM public."Docentes"
		WHERE docente_codigo = codigo) AND curso_nombre = paralelo)
FROM public.sesionclase
ORDER BY sesion;

--Sesion auxiliar
CREATE TABLE sesion(
  sesio integer,
  dia integer,
  hora_inicio time without time zone,
  hora_fin time without time zone
)WITH(OIDS = FALSE);

--Actualizar el periodo lectivo
--Revisar al periodo al que pertenece
UPDATE public.sesionclase
  SET periodo = 4;

--Insertamos en sesion
INSERT INTO public.sesion(
	sesio, dia, hora_inicio, hora_fin)
	SELECT (
	SELECT id_curso
    FROM public."Cursos"
    WHERE id_prd_lectivo = periodo AND id_materia =(
   		SELECT id_materia FROM public."Materias"
		WHERE materia_nombre ILIKE nombre
		AND id_carrera = carrera )
	AND id_docente = (
		SELECT id_docente FROM public."Docentes"
		WHERE docente_codigo = codigo) AND curso_nombre = paralelo),
		dia, hora_inicio, hora_fin
FROM public.sesionclase
ORDER BY sesion;

--Consultamos las materias
SELECT * FROM public.sesion
WHERE sesio IS NULL
--Borramos la materias que no tiene id sesion
DELETE FROM public.sesion
WHERE sesio IS NULL

INSERT INTO public."SesionClase"(
	id_curso, dia_sesion, hora_inicio_sesion, hora_fin_sesion)
	SELECT sesio, dia, hora_inicio, hora_fin
		FROM public.sesion;

--Insetamos en lat
DROP TABLE public.sesion;
DROP TABLE public.sesionclase;


--Consultamos con los cursos actuales
