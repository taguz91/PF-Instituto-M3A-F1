ALTER TABLE "public"."Silabo" 
  ADD COLUMN "editando" bool,
  ADD COLUMN "editado_por" varchar(50),
  ADD COLUMN "utima_edicion_bd" timestamp(0) DEFAULT now();