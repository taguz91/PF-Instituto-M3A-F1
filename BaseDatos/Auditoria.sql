--Para registrar los que ingresan en la aplicacion 
INSERT INTO public."HistorialUsuarios"(
	usu_username, historial_fecha, historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla)
	VALUES ('postgres', now(), 'INICIO SESION', 'SISTEMA', 0);

  INSERT INTO public."HistorialUsuarios"(
  	usu_username, historial_fecha, historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla)
  	VALUES ('postgres', now(), 'CIERRE SESION', 'SISTEMA', 0);
