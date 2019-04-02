CREATE ROLE "permisos" CREATEROLE LOGIN ENCRYPTED PASSWORD 'ROOT';

GRANT Connect, Create, Temporary ON DATABASE "BDinsta" TO "permisos";

GRANT Create, Usage ON SCHEMA "public" TO "permisos";

GRANT Execute ON FUNCTION "public"."actualiza_nom_prd"() TO "permisos";

GRANT Execute ON FUNCTION "public"."almn_carrera_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."almn_curso_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."alumno_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."carrera_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."curso_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."detalle_jrd_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."docente_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."docentes_materia_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."eje_formacion_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_accesos"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_accesos_rol"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_alumno_curso"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_alumnos"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_alumnos_carrera"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_carreras"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_cursos"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_detalle_jornada"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_docente_materia"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_docentes"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_ejes_formacion"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_evaluacion_silabo"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_jornada_docente"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_jornadas"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_lugares"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_malla_alumno"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_materia_requisito"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_materias"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_periodo_ingreso_notas"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_periodo_lectivo"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_personas"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_referencia_silabo"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_referencias"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_roles"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_roles_usuario"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_sector_economico"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_sesion_clase"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_silabo"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_tipo_actividad"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_tipo_nota"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_unidad_silabo"() TO "permisos";

GRANT Execute ON FUNCTION "public"."historial_usuarios"() TO "permisos";

GRANT Execute ON FUNCTION "public"."iniciar_ingreso_notas"() TO "permisos";

GRANT Execute ON FUNCTION "public"."jornada_docente_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."malla_almn_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."materia_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."materia_requisito_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."persona_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."prd_ingreso_nt_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."prd_lectivo_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."referencia_silabo_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."sesion_clase_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."silabo_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."tipo_nota_elim"() TO "permisos";

GRANT Execute ON FUNCTION "public"."usuario_elim"() TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Accesos" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."AccesosDelRol" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."AlumnoCurso" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Alumnos" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."AlumnosCarrera" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Carreras" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Cursos" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."DetalleJornada" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Docentes" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."DocentesMateria" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."EjesFormacion" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."EstrategiasAprendizaje" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."EstrategiasUnidad" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."EvaluacionSilabo" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."HistorialUsuarios" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."IngresoNotas" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."JornadaDocente" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Jornadas" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Lugares" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."MallaAlumno" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."MateriaRequisitos" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Materias" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."PeriodoIngresoNotas" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."PeriodoLectivo" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Personas" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Referencias" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."ReferenciaSilabo" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Roles" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."RolesDelUsuario" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."SectorEconomico" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."SesionClase" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Silabo" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."TipoActividad" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."TipoDeNota" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."UnidadSilabo" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Usuarios" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."Usuarios_Persona" TO "permisos";

GRANT Delete, Insert, References, Select, Trigger, Truncate, Update ON TABLE "public"."ViewAlumnoCurso" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."AccesosDelRol_id_acceso_del_rol_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."AlumnoCurso_id_almn_curso_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Alumnos_id_alumno_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."AlumnosCarrera_id_almn_carrera_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Carreras_id_carrera_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Cursos_id_curso_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."DetalleJornada_id_detalle_jornada_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Docentes_id_docente_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."DocentesMateria_id_docente_mat_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."EjesFormacion_id_eje_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."EstrategiasSilabo_id_estrategia_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."EstrategiasUnidad_id_estrategia_unidad_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."EvaluacionSilabo_id_evaluacion_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."HistorialUsuarios_id_historial_user_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."IngresoNotas_id_ingreso_notas_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."JornadaDocente_id_jornada_docente_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Jornadas_id_jornada_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Lugares_id_lugar_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."MallaAlumno_id_malla_alumno_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."MateriaRequisitos_id_requisito_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Materias_id_materia_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."PeriodoIngresoNotas_id_perd_ingr_notas_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."PeriodoLectivo_id_prd_lectivo_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Personas_id_persona_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Referencias_id_referencia_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."ReferenciaSilabo_id_referencia_silabo_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Roles_id_rol_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."RolesDelUsuario_id_roles_usuarios_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."SectorEconomico_id_sec_economico_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."SesionClase_id_sesion_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Silabo_id_silabo_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."TipoActividad_id_tipo_actividad_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."TipoDeNota_id_tipo_nota_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."UnidadSilabo_id_unidad_seq" TO "permisos";

GRANT Select, Update, Usage ON SEQUENCE "public"."Usuarios_id_usuario_seq" TO "permisos";