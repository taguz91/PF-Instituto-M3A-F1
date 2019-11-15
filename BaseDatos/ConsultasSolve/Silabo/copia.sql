CREATE OR REPLACE FUNCTION copiar_todo_silabo (id_silabo_param INTEGER, id_periodo_param INTEGER)
RETURNS VOID AS $BODY$
DECLARE

  id_silabo_gen INTEGER := 0;
  id_unidad_gen INTEGER := 0;

  reg RECORD;

  unidades CURSOR FOR SELECT
  id_unidad,
  id_silabo,
  numero_unidad,
  objetivo_especifico_unidad,
  resultados_aprendizaje_unidad,
  contenidos_unidad,
  fecha_inicio_unidad,
  fecha_fin_unidad,
  horas_docencia_unidad,
  horas_practica_unidad,
  horas_autonomo_unidad,
  titulo_unidad
  FROM public."UnidadSilabo"
  WHERE id_silabo = id_silabo_param;

BEGIN
  -- Para probar 5681
  INSERT INTO public."Silabo"(
          id_materia,
          id_prd_lectivo,
          estado_silabo,
          fecha_silabo)
  SELECT id_materia,
  id_periodo_param,
  0,
  fecha_silabo
  FROM public."Silabo"
  WHERE id_silabo = id_silabo_param
  RETURNING id_silabo INTO id_silabo_gen;

  RAISE NOTICE 'ID Silabo generado: % ', id_silabo_gen;

  OPEN unidades;
  FETCH unidades INTO reg;
  WHILE ( FOUND ) LOOP
     INSERT INTO public."UnidadSilabo"(
            id_silabo,
            numero_unidad,
            objetivo_especifico_unidad,
            resultados_aprendizaje_unidad,
            contenidos_unidad,
            fecha_inicio_unidad,
            fecha_fin_unidad,
            horas_docencia_unidad,
            horas_practica_unidad,
            horas_autonomo_unidad,
            titulo_unidad)
    VALUES( id_silabo_gen,
            reg.numero_unidad,
            reg.objetivo_especifico_unidad,
            reg.resultados_aprendizaje_unidad,
            reg.contenidos_unidad,
            reg.fecha_inicio_unidad,
            reg.fecha_fin_unidad,
            reg.horas_docencia_unidad,
            reg.horas_practica_unidad,
            reg.horas_autonomo_unidad,
            reg.titulo_unidad )
            RETURNING id_unidad INTO id_unidad_gen;


    INSERT INTO public."EstrategiasUnidad"(
            id_unidad, id_estrategia)
    SELECT id_unidad_gen, id_estrategia
    FROM public."EstrategiasUnidad"
    WHERE id_unidad = reg.id_unidad;

    INSERT INTO public."EvaluacionSilabo"(
            id_unidad, indicador, id_tipo_actividad, instrumento,
            valoracion, fecha_envio, fecha_presentacion)
    SELECT id_unidad_gen,
    indicador,
    id_tipo_actividad,
    instrumento,
    valoracion,
    fecha_envio,
    fecha_presentacion
    FROM public."EvaluacionSilabo"
    WHERE id_unidad = reg.id_unidad;

    RAISE NOTICE 'ID Unidad generada: % ', id_unidad_gen;

    FETCH unidades INTO reg;
  END LOOP;
  CLOSE unidades;

END $BODY$ LANGUAGE plpgsql;
