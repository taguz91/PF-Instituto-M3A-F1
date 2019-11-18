ALTER TABLE "public"."Silabo" 
  ADD COLUMN "editando" bool,
  ADD COLUMN "editado_por" INTEGER,
  ADD COLUMN "utima_edicion_bd" timestamp(0) DEFAULT now();