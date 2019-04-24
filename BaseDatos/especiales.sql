
/*
    
*/

		SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'BDinsta';