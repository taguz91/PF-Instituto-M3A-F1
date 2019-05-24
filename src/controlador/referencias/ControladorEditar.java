
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
  private ConexionBD conexion;
 private String clave;
    public ControladorEditar(frmEditarBiblioteca vista, ReferenciaBD modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.setVisible(true);
    }

    public ControladorEditar(frmEditarBiblioteca vista, ReferenciaBD modelo, VtnPrincipal vtnPrin,String clave, ConexionBD conexion) {
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPrin = vtnPrin;
        this.conexion = conexion;
        this.clave=clave;
    }
    public void inicia_vista()
    {
      //ReferenciaMD m=new ReferenciaMD();
      //m=modelo.EditarBiblioteca(conexion, clave);
    }
   
}

