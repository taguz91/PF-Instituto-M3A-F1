
package controlador.silabo;

import java.util.List;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.silabo.CarrerasBDS;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmConfiguraciónPlanClase;


public class ControladorConfiguracion_plan_clases {
       private List<CarreraMD> carrerasDocente;
    private VtnPrincipal principal;
    private UsuarioBD usuario;
    private ConexionBD conexion;
    private frmConfiguraciónPlanClase frmPlanClase;
    
     public ControladorConfiguracion_plan_clases(VtnPrincipal principal, UsuarioBD usuario, ConexionBD conexion) {
        this.principal = principal;
        this.usuario = usuario;
        this.conexion = conexion;
    }
     
     public void iniciaControlador(){
         if (conexion.getCon() == null) {
            conexion.conectar();
        }
         
     }
     
//     public List<CarreraMD> cargarComboCarreras(){
////         List<CarreraMD> carrerasDocente=CarrerasBDS.consultar(conexion, usuario.getUsername());
////         carrerasDocente.forEach((cmd)-> {
////             frmConfiguraciónPlanClase.
////         });
//     }
    
    
}
