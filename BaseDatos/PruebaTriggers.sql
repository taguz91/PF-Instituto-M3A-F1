INSERT INTO public."Lugares"(
	lugar_nombre, lugar_nivel)
	VALUES ('ECUADOR', 1);

INSERT INTO public."Personas"(
	 persona_identificacion,
	 persona_primer_apellido, persona_segundo_apellido,
	 persona_primer_nombre, persona_segundo_nombre,
	 persona_genero, persona_sexo,
	 persona_estado_civil, persona_etnia,
	 persona_idioma_raiz, persona_tipo_sangre,
	 persona_telefono, persona_celular,
	 persona_correo, persona_fecha_registro,
	 persona_discapacidad, persona_calle_principal,
	 persona_numero_casa, persona_calle_secundaria,
	 persona_referencia, persona_sector,
	 persona_idioma, persona_tipo_residencia,
	 persona_fecha_nacimiento)
	VALUES ('0107390270',
		'GARCÍA', 'INGA',
		'JOHNNY', 'GUSTAVO',
		'M', 'M',
		'SOLTERO', 'MESTIZO',
		'ESPAÑOL', 'ORH+',
		'0968796010', '0968796010',
		'GUS199811@GMAIL.COM', '15/2/2019',
		'false', 'AV. AMERICAS',
		'00-00', 'TURUHUAYCO',
		'FRENTE A UNA TIENDA', 'BELLA VISTA',
		'ESPAÑOL', 'ARRENDADA',
		'24/11/1998');

INSERT INTO "Roles"("rol_nombre") VALUES('ROOT');
INSERT INTO "Usuarios"("usu_username","usu_password", "id_persona")
VALUES('GG', set_byte( MD5('HolaGG')::bytea, 4,64), 1);

INSERT INTO "Usuarios"("usu_username","usu_password") 
VALUES('postgres', set_byte( MD5('Holapostgres')::bytea, 4,64));
