SELECT DISTINCT hi,
hora,
string_agg(lu,'') as Lunes,
string_agg(ma,'') as Martes,
string_agg(mi,'') as Miércoles,
string_agg(ju,'') as Jueves,
string_agg(vi,'') as Viernes,
string_agg(sa,'') as S FROM(
  SELECT  g.hora_inicio_sesion as hi,
  CONCAT(
    extract(hour FROM g.hora_inicio_sesion),
    ':',
    extract(minute FROM g.hora_inicio_sesion),
    ' a ',
    WHEN extract(hour FROM g.hora_fin_sesion) = '0' THEN '00'
    ELSE extract(hour FROM g.hora_fin_sesion)
    END,
    ':',
    extract(minute FROM g.hora_fin_sesion)
  ) as hora,
CASE WHEN g.dia_sesion = 1 THEN
CONCAT(
  substr(a.nombre_jornada,0,2),
  c.curso_ciclo,
  c.curso_paralelo
) END as "lu",
NULL AS ma,
NULL AS mi,
NULL AS ju,
NULL AS vi,
NULL AS sa
FROM "SesionClase" g
JOIN "Cursos"  c ON g.id_curso = c.id_curso
JOIN "Jornadas" a on c.id_jornada = a.id_jornada
WHERE c.id_materia=$P!{id_materia}
AND c.id_prd_lectivo = (
  SELECT id_prd_lectivo
  FROM "Silabo" WHERE id_silabo = $P!{id_silabo}
)
UNION
SELECT  g.hora_inicio_sesion AS hi,
CONCAT(
  extract(hour FROM g.hora_inicio_sesion),
  ':',
  extract(minute FROM g.hora_inicio_sesion),
  ' a ',
  extract(hour FROM g.hora_fin_sesion),
  ':',
  extract(minute FROM g.hora_fin_sesion)
) AS hora,
'' AS lu,
CASE WHEN g.dia_sesion = 2 THEN
	CONCAT(
    substr(a.nombre_jornada,0,2),
    c.curso_ciclo,
    c.curso_paralelo
  ) END AS ma,
NULL AS mi,
NULL AS ju,
NULL AS vi,
NULL AS sa
FROM "SesionClase" g
JOIN "Cursos"  c on g.id_curso = c.id_curso
JOIN "Jornadas" a on c.id_jornada = a.id_jornada
WHERE c.id_materia = $P!{id_materia}
AND c.id_prd_lectivo = (
  SELECT id_prd_lectivo FROM "Silabo" WHERE id_silabo=$P!{id_silabo}
)
UNION
SELECT  g.hora_inicio_sesion as hi,
CONCAT(
  extract(hour FROM g.hora_inicio_sesion),
  ':',
  extract(minute FROM g.hora_inicio_sesion),
  ' a ',
  extract(hour FROM g.hora_fin_sesion),
  ':',
  extract(minute FROM g.hora_fin_sesion)
) AS hora,
'' AS lu,
'' AS ma,
CASE WHEN g.dia_sesion = 3 THEN
	CONCAT(
    substr(a.nombre_jornada,0,2),
    c.curso_ciclo,
    c.curso_paralelo
  ) END AS mi,
  NULL AS ju,
  NULL AS vi,
  NULL AS sa
FROM "SesionClase" g
JOIN "Cursos"  c on g.id_curso = c.id_curso
JOIN "Jornadas" a on c.id_jornada=a.id_jornada
WHERE c.id_materia=$P!{id_materia}
AND c.id_prd_lectivo=(
  SELECT id_prd_lectivo
  FROM "Silabo" WHERE id_silabo=$P!{id_silabo}
)
UNION
SELECT
g.hora_inicio_sesion AS hi,
CONCAT(
  extract(hour FROM g.hora_inicio_sesion),
  ':',
  extract(minute FROM g.hora_inicio_sesion),
  ' a ',
  extract(hour FROM g.hora_fin_sesion),
  ':',
  extract(minute FROM g.hora_fin_sesion)
) AS hora,
'' AS lu,
'' AS ma,
'' AS mi,
CASE WHEN g.dia_sesion = 4 THEN
	CONCAT(
    substr(a.nombre_jornada,0,2),
    c.curso_ciclo,
    c.curso_paralelo
  ) END AS ju,
NULL AS vi,
NULL AS sa
FROM "SesionClase" g
JOIN "Cursos"  c on g.id_curso = c.id_curso
JOIN "Jornadas" a on c.id_jornada = a.id_jornada
WHERE c.id_materia = $P!{id_materia}
AND c.id_prd_lectivo = (
  SELECT id_prd_lectivo FROM "Silabo" WHERE id_silabo=$P!{id_silabo}
)
UNION
SELECT
g.hora_inicio_sesion as hi,
CONCAT(
  extract(hour FROM g.hora_inicio_sesion),
  ':',
  extract(minute FROM g.hora_inicio_sesion),
  ' a ',
  extract(hour FROM g.hora_fin_sesion),
  ':',
  extract(minute FROM g.hora_fin_sesion)
) AS hora,
'' AS lu,
'' AS ma,
'' AS mi,
'' AS ju,
CASE WHEN g.dia_sesion = 5 THEN
	CONCAT(
    substr(a.nombre_jornada,0,2),
    c.curso_ciclo,
    c.curso_paralelo
  ) END AS vi,
NULL AS sa
FROM "SesionClase" g
JOIN "Cursos"  c on g.id_curso = c.id_curso
JOIN "Jornadas" a on c.id_jornada = a.id_jornada
WHERE c.id_materia = $P!{id_materia}
AND c.id_prd_lectivo = (
  SELECT id_prd_lectivo
  FROM "Silabo" WHERE id_silabo=$P!{id_silabo}
)
UNION
SELECT
g.hora_inicio_sesion as hi,
CONCAT(
  extract(hour FROM g.hora_inicio_sesion),
  ':',
  extract(minute FROM g.hora_inicio_sesion),
  ' a ',
  extract(hour FROM g.hora_fin_sesion),
  ':',
  extract(minute FROM g.hora_fin_sesion)
) AS hora,
'' AS lu,
'' AS ma,
'' AS mi,
'' AS ju,
'' AS vi,
	CASE WHEN g.dia_sesion = 6 THEN
		CONCAT(
      substr(a.nombre_jornada,0,2),
      c.curso_ciclo,
      c.curso_paralelo
    ) ELSE ' ' END AS  sa
FROM "SesionClase" g
JOIN "Cursos"  c on g.id_curso = c.id_curso
JOIN "Jornadas" a on c.id_jornada = a.id_jornada
WHERE c.id_materia = $P!{id_materia}
AND c.id_prd_lectivo = (
  SELECT id_prd_lectivo
  FROM "Silabo" WHERE id_silabo=$P!{id_silabo}
)
) AS x
GROUP BY hora, hi
ORDER BY hi

/*
Reemplzado esto extract(minute FROM g.hora_fin_sesion) por esto
CASE WHEN extract(minute FROM g.hora_fin_sesion) = '0' THEN '00'
ELSE extract(minute FROM g.hora_fin_sesion)
END
*/


/*
Reemplzado esto extract(minute FROM g.hora_inicio_sesion) por esto
CASE WHEN extract(minute FROM g.hora_inicio_sesion) = '0' THEN '00'
ELSE extract(minute FROM g.hora_inicio_sesion)
END
*/
