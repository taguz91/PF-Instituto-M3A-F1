
package controlador.silabo;

import java.util.List;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.dbCarreras;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDHorarios;
import vista.silabos.frmConfiguraciónPlanClase;
import vista.silabos.frmPlanClase;


public class ControladorConfiguracion_plan_clases {
    private List<CarreraMD> carrerasDocente;
    private VtnPrincipal principal;
    private UsuarioBD usuario;
    private ConexionBD conexion;
    private frmConfiguraciónPlanClase frmConPlanClase;
    
     public ControladorConfiguracion_plan_clases(UsuarioBD usuario, frmConfiguraciónPlanClase frmConPlanClase) {
        this.usuario = usuario;
        this.frmConPlanClase=frmConPlanClase;
        iniciaControlador();
    }
     
     public void iniciaControlador(){
         cargarComboCarreras();
         
     }
     
     public void cargarComboCarreras(){
         List<CarreraMD> carreras;
         carreras=new dbCarreras().buscarCarreras(usuario.getPersona().getIdPersona());
         for (int i = 0; i < carreras.size(); i++) {
             frmConPlanClase.getCmb_carreras().addItem(carreras.get(i).getNombre());
         }
     }
     

    
    
}
