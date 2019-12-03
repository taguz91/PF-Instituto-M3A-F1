SELECT
prd_lectivo_nombre AS "Periodo Lectivo",
(SELECT count(*)
 FROM public."Matricula" m, public."Alumnos" a, public."Personas" p
 WHERE m.id_prd_lectivo = pl.id_prd_lectivo
 AND a.id_alumno = m.id_alumno
 AND p.id_persona = a.id_persona
 AND p.persona_sexo ILIKE '%H%') AS "Hombres",
 (SELECT count(*)
 FROM public."Matricula" m, public."Alumnos" a, public."Personas" p
 WHERE m.id_prd_lectivo = pl.id_prd_lectivo
 AND a.id_alumno = m.id_alumno
 AND p.id_persona = a.id_persona
 AND p.persona_sexo ILIKE '%M%') AS "Mujeres",
 (SELECT count(*)
 FROM public."Matricula"
 WHERE id_prd_lectivo = pl.id_prd_lectivo) AS "Total Matriculados"

FROM public."PeriodoLectivo" pl
WHERE pl.id_prd_lectivo IN (21, 22, 23, 24, 25, 26, 27, 28, 29)
ORDER BY id_prd_lectivo ;

Copy(
  --Insertar consulta aqui
)
To 'C:\Backups Postgresql\matriculas.csv' with CSV HEADER;
