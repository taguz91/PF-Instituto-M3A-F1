--INSERT DOCENTE: ANDREA PERAL --MATERIA: BASE DE DATOS AVANZADA
INSERT INTO "DocentesMateria"(id_docente, id_materia) 
VALUES(54,54);

--INSERT DOCENTE: SANTIAGO RIOFRIO --MATERIA: CALCULO DIFERENCIAL INTEGRAL
INSERT INTO "DocentesMateria"(id_docente, id_materia) 
VALUES(49,51);

--INSERT DOCENTE: PATRICIO PACHECO --MATERIA: PROGRAMACION VISUAL
INSERT INTO "DocentesMateria"(id_docente, id_materia) 
VALUES(55,53);

--INSERT DOCENTE: PATRICIO PACHECO --MATERIA: DISEÑO DE INTERFACES
INSERT INTO "DocentesMateria"(id_docente, id_materia) 
VALUES(55,56);

--INSERT DOCENTE: ROBERTO CARRANZA --MATERIA: FUNDAMENTOS DE ADMINISTRACION
INSERT INTO "DocentesMateria"(id_docente, id_materia) 
VALUES(14,52);

--INSERT DOCENTE: YAMILA RODRIGUEZ --MATERIA: INGLES
INSERT INTO "DocentesMateria"(id_docente, id_materia) 
VALUES(48,19);

/*
    INSERT EN CURSOS
*/

INSERT INTO "Cursos"(id_materia, id_prd_lectivo,id_docente,id_jornada,curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo)
VALUES(54,1,54,1,'M3B',30,3,'B');

INSERT INTO "Cursos"(id_materia, id_prd_lectivo,id_docente,id_jornada,curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo)
VALUES(51,1,49,1,'M3B',30,3,'B');

INSERT INTO "Cursos"(id_materia, id_prd_lectivo,id_docente,id_jornada,curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo)
VALUES(53,1,55,1,'M3B',30,3,'B');

INSERT INTO "Cursos"(id_materia, id_prd_lectivo,id_docente,id_jornada,curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo)
VALUES(56,1,55,1,'M3B',30,3,'B');

INSERT INTO "Cursos"(id_materia, id_prd_lectivo,id_docente,id_jornada,curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo)
VALUES(52,1,14,1,'M3B',30,3,'B');

INSERT INTO "Cursos"(id_materia, id_prd_lectivo,id_docente,id_jornada,curso_nombre, curso_capacidad, curso_ciclo, curso_paralelo)
VALUES(19,1,48,1,'M3B',30,3,'B');
