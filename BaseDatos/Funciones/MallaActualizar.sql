SELECT id_alumno
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE ac.id_curso = c.id_curso AND
c.id_prd_lectivo = 4

SELECT id_materia
FROM public."AlumnoCurso" ac, public."Cursos" c
WHERE ac.id_curso = c.id_curso AND
c.id_prd_lectivo = 4

SELECT id_almn_carrera
FROM public."AlumnosCarrera"
WHERE id_alumno IN (
	SELECT id_alumno
	FROM public."AlumnoCurso" ac, public."Cursos" c
	WHERE ac.id_curso = c.id_curso AND
	c.id_prd_lectivo = 4
) AND id_carrera = (
	SELECT id_carrera
	FROM public."PeriodoLectivo"
	WHERE id_prd_lectivo = 4
)

--Consultamos los estados de las malla a actualizar


SELECT malla_almn_estado, malla_almn_num_matricula,
persona_primer_nombre, persona_segundo_nombre,
persona_primer_apellido, persona_segundo_apellido,
materia_nombre, materia_ciclo, malla_almn_nota1
FROM public."MallaAlumno" ma, public."AlumnosCarrera" ac,
public."Alumnos" a, public."Personas" p, public."Materias" m
WHERE ma.id_almn_carrera IN (
    SELECT id_almn_carrera
    FROM public."AlumnosCarrera"
    WHERE id_alumno IN (
        SELECT id_alumno
        FROM public."AlumnoCurso" ac, public."Cursos" c
        WHERE ac.id_curso = c.id_curso AND
        c.id_prd_lectivo = 4
        ) AND id_carrera = (
            SELECT id_carrera
            FROM public."PeriodoLectivo"
            WHERE id_prd_lectivo = 4
        )
	) AND ma.id_materia IN (
		SELECT id_materia
		FROM public."AlumnoCurso" ac, public."Cursos" c
		WHERE ac.id_curso = c.id_curso AND
		c.id_prd_lectivo = 4
) AND malla_almn_estado = 'R'
AND ac.id_almn_carrera = ma.id_almn_carrera
AND a.id_alumno = ac.id_alumno
AND p.id_persona = a.id_persona
AND m.id_materia = ma.id_materia
ORDER BY persona_primer_apellido;

SELECT id_malla_alumno
FROM public."MallaAlumno"
WHERE id_almn_carrera IN (
		SELECT id_almn_carrera
		FROM public."AlumnosCarrera"
		WHERE id_alumno IN (
			SELECT id_alumno
			FROM public."AlumnoCurso" ac, public."Cursos" c
			WHERE ac.id_curso = c.id_curso AND
			c.id_prd_lectivo = 4
		) AND id_carrera = (
			SELECT id_carrera
			FROM public."PeriodoLectivo"
			WHERE id_prd_lectivo = 4
		)
	) AND id_materia IN (
		SELECT id_materia
		FROM public."AlumnoCurso" ac, public."Cursos" c
		WHERE ac.id_curso = c.id_curso AND
		c.id_prd_lectivo = 4
	) AND malla_almn_estado = 'R';


--Actualizamos de nuestra carrera
UPDATE public."MallaAlumno"
	SET malla_almn_estado = 'M', malla_almn_num_matricula = malla_almn_num_matricula + 1
	WHERE id_almn_carrera IN (
		SELECT id_almn_carrera
		FROM public."AlumnosCarrera"
		WHERE id_alumno IN (
			SELECT id_alumno
			FROM public."AlumnoCurso" ac, public."Cursos" c
			WHERE ac.id_curso = c.id_curso AND
			c.id_prd_lectivo = 4
		) AND id_carrera = (
			SELECT id_carrera
			FROM public."PeriodoLectivo"
			WHERE id_prd_lectivo = 4
		)
	) AND id_materia IN (
		SELECT id_materia
		FROM public."AlumnoCurso" ac, public."Cursos" c
		WHERE ac.id_curso = c.id_curso AND
		c.id_prd_lectivo = 4
	) AND malla_almn_estado = 'R';

--107 actualizados  en desarrollo en el ultimo periodo
--Para TAS
	UPDATE public."MallaAlumno"
	SET malla_almn_estado = 'M', malla_almn_num_matricula = malla_almn_num_matricula + 1
	WHERE id_almn_carrera IN (
		SELECT id_almn_carrera
		FROM public."AlumnosCarrera"
		WHERE id_alumno IN (
			SELECT id_alumno
			FROM public."AlumnoCurso" ac, public."Cursos" c
			WHERE ac.id_curso = c.id_curso AND
			c.id_prd_lectivo = 8
		) AND id_carrera = (
			SELECT id_carrera
			FROM public."PeriodoLectivo"
			WHERE id_prd_lectivo = 8
		)
	) AND id_materia IN (
		SELECT id_materia
		FROM public."AlumnoCurso" ac, public."Cursos" c
		WHERE ac.id_curso = c.id_curso AND
		c.id_prd_lectivo = 8
	) AND malla_almn_estado = 'R';

--154 actualizados en sistemas en el ultimo periodo

--Actualizamos el numero dematricula si esta 0  
--4 es de SDS
--8 es de TAS
--9 es de Entrenamiento 

UPDATE public."MallaAlumno"
SET malla_almn_num_matricula = malla_almn_num_matricula + 1
WHERE id_almn_carrera IN (
	SELECT id_almn_carrera
	FROM public."AlumnosCarrera"
	WHERE id_alumno IN (
		SELECT id_alumno
		FROM public."AlumnoCurso" ac, public."Cursos" c
		WHERE ac.id_curso = c.id_curso AND
		c.id_prd_lectivo = 4
	) AND id_carrera = (
		SELECT id_carrera
		FROM public."PeriodoLectivo"
		WHERE id_prd_lectivo = 4
	)
) AND id_materia IN (
	SELECT id_materia
	FROM public."AlumnoCurso" ac, public."Cursos" c
	WHERE ac.id_curso = c.id_curso AND
	c.id_prd_lectivo = 4
) AND malla_almn_estado = 'M' AND 
malla_almn_num_matricula = 0;

---TAS


UPDATE public."MallaAlumno"
SET malla_almn_num_matricula = malla_almn_num_matricula + 1
WHERE id_almn_carrera IN (
	SELECT id_almn_carrera
	FROM public."AlumnosCarrera"
	WHERE id_alumno IN (
		SELECT id_alumno
		FROM public."AlumnoCurso" ac, public."Cursos" c
		WHERE ac.id_curso = c.id_curso AND
		c.id_prd_lectivo = 8
	) AND id_carrera = (
		SELECT id_carrera
		FROM public."PeriodoLectivo"
		WHERE id_prd_lectivo = 8
	)
) AND id_materia IN (
	SELECT id_materia
	FROM public."AlumnoCurso" ac, public."Cursos" c
	WHERE ac.id_curso = c.id_curso AND
	c.id_prd_lectivo = 8
) AND malla_almn_estado = 'M' AND 
malla_almn_num_matricula = 0;