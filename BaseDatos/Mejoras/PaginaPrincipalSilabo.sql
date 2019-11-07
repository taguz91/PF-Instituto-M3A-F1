SELECT
CASE m.materia_ciclo
WHEN 1 THEN 'PRIMERO'
WHEN 2 THEN 'SEGUNDO'
WHEN 3 THEN 'TERCERO'
WHEN 4 THEN 'CUARTO'
WHEN 5 THEN 'QUINTO'
WHEN 6 THEN 'SEXTO'
end as ciclo,
c.id_carrera,
m.materia_codigo,
m.materia_nombre,
m.materia_descripcion,
m.materia_objetivo,
m.materia_objetivo_especifico,
m.materia_organizacion_curricular,
m.materia_campo_formacion,
m.materia_horas_docencia,
m.materia_horas_practicas,
m.materia_horas_auto_estudio,
ROUND( CAST(
  CAST(m.materia_horas_docencia AS FLOAT) /
  CAST(c.carrera_semanas AS FLOAT) AS NUMERIC),
1) AS horas_sem_docen,
ROUND ( CAST(
  CAST(m.materia_horas_practicas AS FLOAT) /
  CAST(c.carrera_semanas AS FLOAT)AS NUMERIC),
1)  AS horas_sem_prac,
current_date,
ROUND( CAST(
  CAST(m.materia_horas_auto_estudio AS FLOAT) /
  CAST(c.carrera_semanas AS FLOAT) AS NUMERIC),
1) AS horas_sem_auto,
m.materia_horas_docencia + m.materia_horas_practicas + m.materia_horas_auto_estudio AS materia_total_horas,
c.carrera_nombre,
c.carrera_modalidad,
ROUND( CAST(
  CAST( m.materia_horas_practicas AS FLOAT)/CAST(c.carrera_semanas AS FLOAT) +
  CAST(m.materia_horas_docencia AS FLOAT)/CAST(c.carrera_semanas AS FLOAT) +
  CAST(m.materia_horas_auto_estudio AS FLOAT)/CAST(c.carrera_semanas AS FLOAT)
  AS NUMERIC),
1) AS horas_semanales
FROM "Materias" m,"Carreras" c
WHERE m.id_carrera = c.id_carrera
AND id_materia = $P!{parameter1};


-- Con parametro en semanas

SELECT
CASE m.materia_ciclo
WHEN 1 THEN 'PRIMERO'
WHEN 2 THEN 'SEGUNDO'
WHEN 3 THEN 'TERCERO'
WHEN 4 THEN 'CUARTO'
WHEN 5 THEN 'QUINTO'
WHEN 6 THEN 'SEXTO'
end as ciclo,
c.id_carrera,
m.materia_codigo,
m.materia_nombre,
m.materia_descripcion,
m.materia_objetivo,
m.materia_objetivo_especifico,
m.materia_organizacion_curricular,
m.materia_campo_formacion,
m.materia_horas_docencia,
m.materia_horas_practicas,
m.materia_horas_auto_estudio,
ROUND( CAST(
  CAST(m.materia_horas_docencia AS FLOAT) /
  CAST($P{num_semanas} AS FLOAT) AS NUMERIC),
1) AS horas_sem_docen,
ROUND ( CAST(
  CAST(m.materia_horas_practicas AS FLOAT) /
  CAST($P{num_semanas} AS FLOAT)AS NUMERIC),
1)  AS horas_sem_prac,
current_date,
ROUND( CAST(
  CAST(m.materia_horas_auto_estudio AS FLOAT) /
  CAST($P{num_semanas} AS FLOAT) AS NUMERIC),
1) AS horas_sem_auto,
m.materia_horas_docencia + m.materia_horas_practicas + m.materia_horas_auto_estudio AS materia_total_horas,
c.carrera_nombre,
c.carrera_modalidad,
ROUND( CAST(
  CAST( m.materia_horas_practicas AS FLOAT)/CAST($P{num_semanas} AS FLOAT) +
  CAST(m.materia_horas_docencia AS FLOAT)/CAST($P{num_semanas} AS FLOAT) +
  CAST(m.materia_horas_auto_estudio AS FLOAT)/CAST($P{num_semanas} AS FLOAT)
  AS NUMERIC),
1) AS horas_semanales
FROM "Materias" m,"Carreras" c
WHERE m.id_carrera = c.id_carrera
AND id_materia = $P!{parameter1};
