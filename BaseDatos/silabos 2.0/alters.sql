ALTER TABLE "public"."Silabo"
  ADD COLUMN "editando" bool,
  ADD COLUMN "editado_por" INTEGER,
  ADD COLUMN "utima_edicion_bd" timestamp(0) DEFAULT now();

-- Agregamos el docente coordinador de este silabo

ALTER TABLE public."Silabo"
ADD COLUMN id_docente_coordinador INTEGER;
