
/*
    
*/

		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'BDcierre';


UPDATE public."Notas"
SET id_tipo_nota = 14 
WHERE id_tipo_nota = 5;


UPDATE public."Notas"
SET id_tipo_nota = 39
WHERE id_tipo_nota = 6;


UPDATE public."Notas"
SET id_tipo_nota = 13
WHERE id_tipo_nota = 7;


UPDATE public."Notas"
SET id_tipo_nota = 15
WHERE id_tipo_nota = 8;


UPDATE public."Notas"
SET id_tipo_nota = 16
WHERE id_tipo_nota = 9;


UPDATE public."Notas"
SET id_tipo_nota = 12
WHERE id_tipo_nota = 4;


UPDATE public."Notas"
SET id_tipo_nota = 17
WHERE id_tipo_nota = 10;