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
                + "	pago.\"Comprobante\".id_comprobante,\n"
                + "	pago.\"Comprobante\".id_prd_lectivo,\n"
                + "	pago.\"Comprobante\".id_almno,\n"
                + "	pago.\"Comprobante\".comprobante_total,\n"
                + "	pago.\"Comprobante\".comprobante_codigo,\n"
                + "	pago.\"Comprobante\".comprobante_fecha_pago\n"
                + "FROM\n"
                + "	pago.\"Comprobante\" \n"
                + "WHERE\n"
                + "	pago.\"Comprobante\".comprobante_activo IS TRUE"
                + "";

        List<ComprobanteMD> comprobantes = new ArrayList<>();

        ConnDBPool pool = new ConnDBPool();
        Connection conn = pool.getConnection();
        ResultSet rs = pool.ejecutarQuery(SELECT, conn, null);

        try {
            while (rs.next()) {

                ComprobanteMD comprobante = new ComprobanteMD();

                comprobante.setId(rs.getInt("id_comprobante"));
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setPeriodo(rs.getInt("id_prd_lectivo"));
                comprobante.setPeriodo(periodo);
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
