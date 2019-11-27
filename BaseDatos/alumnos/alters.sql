ALTER TABLE "alumno"."Egresados" 
  ADD CONSTRAINT "almn_carrera__fk__egresados" FOREIGN KEY ("id_almn_carrera") REFERENCES "public"."AlumnosCarrera" ("id_almn_carrera") ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT "id_prd_lectivo__fk__egresados" FOREIGN KEY ("id_prd_lectivo") REFERENCES "public"."PeriodoLectivo" ("id_prd_lectivo") ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT "unique__almn_carrera___egresado__" UNIQUE ("id_almn_carrera");

COMMENT ON CONSTRAINT "unique__almn_carrera___egresado__" ON "alumno"."Egresados" IS 'Este unique sirve para que jamas se repita el mismo alumno ';

ALTER TABLE "alumno"."Egresados" 
  ADD PRIMARY KEY ("id_egresado");