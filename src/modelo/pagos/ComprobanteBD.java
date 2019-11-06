package modelo.pagos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author MrRainx
 */
public class ComprobanteBD {

    public static List<ComprobanteMD> selectAll() {
        String SELECT = ""
                + "SELECT\n"
                + "	\"Comprobante\".id_comprobante,\n"
                + "	\"Comprobante\".id_almno,\n"
                + "	\"Comprobante\".comprobante_total,\n"
                + "	\"Comprobante\".comprobante_codigo,\n"
                + "	\"Comprobante\".comprobante_fecha_pago,\n"
                + "	\"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "	\"Comprobante\".id_prd_lectivo,\n"
                + "	\"Personas\".persona_identificacion,\n"
                + "	\"Personas\".persona_primer_apellido,\n"
                + "	\"Personas\".persona_primer_nombre \n"
                + "FROM\n"
                + "	\"Comprobante\"\n"
                + "	INNER JOIN \"Alumnos\" ON \"Comprobante\".id_almno = \"Alumnos\".id_alumno\n"
                + "	INNER JOIN \"PeriodoLectivo\" ON \"Comprobante\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo\n"
                + "	INNER JOIN \"Personas\" ON \"Alumnos\".id_persona = \"Personas\".id_persona \n"
                + "WHERE\n"
                + "	\"Comprobante\".comprobante_activo IS TRUE"
                + "";

        List<ComprobanteMD> comprobantes = new ArrayList<>();

        ConnDBPool pool = new ConnDBPool();
        Connection conn = pool.getConnection();
        ResultSet rs = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rs.next()) {

                ComprobanteMD comprobante = new ComprobanteMD();

                comprobante.setId(rs.getInt("id_comprobante"));

                PeriodoLectivoMD periodo = new PeriodoLectivoMD()
                        .setPeriodo(rs.getInt("id_prd_lectivo"))
                        .setNombre(rs.getString("prd_lectivo_nombre"));

                comprobante.setPeriodo(periodo);

                PeriodoLectivoMD periodoBuild = new PeriodoLectivoMD().periodoBuilder($ -> {

                });

                comprobante.setTotal(rs.getDouble("comprobante_total"));
                comprobante.setCodigo(rs.getString("comprobante_codigo"));
                System.out.println(rs.getDate("comprobante_fecha_pago"));

                comprobantes.add(comprobante);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ComprobanteBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // pool.closeStmt().close(rs).close(conn);
        }

        return comprobantes;
    }

}
