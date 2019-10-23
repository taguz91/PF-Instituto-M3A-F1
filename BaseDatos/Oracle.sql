--Crear un trigger que se ejecute antes de insertar un producto dentro de la tala detalle factura, se debe validar si existe la suficiente cantidad disponible en la tabla inventarios del producto, si no existe dicha cantidad, no debe permitir ingresar el producto.

----

CREATE OR REPLACE TRIGGER fecha_contrato
BEFORE INSERT ON employees FOR EACH ROW
BEGIN
	IF :new.hire_date IS NULL THEN
    	:new.hire_date := SYSDATE;
	END IF;
END;

----

CREATE OR RAPLCE TRIGGER val_producto
BEFORE INSERT ON T_DETALLE_FACTURA FOR EACH ROW
DECLARE
  cant_productos NUMBER := 0;
BEGIN

  SELECT
  disponible
  INTO
  cant_productos
  FROM T_INVENTARIOS  i,
  T_FACTURAS f,
  T_EMPLEADOS e
  WHERE
  f.id_factura = NEW.id_factura_fk AND
  e.id_empleado = f.id_empleado_fk AND
  i.id_producto = NEW.ID_PRODUCTO_FK AND
  i.id_sucursal = e.id_sucursal;

  IF :cant_productos >= NEW.disponible THEN
    -- Guardamos
  ELSE
    -- Error no se guarda
  END IF;

END;


--

CREATE OR REPLACE PROCEDURE
comprobar_salario()
DECLARE

tiempo_trabajo NUMBER(10);
ciudad VARCHAR2(255);
salario NUMBER(8, 2);
id NUMBER(6);

-- Cursor
CURSOR empleados
IS  SELECT
employee_id,
MOTHS_BETWEEN(SYSDATE, hire_date) AS tiempo,
salary,
city
FROM
employees e,
departments d,
locations l
WHERE
d.department_id = e.department_id AND
l.location_id = d.location_id;

BEGIN

  -- Debes abrir el cursor
  OPEN empleados;

  LOOP
  FETCH empleados INTO
  id,
  tiempo_trabajo,
  salario,
  ciudad;

    IF tiempo_trabajo/12 < 10 AND ciudad LIKE 'Seatle' THEN
      UPDATE employees
      SET salary = ( salary * 0.20 ) + salary
      WHERE employee_id = id;
    ELSE IF  THE
    ENF IF;

  -- Debes cerrar el cursor
  CLOSE empleados;

END;
