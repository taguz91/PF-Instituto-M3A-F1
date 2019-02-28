--Importando periodo lectivos  

Copy periodolectivos ("per_codigo", "per_carrera", "per_fechainicio", "per_fechafin")
To 'C:\BDPostgresBackups\periodosP1.csv' 
delimiters ';' with CSV HEADER;


CREATE TABLE periodolectivos(
	per_codigo integer, 
	per_carrera integer, 
	per_fechainicio date, 
	per_fechafin date
) WITH (OIDS = FALSE); 

Copy periodolectivos ("per_codigo", "per_carrera", "per_fechainicio", "per_fechafin")
From 'C:\BDPostgresBackups\periodosP1.csv' 
delimiter AS ';' NULL AS '' CSV HEADER; 

ALTER TABLE public."PeriodoLectivo" ALTER COLUMN "prd_lectivo_observacion" DROP NOT NULL; 

INSERT INTO public."PeriodoLectivo"(
	id_prd_lectivo, id_carrera, prd_lectivo_nombre, prd_lectivo_fecha_inicio, prd_lectivo_fecha_fin, 
	prd_lectivo_activo)
	SELECT per_codigo, per_carrera, 'DEBEMOS ACTUALIZAR NOMBRE', per_fechainicio, per_fechafin, 'true'
	FROM public.periodolectivos;

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

