select distinct hi,hora,string_agg(lu,'') as Lunes,string_agg(ma,'') as Martes,string_agg(mi,'') as Miércoles,string_agg(ju,'') as Jueves,string_agg(vi,'') as Viernes,string_agg(sa,'') as S from(
select  g.hora_inicio_sesion as hi,CONCAT(extract(hour from g.hora_inicio_sesion),':',extract(minute from g.hora_inicio_sesion),' a ', extract(hour from g.hora_fin_sesion),':',extract(minute from g.hora_fin_sesion)) as hora,
			case when g.dia_sesion=1 then
				concat(substr(a.nombre_jornada,0,2),c.curso_ciclo,c.curso_paralelo) end
				 as "lu",null as ma,null as mi,null as ju,null as vi,null as sa
from "SesionClase" g join "Cursos"  c on g.id_curso=c.id_curso join "Jornadas" a on c.id_jornada=a.id_jornada
where     c.id_materia=$P!{id_materia} and c.id_prd_lectivo=(select id_prd_lectivo from "Silabo" where id_silabo=5133)
union
select  g.hora_inicio_sesion as hi,CONCAT(extract(hour from g.hora_inicio_sesion),':',extract(minute from g.hora_inicio_sesion),' a ', extract(hour from g.hora_fin_sesion),':',extract(minute from g.hora_fin_sesion)) as hora,
			'' as lu,
			case when g.dia_sesion=2 then
				concat(substr(a.nombre_jornada,0,2),c.curso_ciclo,c.curso_paralelo) end
				 as ma,null as mi,null as ju,null as vi,null as sa
from "SesionClase" g join "Cursos"  c on g.id_curso=c.id_curso join "Jornadas" a on c.id_jornada=a.id_jornada
where      c.id_materia=$P!{id_materia} and c.id_prd_lectivo=(select id_prd_lectivo from "Silabo" where id_silabo=5133)
	union
select  g.hora_inicio_sesion as hi, CONCAT(extract(hour from g.hora_inicio_sesion),':',extract(minute from g.hora_inicio_sesion),' a ', extract(hour from g.hora_fin_sesion),':',extract(minute from g.hora_fin_sesion)) as hora,
			'' as lu,
	        '' as ma,
			case when g.dia_sesion=3 then
				concat(substr(a.nombre_jornada,0,2),c.curso_ciclo,c.curso_paralelo) end
				 as mi,null as ju,null as vi,null as sa
from "SesionClase" g join "Cursos"  c on g.id_curso=c.id_curso join "Jornadas" a on c.id_jornada=a.id_jornada
where     c.id_materia=$P!{id_materia} and c.id_prd_lectivo=(select id_prd_lectivo from "Silabo" where id_silabo=5133)
	union
select  g.hora_inicio_sesion as hi,CONCAT(extract(hour from g.hora_inicio_sesion),':',extract(minute from g.hora_inicio_sesion),' a ', extract(hour from g.hora_fin_sesion),':',extract(minute from g.hora_fin_sesion)) as hora,
			'' as lu,
	        '' as ma,
	        '' as mi,
			case when g.dia_sesion=4 then
				concat(substr(a.nombre_jornada,0,2),c.curso_ciclo,c.curso_paralelo) end
				 as ju,null as vi,null as sa
from "SesionClase" g join "Cursos"  c on g.id_curso=c.id_curso join "Jornadas" a on c.id_jornada=a.id_jornada
where     c.id_materia=$P!{id_materia} and c.id_prd_lectivo=(select id_prd_lectivo from "Silabo" where id_silabo=5133)
	union
select  g.hora_inicio_sesion as hi, CONCAT(extract(hour from g.hora_inicio_sesion),':',extract(minute from g.hora_inicio_sesion),' a ', extract(hour from g.hora_fin_sesion),':',extract(minute from g.hora_fin_sesion)) as hora,
			'' as lu,
	        '' as ma,
	        '' as mi,
	        '' as ju,
			case when g.dia_sesion=5 then
				concat(substr(a.nombre_jornada,0,2),c.curso_ciclo,c.curso_paralelo) end
				 as vi,null as sa
from "SesionClase" g join "Cursos"  c on g.id_curso=c.id_curso join "Jornadas" a on c.id_jornada=a.id_jornada
where     c.id_materia=$P!{id_materia} and c.id_prd_lectivo=(select id_prd_lectivo from "Silabo" where id_silabo=5133)
	union
select  g.hora_inicio_sesion as hi,CONCAT(extract(hour from g.hora_inicio_sesion),':',extract(minute from g.hora_inicio_sesion),' a ', extract(hour from g.hora_fin_sesion),':',extract(minute from g.hora_fin_sesion)) as hora,
			'' as lu,
	        '' as ma,
	        '' as mi,
	        '' as ju,
	        '' as vi,
			case when g.dia_sesion=6 then
				concat(substr(a.nombre_jornada,0,2),c.curso_ciclo,c.curso_paralelo) else ' ' end
				 as  sa
from "SesionClase" g join "Cursos"  c on g.id_curso=c.id_curso join "Jornadas" a on c.id_jornada=a.id_jornada
where     c.id_materia=$P!{id_materia} and c.id_prd_lectivo=(select id_prd_lectivo from "Silabo" where id_silabo=5133)

) as x
group by hora,hi
order by hi
