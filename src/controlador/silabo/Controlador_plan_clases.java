
package controlador.silabo;

import modelo.ConexionBD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmPlanClase;


public class Controlador_plan_clases {
     private final UsuarioBD usuario;
    private ConexionBD conexion;
    private final VtnPrincipal vtnPrincipal;
    private frmPlanClase fPlanClase;
    public Controlador_plan_clases(UsuarioBD usuario, VtnPrincipal vtnPrincipal) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = new ConexionBD();
    }
     public void iniciaControlador(){
         conexion.conectar();
         fPlanClase=new frmPlanClase();
         vtnPrincipal.getDpnlPrincipal().add(fPlanClase);
         fPlanClase.setTitle("Plan de Clases");
         fPlanClase.show();
         fPlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - fPlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - fPlanClase.getSize().height) / 2);
         fPlanClase.getBtnCancelarPC().addActionListener(a1 -> {
             fPlanClase.dispose();
             ControladorConfiguracion_plan_clases ccpc=new ControladorConfiguracion_plan_clases(usuario, vtnPrincipal);
             ccpc.iniciarControlaador();
         });
           
           
     }
    
    
    
    
     
     
}
