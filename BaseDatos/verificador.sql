/*
    CORRECTO
*/


SELECT
"public"."Notas".id_nota,
"public"."Notas".nota_valor,
"public"."Notas".id_tipo_nota,
"public"."TipoDeNota".tipo_nota_nombre,
"public"."TipoDeNota".tipo_nota_valor_minimo,
"public"."TipoDeNota".tipo_nota_valor_maximo,
"public"."TipoDeNota".id_prd_lectivo,
"public"."PeriodoLectivo".prd_lectivo_nombre
FROM
"public"."Notas"
INNER JOIN "public"."TipoDeNota" ON "public"."Notas".id_tipo_nota = "public"."TipoDeNota".id_tipo_nota
INNER JOIN "public"."PeriodoLectivo" ON "public"."TipoDeNota".id_prd_lectivo = "public"."PeriodoLectivo".id_prd_lectivo
WHERE
"public"."TipoDeNota".tipo_nota_estado IS TRUE AND
"public"."Notas".id_almn_curso = 8589