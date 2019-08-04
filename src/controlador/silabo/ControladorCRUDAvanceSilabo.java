/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDAvanceSilabo;

/**
 *
 * @author Daniel
 */
public class ControladorCRUDAvanceSilabo {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private boolean esCordinador = false;
    private List<CarreraMD> carreras_docente;
    private frmCRUDAvanceSilabo seguimiento;
    private VtnPrincipal vtnPrincipal;

    public ControladorCRUDAvanceSilabo(UsuarioBD usuario, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.vtnPrincipal = vtnPrincipal;
    }
    public void initCrud(){
         conexion.conectar();

        seguimiento = new frmCRUDAvanceSilabo();
        vtnPrincipal.getDpnlPrincipal().add(seguimiento);
        seguimiento.setTitle("Avances de Sílabos");
        seguimiento.show();

        seguimiento.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - seguimiento.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - seguimiento.getSize().height) / 2);
        seguimiento.getBtnNuevo().addActionListener(e->insertar());

    }
    public void insertar(){
         ControladorConfiguracionAvanceSilabo AS= new ControladorConfiguracionAvanceSilabo(usuario,vtnPrincipal, conexion);
        AS.init();
        seguimiento.dispose();
    }

}
