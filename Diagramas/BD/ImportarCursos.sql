--Importando periodo lectivos  

Copy periodolectivos ("per_codigo", "per_carrera", "per_fechainicio", "per_fechafin")
To 'C:\Backups Postgresql\periodosP1.csv' 
delimiters ';' with CSV HEADER;


CREATE TABLE periodolectivos(
	per_codigo integer, 
	per_carrera integer, 
	per_fechainicio date, 
	per_fechafin date
) WITH (OIDS = FALSE); 

Copy periodolectivos ("per_codigo", "per_carrera", "per_fechainicio", "per_fechafin")
From 'C:\Backups Postgresql\periodosP1.csv' 
delimiter AS ';' NULL AS '' CSV HEADER; 

ALTER TABLE public."PeriodoLectivo" ALTER COLUMN "prd_lectivo_observacion" DROP NOT NULL; 

INSERT INTO public."PeriodoLectivo"(
	id_prd_lectivo, id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin, 
	prd_lectivo_activo)
	SELECT per_codigo, per_carrera, 'DEBEMOS ACTUALIZAR NOMBRE', per_fechainicio, per_fechafin, 'true'
	FROM public.periodolectivos;

--Seteamos el periodo lectivo

INSERT INTO public."PeriodoLectivo"(
	id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin, 
	prd_lectivo_activo)
	SELECT per_carrera, (
	SELECT carrera_codigo  
	FROM public."Carreras"
	WHERE id_carrera = per_carrera
) ||' '|| EXTRACT(MONTH from per_fechainicio) || '/' || EXTRACT(YEAR from per_fechainicio)
  ||' - '|| EXTRACT(MONTH from per_fechafin) || '/' || EXTRACT(YEAR from per_fechafin), 
  per_fechainicio, per_fechafin, 'true'
	FROM public.periodolectivos ORDER BY per_fechafin DESC;

DROP TABLE public.periodolectivos

--Importamos cursos  

--Importando periodo lectivos  

SELECT matp_numero, matp_periodolectivo, matp_profesor, matp_materia, matp_jornada, matp_paralelo,
 matp_capacidad, matp_admining, matp_adminfec1, matp_adminact, matp_adminfec2
	FROM public.materiaperiodos;

Copy materiaperiodos ("matp_numero", "matp_periodolectivo", "matp_profesor", "matp_materia", 
"matp_jornada", "matp_paralelo", "matp_capacidad")
To 'C:\BDPostgresBackups\cursosP1.csv' 
delimiters ';' with CSV HEADER;

-- Table: public.materiaperiodos

-- DROP TABLE public.materiaperiodos;

CREATE TABLE public.materiaperiodos
(
    matp_numero integer,
    matp_periodolectivo integer,
    matp_profesor integer,
    matp_materia integer,
    matp_jornada integer,
    matp_paralelo character(2),
    matp_capacidad integer 
)
WITH (
    OIDS = FALSE
)

Copy materiaperiodos ("matp_numero", "matp_periodolectivo", "matp_profesor", "matp_materia", 
"matp_jornada", "matp_paralelo", "matp_capacidad")
From 'C:\BDPostgresBackups\cursosP1.csv' 
delimiter AS ';' NULL AS '' CSV HEADER; 

--Arregamos la tabla 
select * from public.materiaperiodos 
WHERE matp_profesor = 17
--ID 16
SELECT * from public."Docentes"
WHERE "docente_codigo" = '0703630715';
--ID 17
SELECT * from public."Docentes"
WHERE "docente_codigo" = '0301632725';

UPDATE public.materiaperiodos
	SET matp_profesor = 53
	WHERE matp_profesor = 16 ;

--ID 107
SELECT * from public."Docentes"
WHERE "docente_codigo" = '0104797733';

UPDATE public.materiaperiodos
	SET matp_profesor = 69
	WHERE matp_profesor = 107 ;


INSERT INTO public."Cursos"(
	id_materia, id_prd_lectivo, id_docente, id_jornada, 
	curso_nombre, curso_capacidad, curso_ciclo, 
	curso_permiso_ingreso_nt, curso_paralelo)
	SELECT matp_materia, matp_periodolectivo, matp_profesor, matp_jornada, 
	matp_periodolectivo || '-' ||
	(
		SELECT id_carrera 
		FROM public."PeriodoLectivo"
		WHERE id_prd_lectivo = matp_periodolectivo
		) || '-' || matp_jornada || '-' || 
	 (
		SELECT materia_ciclo 
		FROM public."Materias"
		WHERE id_materia = matp_materia
		), 
	matp_capacidad, (
		SELECT materia_ciclo 
		FROM public."Materias"
		WHERE id_materia = matp_materia
		), 
	'true', matp_paralelo
	FROM public.materiaperiodos;


--Importamos cursos con el id del docente 
SELECT matp_numero, matp_periodolectivo, matp_profesor, matp_materia, matp_jornada, matp_paralelo, 
matp_capacidad, (
SELECT pro_id
FROM public.profesores 
WHERE pro_codigo = matp_profesor)
FROM public.materiaperiodos;


Copy (SELECT matp_numero, matp_periodolectivo, matp_profesor, matp_materia, matp_jornada, matp_paralelo, 
matp_capacidad, (
SELECT pro_id
FROM public.profesores 
WHERE pro_codigo = matp_profesor)
FROM public.materiaperiodos)
To 'C:\BDPostgresBackups\cursosP2.csv' 
with CSV HEADER;


