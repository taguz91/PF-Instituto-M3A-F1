
--Reasignación de Materias al nuevo Docente
CREATE OR REPLACE FUNCTION reasignarMaterias(curso_Old integer, curso_New integer)
RETURNS VOID AS $reasignarMaterias$
DECLARE

    total INTEGER := 0;
    ingresados INTEGER := 0;

    reg RECORD;
    backup_Alumno_Curso CURSOR FOR SELECT id_almn_curso, id_alumno, id_curso, almn_curso_asistencia, almn_curso_nota_final,
    almn_curso_num_faltas, almn_curso_estado FROM public."AlumnoCurso" WHERE id_curso = curso_Old;

BEGIN

    SELECT count(*) INTO total FROM public."AlumnoCurso"
    WHERE id_curso = curso_Old;

    OPEN backup_Alumno_Curso;
    FETCH backup_Alumno_Curso INTO reg;

    WHILE (FOUND) LOOP
        ingresados := ingresados + 1;
        INSERT INTO public."AlumnoCurso" (id_alumno, id_curso, almn_curso_asistencia, almn_curso_nota_final,
        almn_curso_estado, almn_curso_num_faltas, almn_curso_fecha_registro, almn_curso_activo)
        VALUES (reg.id_alumno, curso_New, reg.almn_curso_asistencia, reg.almn_curso_nota_final,
        reg.almn_curso_estado, reg.almn_curso_num_faltas, now(), true);
        FETCH backup_Alumno_Curso Into reg;
    END LOOP;
    RAISE NOTICE 'Total de ingresados: % Todos se ingreasaron: %', ingresados, total = ingresados;
    RETURN;
    CLOSE backup_Alumno_Curso;
END;
$reasignarMaterias$ LANGUAGE plpgsql;

--Reasignación de Notas al nuevo Docente
CREATE OR REPLACE FUNCTION reasignarNotas(curso_Old integer, curso_New integer)
RETURNS VOID AS $reasignarNotas$
DECLARE

    total INTEGER := 0;
    ingresados INTEGER := 0;

    reg RECORD;
    reg_2 RECORD;

    backup_Notas_Old CURSOR FOR SELECT a.id_curso, a.almn_curso_estado, n.nota_valor, n.id_almn_curso,
    n.id_nota, n.id_tipo_nota FROM public."AlumnoCurso" a JOIN public."Notas" n USING(id_almn_curso)
    WHERE id_curso = curso_Old;
    backup_Notas_New CURSOR FOR SELECT a.id_curso, a.almn_curso_estado, n.nota_valor, n.id_almn_curso,
    n.id_nota, n.id_tipo_nota FROM public."AlumnoCurso" a JOIN public."Notas" n USING(id_almn_curso)
    WHERE id_curso = curso_New;

BEGIN

    SELECT count(*) INTO total FROM public."AlumnoCurso" a JOIN public."Notas" n USING(id_almn_curso)
    WHERE id_curso = curso_Old;

    OPEN backup_Notas_Old;
    FETCH backup_Notas_Old INTO reg;
    OPEN backup_Notas_New;
    FETCH backup_Notas_New INTO reg_2;

    WHILE (FOUND) LOOP
        ingresados := ingresados + 1;
        UPDATE public."Notas" SET nota_valor = reg.nota_valor, id_tipo_nota = reg.id_tipo_nota
        WHERE id_nota = reg_2.id_nota;
        FETCH backup_Notas_Old INTO reg;
        FETCH backup_Notas_New INTO reg_2;
    END LOOP;
    RAISE NOTICE 'Total de ingresados: % Todos se ingreasaron: %', ingresados, total = ingresados;
    RETURN;
    CLOSE backup_Notas_Old;
    CLOSE backup_Notas_New;
END;
$reasignarNotas$ LANGUAGE plpgsql;