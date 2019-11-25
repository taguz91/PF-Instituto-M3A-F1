INSERT INTO pago."ComprobantePago"(
id_comprobante_pago,
id_prd_lectivo,
id_alumno,
comprobante,
comprobante_total,
comprobante_codigo,
comprobante_fecha_pago,
comprobante_observaciones,
comprobante_usuario_ingreso
) VALUES (
  ?, ?, ?, ?,
  ?, ?, ?, ?,
  ?);


UPDATE pago."ComprobantePago"
SET comprobante=?
WHERE id_comprobante_pago=?;


UPDATE pago."ComprobantePago"
SET id_prd_lectivo=?,
comprobante_total=?,
comprobante_codigo=?,
comprobante_fecha_pago=?,
comprobante_observaciones=?,
comprobante_usuario_ingreso=?
WHERE id_comprobante_pago=?;


INSERT INTO pago."PagoMateria"(
id_comprobante,
id_malla_alumno,
pago_materia,
pago_numero_matricula)
VALUES (?, ?, ?, ?);

UPDATE pago."PagoMateria"
SET pago_materia=?,
pago_numero_matricula=?
WHERE id_pago_materia=?;
