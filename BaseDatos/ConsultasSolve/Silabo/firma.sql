SELECT CONCAT(
  d.docente_abreviatura,
  ' ',
  p.persona_primer_apellido,
  ' ',
  p.persona_primer_nombre
) as nombreCoordinador
FROM public."Silabo" s
JOIN public."PeriodoLectivo" pl ON pl.id_prd_lectivo = s.id_prd_lectivo
JOIN public."Docentes" d ON d.id_docente = pl.prd_lectivo_coordinador
JOIN public."Personas" p ON p.id_persona = d.id_persona
WHERE s.id_silabo =
ORDER BY prd_lectivo_fecha_fin


SELECT ca.carrera_nombre,
CONCAT(
  d.docente_abreviatura,
  ' ',
  p.persona_primer_nombre,
  ' ',
  p.persona_primer_apellido,
  ' ',
  p.persona_segundo_apellido
) AS apellidos
FROM public."Carreras" ca
JOIN public."PeriodoLectivo" pl ON pl.id_carrera = ca.id_carrera
JOIN public."Docentes" d ON d.id_docente = ca.id_docente_coordinador
JOIN public."Personas" p ON p.id_persona = d.id_persona
WHERE ca.id_carrera = $P!{id_carrera};


--

select p.persona_identificacion,concat(p.persona_primer_apellido,' ',p.persona_segundo_apellido) as apellidos,
	concat(p.persona_primer_nombre,' ',p.persona_segundo_nombre) as nombres
	from "Notas" n join "TipoDeNota" tn on n.id_tipo_nota=tn.id_tipo_nota join
	"AlumnoCurso" ac on ac.id_almn_curso=n.id_almn_curso join "Cursos" c ON c.id_curso=ac.id_curso
	join "Alumnos"  a on ac.id_alumno=a.id_alumno join "Personas" p on p.id_persona=a.id_persona
	where tn.tipo_nota_nombre = 'G. DE AULA 1'and  ac.almn_curso_asistencia='Asiste' and ac.almn_curso_activo='true'
	AND C.id_curso=$P!{id_curso} and  ROUND(((ROUND(n.nota_valor,1)*100)/30),1)<70
	order by concat(p.persona_primer_apellido,' ',p.persona_segundo_apellido)



-- Coordinador encargado
SELECT CONCAT (
a.docente_abreviatura,
' ',
p.persona_primer_apellido,
' ',
p.persona_primer_nombre
) as nombreCoordinador ,

CASE WHEN p.persona_identificacion = '0302298138' THEN
'COORDINADOR/A (E) DE LA CARRERA'
ELSE 'COORDINADOR/A DE LA CARRERA'
END AS coordinador_carrera

FROM "Carreras" c , "Docentes" a, "Personas" p
WHERE c.id_docente_coordinador = a.id_docente
AND a.id_persona = p.id_persona
AND C.id_carrera = $P{ID_CARR};
