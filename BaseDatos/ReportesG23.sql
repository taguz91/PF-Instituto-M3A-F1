--Consultamos los datos de un docente en un curso
SELECT  carrera_nombre, curso_nombre, materia_nombre, materia_ciclo,
persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' || persona_segundo_apellido AS Nombre
FROM public."Cursos" c, public."Docentes" d, public."Personas" p, public."Materias" m,
public."PeriodoLectivo" pl, public."Carreras" cr
WHERE d.id_docente = c.id_docente
AND p.id_persona = d.id_persona
AND m.id_materia = c.id_materia
AND pl.id_prd_lectivo = c.id_prd_lectivo
AND cr.id_carrera = pl.id_carrera;

--Consultamos todos los alumnos en un curso
SELECT persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||
persona_primer_apellido || ' ' || persona_segundo_apellido AS Nombre
FROM public."AlumnoCurso" ac, public."Alumnos" a, public."Personas" p
WHERE ac.id_alumno = a.id_alumno
AND p.id_persona = a.id_persona
AND id_curso = 634; --Se le pasaria el curso como parametro


//Selecciona en que carrera se encuentra el estudiante   //Lista de Alumnos por carrera 

select c.carrera_nombre,
p.persona_primer_apellido ||' '|| p.persona_segundo_apellido
||' '||p.persona_primer_nombre ||' '|| p.persona_segundo_nombre
as Alumno,p.persona_identificacion,p.persona_correo,p.persona_telefono
from ((public."AlumnosCarrera" a join public."Alumnos" al using(id_alumno))
join  public."Personas" p using(id_persona))join  public."Carreras" c using(id_carrera)
where a.id_carrera=2 order by Alumno;

//Select para el reporte de personas.

SELECT p.id_persona,p.persona_foto, p.persona_identificacion, 
p.persona_primer_apellido ||' '|| p.persona_segundo_apellido
 ||' '|| p.persona_primer_nombre ||' '|| 
 p.persona_segundo_nombre as persona, p.persona_genero, 
 p.persona_sexo, p.persona_estado_civil, 
 p.persona_etnia, p.persona_idioma_raiz, p.persona_tipo_sangre, p.persona_telefono, 
 p.persona_celular, p.persona_correo, p.persona_fecha_registro, p.persona_discapacidad,
 p.persona_tipo_discapacidad, p.persona_porcenta_discapacidad, p.persona_carnet_conadis, 
 p.persona_calle_principal, p.persona_numero_casa, p.persona_calle_secundaria, 
 p.persona_referencia, p.persona_sector, p.persona_idioma, p.persona_tipo_residencia, 
 p.persona_fecha_nacimiento,p.persona_activa,l.lugar_nombre as LugarNatal,
 z.lugar_nombre as lugarResidencia
FROM (public."Personas" p join public."Lugares" l on p.id_lugar_natal=l.id_lugar)join 
public."Lugares" z on p.id_lugar_residencia=z.id_lugar
where persona_identificacion= $(cedula);

---Reporte de Docente----

SELECT d.docente_fecha_contrato,d.docente_tipo_tiempo,
p.persona_primer_nombre || ' ' || p.persona_segundo_nombre || ' ' ||
p.persona_primer_apellido || ' ' || p.persona_segundo_apellido AS docente,
p.persona_foto, p.persona_celular,p.persona_correo
	FROM public."Docentes" d join public."Personas" p using(id_persona);