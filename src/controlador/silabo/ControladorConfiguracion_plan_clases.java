
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
   
     private final UsuarioBD usuario;
     private ConexionBD conexion;
     private frmConfiguraciónPlanClase frm_cong_PlanClase;
     private final VtnPrincipal vtnPrincipal;

    public ControladorConfiguracion_plan_clases(UsuarioBD usuario, VtnPrincipal vtnPrincipal) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion=new ConexionBD();
    }
     public void iniciarControlaador(){
         conexion.conectar();
         frm_cong_PlanClase=new frmConfiguraciónPlanClase();
         vtnPrincipal.getDpnlPrincipal().add(frm_cong_PlanClase);
         frm_cong_PlanClase.setTitle("Configuración Plan de Clases");
         frm_cong_PlanClase.show();
          frm_cong_PlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - frm_cong_PlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - frm_cong_PlanClase.getSize().height) / 2);
          
          
          cargarComboCarreras();
     }
     

    
    public List<CarreraMD> cargarComboCarreras(){
        List<CarreraMD> carrerasDocentes=CarrerasBDS.consultar(conexion, usuario.getUsername());
        carrerasDocentes.forEach((cmd) -> {
            frm_cong_PlanClase.getCmb_carreras().addItem(cmd.getNombre());
        });

        return carrerasDocentes;
    }
}
