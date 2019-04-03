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

Copy (SELECT id_sesion, sc.id_curso, dia_sesion, hora_inicio_sesion,
hora_fin_sesion, c.id_docente, docente_codigo, materia_nombre,
id_carrera, id_prd_lectivo
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
  id_sesion integer,
  id_curso integer,
  dia_sesion integer,
  hora_inicio_sesion time without time zone,
  hora_fin_sesion time without time zone,
  id_docente integer,
  docente_codigo character varying(20),
  materia_nombre character varying(450),
  id_carrera integer,
  id_prd_lectivo integer
)WITH(OIDS = FALSE);

Copy public.sesionclase(id_sesion,id_curso,dia_sesion,hora_inicio_sesion,hora_fin_sesion,
id_docente,docente_codigo,materia_nombre,id_carrera, id_prd_lectivo)
From 'C:\Backups Postgresql\BDActual\sesionClaseP1.csv'
delimiter AS ',' NULL AS '' CSV HEADER;

--Con esto consultamos los datos en cualquier base de datos
Copy (SELECT id_sesion, sc.id_curso, dia_sesion, hora_inicio_sesion,
hora_fin_sesion, c.id_docente, docente_codigo, materia_nombre,
id_carrera, id_prd_lectivo
FROM public."SesionClase" sc, public."Cursos" c,
public."Docentes" d, public."Personas" p, public."Materias" m
WHERE c.id_curso = sc.id_curso AND
c.id_docente = d.id_docente AND
p.id_persona = d.id_persona AND
m.id_materia = c.id_materia)
To 'C:\Backups Postgresql\BDActual\sesionClaseP1.csv'
WITH csv HEADER

DROP TABLE public.sesionclase

--Consultamos con los cursos actuales
