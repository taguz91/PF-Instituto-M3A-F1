--Alters
ALTER TABLE public."MallaAlumno" ALTER COLUMN "malla_almn_nota1" TYPE numeric(6, 2);
ALTER TABLE public."MallaAlumno" ALTER COLUMN "malla_almn_nota2" TYPE numeric(6, 2);
ALTER TABLE public."MallaAlumno" ALTER COLUMN "malla_almn_nota3" TYPE numeric(6, 2);

--Alters
ALTER TABLE public."AlumnoCurso" ALTER COLUMN "almn_curso_nt_1_parcial" TYPE numeric(6, 2);
ALTER TABLE public."AlumnoCurso" ALTER COLUMN "almn_curso_nt_examen_interciclo" TYPE numeric(6, 2);
ALTER TABLE public."AlumnoCurso" ALTER COLUMN "almn_curso_nt_2_parcial" TYPE numeric(6, 2);
ALTER TABLE public."AlumnoCurso" ALTER COLUMN "almn_curso_nt_examen_final" TYPE numeric(6, 2);
ALTER TABLE public."AlumnoCurso" ALTER COLUMN "almn_curso_nt_examen_supletorio" TYPE numeric(6, 2);
ALTER TABLE public."AlumnoCurso" ALTER COLUMN "almn_curso_nota_final" TYPE numeric(6, 2);
