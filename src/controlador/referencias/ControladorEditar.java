
package controlador.referencias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.ReferenciasB.ReferenciaBD;
import modelo.ReferenciasB.ReferenciasMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmEditarBiblioteca;


public class ControladorEditar {
    private frmEditarBiblioteca vista;
    private ReferenciaBD modelo ;
    private  VtnPrincipal vtnPrin;
 private ConectarDB conecta;
 private String clave;
    public ControladorEditar(frmEditarBiblioteca vista, ReferenciaBD modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.setVisible(true);
    }

    public ControladorEditar(frmEditarBiblioteca vista, ReferenciaBD modelo, VtnPrincipal vtnPrin,String clave, ConectarDB conecta) {
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPrin = vtnPrin;
        this.conecta = conecta;
        this.clave=clave;
    }
    
    public  ReferenciasMD EditarBiblioteca(ConexionBD conexion, String clave) {

        List<ReferenciasMD> referencias = new ArrayList<>();
         ReferenciasMD tmp = new ReferenciasMD();
        try {

            PreparedStatement st = conexion.getCon().prepareStatement("SELECT id_referencia, codigo_referencia, descripcion_referencia, tipo_referencia, existe_en_biblioteca\n"
                    + "FROM public.\"Referencias\"\n"
                    + "WHERE tipo_referencia='Base'\n"
                    + "AND descripcion_referencia ILIKE '%" + clave + "%'");

            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                
                tmp.setId_referencia(rs.getInt(1));
                tmp.setCodigo_referencia(rs.getString(2));
                tmp.setDescripcion_referencia(rs.getString(3));
                tmp.setTipo_referencia(rs.getString(4));
                tmp.setExiste_en_biblioteca(rs.getBoolean(5));

                
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReferenciasMD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  tmp;

    }
}