CREATE TABLE public.materiaperiodos
(
    matp_numero integer,
    matp_periodolectivo integer,
    matp_profesor integer,
    matp_materia integer,
    matp_jornada integer,
    matp_paralelo character(2),
    matp_capacidad integer, 
    pro_id character varying(20) 
)
WITH ( OIDS = FALSE);


Copy materiaperiodos("matp_numero", "matp_periodolectivo", "matp_profesor",
	"matp_materia", "matp_jornada", "matp_paralelo",
	"matp_capacidad", "pro_id")
From 'C:\BDPostgresBackups\cursosP2.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;


ALTER TABLE public."Cursos" ALTER COLUMN "id_docente" DROP NOT NULL; 

INSERT INTO public."Cursos"(
	id_materia, id_prd_lectivo, id_docente, id_jornada, 
	curso_nombre, curso_capacidad, curso_ciclo, 
	curso_permiso_ingreso_nt, curso_paralelo)
	SELECT matp_materia, matp_periodolectivo, (
		SELECT id_docente  
		FROM public."Docentes" 
		WHERE docente_codigo  = pro_id
	), matp_jornada, 
	matp_periodolectivo || '-' ||
	(
		SELECT id_carrera 
		FROM public."PeriodoLectivo"
		WHERE id_prd_lectivo = matp_periodolectivo
		) || '-' || matp_jornada || '-' || 
	 (
		SELECT materia_ciclo 
		FROM public."Materias"
		WHERE id_materia = matp_materia
		), 
	matp_capacidad, (
		SELECT materia_ciclo 
		FROM public."Materias"
		WHERE id_materia = matp_materia
		), 
	'true', matp_paralelo
	FROM public.materiaperiodos;


--Importamos materias docente  

Copy (SELECT pro_id, mat_nombre, mat_carrera  FROM public.docentematerias, 
public.profesores, public.materias
WHERE profesores.pro_codigo = docm_profesor AND mat_codigo = docm_materia)
To 'C:\Backups Postgresql\docentesMateriaP1.csv' 
with CSV HEADER;

CREATE TABLE "docentesmateria"(
	docente_cedula character varying(20), 
	nateria_nombre character varying(100), 
	id_carrera integer
); 

Copy docentesmateria("docente_cedula", "nateria_nombre", "id_carrera")
From 'C:\Backups Postgresql\docentesMateriaP1.csv' 
delimiter AS ',' NULL AS '' CSV HEADER;

--Se edita el id de carrera de la tabla importada
UPDATE public.docentesmateria
	SET id_carrera=10
	WHERE id_carrera=11;

--Estas dos personas no se encuentras registradas en docentes 
SELECT * FROM public."Personas" WHERE persona_identificacion = '0103687323'
SELECT * FROM public."Personas" WHERE persona_identificacion = '0104851720'

INSERT INTO public."Docentes"(
	id_persona, docente_codigo, docente_otro_trabajo,
	 docente_categoria, docente_fecha_contrato, 
	 docente_tipo_tiempo)
SELECT id_persona, '0103687323', 'false', 6, '2016-10-01', 'C'
		FROM public."Personas"
		WHERE persona_identificacion = '0103687323'

INSERT INTO public."Docentes"(
	id_persona, docente_codigo, docente_otro_trabajo,
	 docente_categoria, docente_fecha_contrato, 
	 docente_tipo_tiempo)
SELECT id_persona, '0104851720', 'false', 6, '2015-06-01', 'C'
		FROM public."Personas"
		WHERE persona_identificacion = '0104851720'

--Consultamos el id del docente 
SELECT (SELECT id_docente
	   FROM public."Docentes" 
	   WHERE docente_codigo = docente_cedula), 
	    'true'
FROM public.docentesmateria;

--Consultamos las materias que se repiten en la tabla materias para eliminarlas  
SELECT materia_nombre, id_carrera, COUNT(*) 
FROM public."Materias" 
GROUP BY id_carrera, materia_nombre 
HAVING COUNT(*) > 1;

--Consultamos sucodigo para eliminar una de ellas  
SELECT * 
FROM public."Materias" 
WHERE materia_nombre = 'DERECHOS HUMANOS APLICADOS AL CONTEXTO PENITENCIARIO';

DELETE FROM public."Materias"
	WHERE id_materia = 325;

SELECT * 
FROM public."Materias" 
WHERE materia_nombre = 'SEGURIDAD INDUSTRIAL';

DELETE FROM public."Materias"
	WHERE id_materia = 317;

SELECT * 
FROM public."Materias" 
WHERE materia_nombre = 'TICS II';

DELETE FROM public."Materias"
	WHERE id_materia = 324;


--Insertamos docentes materia 
INSERT INTO public."DocentesMateria"(
	id_docente, id_materia, docente_mat_activo)
SELECT (SELECT id_docente
	   FROM public."Docentes" 
	   WHERE docente_codigo = docente_cedula), 
	   (SELECT id_materia 
	   FROM public."Materias"
	   WHERE materia_nombre = nateria_nombre AND 
	   id_carrera = docentesmateria.id_carrera), 'true'
FROM public.docentesmateria;

DROP TABLE docentesmateria