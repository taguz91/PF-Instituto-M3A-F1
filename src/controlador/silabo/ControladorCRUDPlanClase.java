/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import modelo.ConexionBD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDPlanClase;



/**
 *
 * @author Daniel
 */
public class ControladorCRUDPlanClase {
  private final UsuarioBD usuario;
  private ConexionBD conexion;
  private final VtnPrincipal principal;
  private frmCRUDPlanClase fCrud_plan_Clases;

    public ControladorCRUDPlanClase(UsuarioBD usuario, ConexionBD conexion, VtnPrincipal principal) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.principal = principal;
    }
  
  public void iniciaControlador(){
        conexion.conectar();

        fCrud_plan_Clases = new frmCRUDPlanClase();
        principal.getDpnlPrincipal().add(fCrud_plan_Clases);
        fCrud_plan_Clases.setTitle("Configuración Plan de Clases");
        fCrud_plan_Clases.show();

        fCrud_plan_Clases.setLocation((principal.getDpnlPrincipal().getSize().width - fCrud_plan_Clases.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - fCrud_plan_Clases.getSize().height) / 2);
         
        fCrud_plan_Clases.getBtnNuevoPLC().addActionListener(a -> { 
            fCrud_plan_Clases.dispose();
         ControladorConfiguracion_plan_clases cp = new ControladorConfiguracion_plan_clases(usuario, principal, conexion);
        cp.iniciarControlaador();
        });
        
  } 
}
