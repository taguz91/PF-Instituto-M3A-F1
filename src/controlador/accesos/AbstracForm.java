/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.accesos;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import groovy.model.DefaultTableModel;
import modelo.accesos.AccesosBD;
import modelo.usuario.RolBD;
import vista.accesos.FrmAccesosDeRol;

/**
 *
 * @author MrRainx
 */
public abstract class AbstracForm {

    private final VtnPrincipalCTR desktop;
    private final FrmAccesosDeRol vista;
    private final AccesosBD modelo;
    private RolBD rol;

    private DefaultTableModel tabla;

    public AbstracForm(VtnPrincipalCTR destop) {
        this.desktop = destop;
        this.vista = new FrmAccesosDeRol();
        this.modelo = new AccesosBD();
    }

    public void setRol(RolBD rol) {
        this.rol = rol;
    }

    public void Init() {

        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());
        tabla = (DefaultTableModel) vista.getTabPermDados().getModel();
        //tabla.get
    }

}
