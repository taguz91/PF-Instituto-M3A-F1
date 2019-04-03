--Tabla en la que se almacenaran los datos
CREATE TABLE "MallaAlumnoBackup"(
	"id_malla_alumno" integer NOT NULL,
	"id_materia" integer NOT NULL,
	"id_almn_carrera" integer NOT NULL,
	"malla_almn_ciclo" integer NOT NULL,
	"malla_almn_num_matricula" integer NOT NULL DEFAULT '0',
	"malla_almn_nota1" numeric(6, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota2" numeric(6, 2) NOT NULL DEFAULT '0',
	"malla_almn_nota3" numeric(6, 2) NOT NULL DEFAULT '0',
	"malla_almn_estado" character varying(1) NOT NULL DEFAULT 'P',
	"malla_alm_observacion" character varying(200),
  "id_prd_lectivo" integer NOT NULL,
  "fecha_cierre" TIMESTAMP DEFAULT now()
) WITH (OIDS = FALSE);
/*
  El trigger mas genial de la vida, es cuando se sierra un periodo
  consultaremos todos los alumnos de ese perido lectivo que estuvieron matriculados
  y actualizaremos sus datos
*/

CREATE OR REPLACE FUNCTION cierre_prd_backup()
RETURNS TRIGGER AS $cierre_prd_backup$
--Declaramos un cursor
BEGIN
	IF new.prd_lectivo_estado = FALSE THEN
	 --Buscamos todos los alumnos para insertarlos en la tabla copia
   INSERT INTO public."MallaAlumnoBackup"(
  	id_malla_alumno, id_materia, id_almn_carrera, malla_almn_ciclo,
  	malla_almn_num_matricula, malla_almn_nota1,
  	malla_almn_nota2, malla_almn_nota3, malla_almn_estado,
  	malla_alm_observacion, id_prd_lectivo)
    SELECT id_malla_alumno, id_materia, ma.id_almn_carrera,
    malla_almn_ciclo, malla_almn_num_matricula,
    malla_almn_nota1, malla_almn_nota2,
    malla_almn_nota3, malla_almn_estado, malla_alm_observacion, old.id_prd_lectivo
    FROM public."MallaAlumno" ma, public."AlumnosCarrera" ac
    WHERE ac.id_carrera = old.id_carrera
    AND ma.id_almn_carrera = ac.id_almn_carrera
    AND malla_almn_estado = 'M';
	END IF;
	RETURN NEW;
END;
$cierre_prd_backup$ LANGUAGE plpgsql;


CREATE TRIGGER cierre_prd_backup
BEFORE UPDATE OF prd_lectivo_estado
ON public."PeriodoLectivo" FOR EACH ROW
EXECUTE PROCEDURE cierre_prd_backup();

--Luego de cerrar el periodo se le consulta las notas

--Una funcion que me llena las notas *
--Solo debo pasar el id del periodo
CREATE OR REPLACE FUNCTION llenar_cursos()
RETURNS VOID AS $llenar_cursos$
DECLARE
  materia INTEGER := 0;
  reg RECORD;
  cursos_prd CURSOR FOR SELECT id_curso, id_materia , c.id_prd_lectivo, id_carrera
  FROM public."Cursos" c, public."PeriodoLectivo" pl
  WHERE
  c.id_prd_lectivo = 2  AND
  pl.id_prd_lectivo = c.id_prd_lectivo ORDER BY id_materia;
BEGIN
  OPEN cursos_prd;
  FETCH cursos_prd INTO reg;
  WHILE ( FOUND ) LOOP
    RAISE NOTICE 'Curso: %', reg.id_curso;
    RAISE NOTICE 'Materia: %', reg.id_materia;
    IF materia <> reg.id_materia THEN
        materia := reg.id_materia;
        INSERT INTO public."AlumnoCurso"(
        id_alumno, id_curso, almn_curso_nota_final)
        SELECT id_alumno, reg.id_curso, floor(random()* (90-25 + 1) + 25)
        FROM public."MallaAlumno" ma, public."AlumnosCarrera" ac
        WHERE ac.id_carrera = reg.id_carrera AND
        ma.id_almn_carrera = ac.id_almn_carrera AND
        ma.id_materia = reg.id_materia AND
        malla_almn_estado = 'M';
        RAISE NOTICE 'SE INSERTO';
    ELSE
      RAISE NOTICE 'Ya se registro en esta materia: %', materia;
    END IF;
    FETCH cursos_prd INTO reg;
  END LOOP;
  RETURN;
END;
$llenar_cursos$ LANGUAGE plpgsql;

--Ahora al cerrar el periodo se migra las notas

--Creamos un trigger
CREATE OR REPLACE FUNCTION pasar_notas()
RETURNS TRIGGER AS $pasar_notas$
DECLARE
  total INTEGER := 0;
  ingresados INTEGER := 0;
  num_matricula INTEGER := 0;
  estado character varying(10) := 'S';

  reg RECORD;
  almn_curso CURSOR FOR  SELECT id_alumno, id_materia, almn_curso_nota_final, id_carrera
  FROM public."AlumnoCurso" ac, public."Cursos" c,
  public."PeriodoLectivo" pl
  WHERE c.id_prd_lectivo = old.id_prd_lectivo AND
  ac.id_curso = c.id_curso AND
  pl.id_prd_lectivo = c.id_prd_lectivo;

BEGIN
  IF new.prd_lectivo_estado = FALSE THEN
--Comprobamos el total de registros que se deberian hacer
    SELECT count(*) INTO total
    FROM public."AlumnoCurso" ac, public."Cursos" c
    WHERE c.id_prd_lectivo = 2 AND
    ac.id_curso = c.id_curso;
    RAISE NOTICE 'Total de registros: %', total;
    OPEN almn_curso;
    FETCH almn_curso INTO reg;
    WHILE ( FOUND ) LOOP
      ingresados := ingresados + 1;
      --Consultamos el numero de matricula
      SELECT malla_almn_num_matricula INTO num_matricula
    	FROM public."MallaAlumno"
    	WHERE id_materia = reg.id_materia
    	AND id_almn_carrera = (
    		SELECT id_almn_carrera
    		FROM public."AlumnosCarrera"
    		WHERE id_carrera = reg.id_carrera AND id_alumno = reg.id_alumno
    	);
      RAISE NOTICE 'Numero de matricula : % de %', num_matricula, reg.id_alumno;
      --Revisamos como sera el estado
      IF reg.almn_curso_nota_final >= 70 THEN
        estado := 'C';
      ELSE
        estado := 'R';
      END IF;
      RAISE NOTICE 'Curso o reprobo : % Nota: %', estado, reg.almn_curso_nota_final;

      IF num_matricula = 1 THEN
      UPDATE public."MallaAlumno"
          SET  malla_almn_nota1 = reg.almn_curso_nota_final, malla_almn_estado=estado
          WHERE id_materia = reg.id_materia
          AND id_almn_carrera = (
            SELECT id_almn_carrera
            FROM public."AlumnosCarrera"
            WHERE id_carrera = reg.id_carrera AND id_alumno = reg.id_alumno
          );
      ELSIF num_matricula = 2 THEN
      UPDATE public."MallaAlumno"
      SET malla_almn_nota2= reg.almn_curso_nota_final, malla_almn_estado=estado
      WHERE id_materia = reg.id_materia
      AND id_almn_carrera = (
        SELECT id_almn_carrera
        FROM public."AlumnosCarrera"
        WHERE id_carrera = reg.id_carrera AND id_alumno = reg.id_alumno
      );
      ELSE
      UPDATE public."MallaAlumno"
      SET  malla_almn_nota3 = reg.almn_curso_nota_final, malla_almn_estado=estado
      WHERE id_materia = reg.id_materia
      AND id_almn_carrera = (
        SELECT id_almn_carrera
        FROM public."AlumnosCarrera"
        WHERE id_carrera = reg.id_carrera AND id_alumno = reg.id_alumno
      );
      END IF;

      FETCH almn_curso INTO reg;
    END LOOP;
    RAISE NOTICE 'Total de ingresados: %', ingresados;
  END IF;
  RETURN NEW;
END;
$pasar_notas$ LANGUAGE plpgsql;

CREATE TRIGGER pasar_notas_malla
AFTER UPDATE OF prd_lectivo_estado
ON public."PeriodoLectivo" FOR EACH ROW
EXECUTE PROCEDURE pasar_notas();
