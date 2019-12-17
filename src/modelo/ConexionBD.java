
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.CONBD;

/**
 *
 * @author Andres Ullauri
 */
public class ConexionBD extends CONBD {
    

    private Connection con = null;

    private Statement stm = null;

    private ResultSet rs = null;

    public void conectar() {
        //con = CON.getConnection();
    }

    public void desconectar() throws SQLException {

        if (con != null) {
            con.close();
        }

        if (stm != null) {
            stm.close();
        }

        if (rs != null) {
            rs.close();
        }
    }

    public Connection getCon() {
        // Comprobar conexion
        con = CON.getConnection();
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStm() {
        return stm;
    }

    public void setStm(Statement stm) {
        this.stm = stm;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

}
