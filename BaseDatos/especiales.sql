
/*
    
*/

		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'BDcierre';


UPDATE public."Notas"
SET id_tipo_nota = 14 
WHERE id_tipo_nota = 5;


UPDATE public."Notas"
SET id_tipo_nota = 39
WHERE id_tipo_nota = 6;