SELECT 1
FROM public."Asistencia"
WHERE id_almn_curso IN (
  SELECT id_almn_curso
  FROM public."AlumnoCurso"
  WHERE id_curso = :idCurso
) AND fecha_asistencia = :fecha;


INSERT INTO public."Asistencia"(
id_almn_curso,
fecha_asistencia,
numero_faltas )
VALUES (
1,
'04/12/2019',
0
);

--- Actualizamos

UPDATE public."Asistencia"
SET numero_faltas = :numFaltas
WHERE id_almn_curso = :idAlumno
AND fecha_asistencia = :fecha;

-- Peticion JSON

[
{
"fecha": "12/06/2019",
"id_curos": 1 ,
"alumnos": [
{
"id_almn_curso": 12,
"fecha": "12/06/2019",
"horas": 2
}
]
}
]
